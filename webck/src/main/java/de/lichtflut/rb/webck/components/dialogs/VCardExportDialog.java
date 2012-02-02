/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import static org.arastreju.sge.SNOPS.fetchObject;
import static org.arastreju.sge.SNOPS.string;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  Modal dialog for vCard export of a person ResourceNode.
 * </p>
 *
 * <p>
 * 	Created Feb 2, 2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class VCardExportDialog extends AbstractRBDialog implements IResourceListener {

	private final ResourceStreamResource resource;
	
	private final IModel<String> format;

	private final IModel<RBEntity> entityModel;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public VCardExportDialog(final String id, final IModel<RBEntity> model) {
		super(id);
		this.entityModel = model;
		
		format = new Model<String>("vCard 3.0");
		resource = prepareResource(format);
		
		final Form form = new Form("form");
		form.add(new DropDownChoice<String>("format", format, getChoices()));
		form.add(new AjaxButton("exportButton", form) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				final String uid = "&uid" + System.nanoTime();
				target.appendJavaScript("window.location.href='" +  getDownloadUrl() + uid + "'");
				close(target);
			}
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				target.add(form);
			}
			
		});
		
		add(form); 
		
		setModal(true);
		setWidth(600);
	}
	
	// -- IResourceListener -------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void onResourceRequested() {
		final RequestCycle cycle = RequestCycle.get();
		final Attributes a = new Attributes(cycle.getRequest(), cycle.getResponse(), null);
		resource.setFileName(getFilename(entityModel.getObject().getNode()));
		resource.respond(a);
	}
	
	// ----------------------------------------------------
	
	protected ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"vCard 3.0", "vCard 2.1"}));
	}
	
	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE);
	}
	
	protected ResourceStreamResource prepareResource(final IModel<String> format) {
		 final ResourceStreamResource resource = new ResourceStreamResource(new ContentStream(format));
		 resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		 //TODO: scheint keine Auswirkung zu haben!
		 resource.setTextEncoding("UTF-8");
		 return resource;
	}
	
	private String getFilename(ResourceNode node) {
		final StringBuilder sb = new StringBuilder(255);
		sb.append(string(fetchObject(node, RB.HAS_LAST_NAME)));
		sb.append("_");
		sb.append(string(fetchObject(node, RB.HAS_FIRST_NAME)));
		sb.append("-");
		sb.append(System.currentTimeMillis());
		sb.append(".vcf");
		return sb.toString();
	}
	
	// ----------------------------------------------------
	
	class ContentStream extends AbstractResourceStream {

		private final IModel<String> format;
		private transient InputStream in;
		private Bytes length;

		/**
		 * @param format
		 */
		public ContentStream(final IModel<String> format) {
			this.format = format;
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		public InputStream getInputStream() throws ResourceStreamNotFoundException {
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try {
				
				final RBEntity entity = entityModel.getObject();
				
				if ("vCard 3.0".equalsIgnoreCase(format.getObject())){
					write30(entity.getNode(), buffer);
				} else {
					throw new IllegalArgumentException("Format not yet supported: " + format.getObject());
				}
				
				length = Bytes.bytes(buffer.size());
				in = new ByteArrayInputStream(buffer.toByteArray());
				buffer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return in;
		}
		
		private void write30(final ResourceNode node, final OutputStream out)	throws IOException {
			final StringBuilder sb = new StringBuilder();
/*

BEGIN:VCARD
VERSION:3.0
FN:Vorname Nachname
N:Nachname;Vorname;;;
BDAY:1980-05-21
ADR:;;Diestrasse 100;Stadt;;55555;Heimatland
EMAIL;TYPE=internet:emailnr1@mail.de
EMAIL;TYPE=internet:emailnr2@mail.de
TEL;TYPE=work:0221-12345
TEL;TYPE=cell:0162-1234567
TEL;TYPE=home:02202-123456
UID:
REV:2012-01-31T14:28:30Z
END:VCARD

*/
			sb.append("BEGIN:VCARD\n");
			sb.append("VERSION:3.0\n");
			sb.append("FN:" +string(fetchObject(node, RB.HAS_FIRST_NAME)) +" " +string(fetchObject(node, RB.HAS_LAST_NAME)) +"\n");
			sb.append("N:" +string(fetchObject(node, RB.HAS_LAST_NAME)) +";" +string(fetchObject(node, RB.HAS_FIRST_NAME)) +";;;\n");
			sb.append("BDAY:" +"\n");
			
			//TODO: address
			//TODO: iterate over e-mails and phone numbers
			
			sb.append("UID:" +node.getQualifiedName() +"\n");
			sb.append("REV:" +Calendar.getInstance().getTime() +"\n"); //TODO DateFormat
			sb.append("END:VCARD");
			out.write(sb.toString().getBytes());
		}

		/** 
		* {@inheritDoc}
		*/
		@Override
		public void close() throws IOException {
			in.close();
			in = null;
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		public Bytes length() {
			return length;
		}
		
	}

}
