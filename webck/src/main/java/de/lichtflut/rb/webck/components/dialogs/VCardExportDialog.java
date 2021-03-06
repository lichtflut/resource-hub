/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.dialogs;


import de.lichtflut.rb.core.RB;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.ValueNode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

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
public class VCardExportDialog extends RBDialog implements IResourceListener {

	private final ResourceStreamResource resource;
	
	private final IModel<String> format;

	private final IModel<ResourceNode> nodeModel;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public VCardExportDialog(final String id, final IModel<ResourceNode> model) {
		super(id);
		this.nodeModel = model;
		
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
	
	@Override
	public void onResourceRequested() {
		final RequestCycle cycle = RequestCycle.get();
		final Attributes a = new Attributes(cycle.getRequest(), cycle.getResponse(), null);
		resource.setFileName(getFilename(nodeModel.getObject()));
		resource.respond(a);
	}
	
	// ----------------------------------------------------
	
	protected ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"vCard 3.0", "vCard 2.1"}));
	}
	
	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE, new PageParameters());
	}
	
	protected ResourceStreamResource prepareResource(final IModel<String> format) {
		 final ResourceStreamResource resource = new ResourceStreamResource(new ContentStream(format));
		 resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		 //TODO: scheint keine Auswirkung zu haben!
		 resource.setTextEncoding("UTF-8");
		 return resource;
	}
	
	private String getFilename(ResourceNode node) {
		final StringBuilder sb = new StringBuilder();
		sb.append(nsString(node, RB.HAS_LAST_NAME));
		sb.append("_");
		sb.append(nsString(node, RB.HAS_FIRST_NAME));
		sb.append("-");
		sb.append(System.currentTimeMillis());
		sb.append(".vcf");
		return sb.toString();
	}
	
	/**
	 * Private Method to return a null-save String
	 * @param subject
	 * @param predicate
	 * @return the string representation or empty string if null
	 */
	private String nsString(ResourceNode subject, ResourceID predicate) {
		String retVal = SNOPS.string(SNOPS.fetchObject(subject, predicate));
		if(retVal == null) {
			retVal = "";
		}
		return retVal;
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
		
		@Override
		public InputStream getInputStream() throws ResourceStreamNotFoundException {
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try {
				
				final ResourceNode node = nodeModel.getObject();
				
				if ("vCard 3.0".equalsIgnoreCase(format.getObject())){
					write30(node, buffer);
				} else if ("vCard 2.1".equalsIgnoreCase(format.getObject())){
					write21(node, buffer);
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
		
		/**
		 * Writes the vCard file content valid to specification 3.0
		 * @param node
		 * @param out
		 * @throws IOException
		 */
		private void write30(final ResourceNode node, final OutputStream out) throws IOException {
			final StringBuilder sb = new StringBuilder();

			String lastname = nsString(node, RB.HAS_LAST_NAME);
			String firstname = nsString(node, RB.HAS_FIRST_NAME);
			
			
			String bday = "";
			SemanticNode bdayNode = SNOPS.fetchObject(node, RB.HAS_DATE_OF_BIRTH);
			if(bdayNode != null && bdayNode.isValueNode()) {
				ValueNode bdayValue = bdayNode.asValue();
				if(bdayValue.getDataType().equals(ElementaryDataType.DATE)) {
					bday = new SimpleDateFormat("yyyy-MM-dd").format(bdayValue.getTimeValue());
				}
			}

			String street = "";
			String city = "";
			String zipcode = "";
			String country = "";
			SemanticNode addressNode = SNOPS.fetchObject(node, RB.HAS_ADDRESS);
			if(addressNode != null && addressNode.isResourceNode()) {
				ResourceNode adrResource = addressNode.asResource();
				street = nsString(adrResource, RB.HAS_STREET) +" "
						+nsString(adrResource, RB.HAS_HOUSE_NO);
				zipcode = nsString(adrResource, RB.HAS_ZIPCODE);
				SemanticNode cityNode = SNOPS.fetchObject(adrResource, RB.IS_IN_CITY);
				if(cityNode != null && cityNode.isResourceNode()) {
					ResourceNode cityResource = cityNode.asResource();
					city = nsString(cityResource, RB.HAS_NAME);
					SemanticNode countryNode = SNOPS.fetchObject(cityResource, RB.IS_IN_COUNTRY);
					if(countryNode != null && countryNode.isResourceNode()) {
						ResourceNode countryResource = countryNode.asResource();
						country = nsString(countryResource, RB.HAS_NAME);
					}
				}
			}
			
			String rev = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(Calendar.getInstance().getTime());
			
			sb.append("BEGIN:VCARD\n");
			sb.append("VERSION:3.0\n");
			sb.append("FN:" +firstname +" " +lastname +"\n");
			sb.append("N:" +lastname +";" +firstname +";;;\n");
			sb.append("BDAY:" +bday +"\n");
			
			sb.append("ADR:;;" +street +";" +city +";;" +zipcode +";" +country +"\n");
			
			for (SemanticNode emailNode : SNOPS.objects(node, RB.HAS_EMAIL)) {
				sb.append("EMAIL;TYPE=INTERNET:" +SNOPS.string(emailNode) +"\n");
			}
			
			for (SemanticNode contactDataNode : SNOPS.objects(node, RB.HAS_CONTACT_DATA)) {
				if(contactDataNode.isResourceNode()) {
					ResourceNode contactResource = contactDataNode.asResource();
					String type = nsString(contactResource, RB.HAS_VCARD_TYPE).toUpperCase();
					String typePrfx = "";
					if(!type.isEmpty()) {
						typePrfx = ";TYPE=" +type;
					}
					sb.append("TEL" +typePrfx +":" +nsString(contactResource, RB.HAS_VALUE) +"\n");
				}
			}
			
			sb.append("UID:" +node.getQualifiedName() +"\n");
			sb.append("REV:" +rev +"\n");
			sb.append("END:VCARD");
			out.write(sb.toString().getBytes());
		}
		
		/**
		 * Writes the vCard file content valid to specification 2.1
		 * @param node
		 * @param out
		 * @throws IOException
		 */
		private void write21(final ResourceNode node, final OutputStream out) throws IOException {
			final StringBuilder sb = new StringBuilder();

			String lastname = nsString(node, RB.HAS_LAST_NAME);
			String firstname = nsString(node, RB.HAS_FIRST_NAME);
			
			
			String bday = "";
			SemanticNode bdayNode = SNOPS.fetchObject(node, RB.HAS_DATE_OF_BIRTH);
			if(bdayNode != null && bdayNode.isValueNode()) {
				ValueNode bdayValue = bdayNode.asValue();
				if(bdayValue.getDataType().equals(ElementaryDataType.DATE)) {
					bday = new SimpleDateFormat("yyyyMMdd").format(bdayValue.getTimeValue());
				}
			}

			String street = "";
			String city = "";
			String zipcode = "";
			String country = "";
			SemanticNode addressNode = SNOPS.fetchObject(node, RB.HAS_ADDRESS);
			if(addressNode != null && addressNode.isResourceNode()) {
				ResourceNode adrResource = addressNode.asResource();
				street = nsString(adrResource, RB.HAS_STREET) +" "
						+nsString(adrResource, RB.HAS_HOUSE_NO);
				zipcode = nsString(adrResource, RB.HAS_ZIPCODE);
				SemanticNode cityNode = SNOPS.fetchObject(adrResource, RB.IS_IN_CITY);
				if(cityNode != null && cityNode.isResourceNode()) {
					ResourceNode cityResource = cityNode.asResource();
					city = nsString(cityResource, RB.HAS_NAME);
					SemanticNode countryNode = SNOPS.fetchObject(cityResource, RB.IS_IN_COUNTRY);
					if(countryNode != null && countryNode.isResourceNode()) {
						ResourceNode countryResource = countryNode.asResource();
						country = nsString(countryResource, RB.HAS_NAME);
					}
				}
			}
			
			String rev = new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(Calendar.getInstance().getTime());
			
			sb.append("BEGIN:VCARD\r\n");
			sb.append("VERSION:2.1\r\n");
			sb.append("FN:" +firstname +" " +lastname +"\r\n");
			sb.append("N:" +lastname +";" +firstname +"\r\n");
			sb.append("BDAY:" +bday +"\r\n");
			
			sb.append("ADR:;;" +street +";" +city +";;" +zipcode +";" +country +"\r\n");
			
			for (SemanticNode emailNode : SNOPS.objects(node, RB.HAS_EMAIL)) {
				sb.append("EMAIL;INTERNET:" +SNOPS.string(emailNode) +"\r\n");
			}
			
			for (SemanticNode contactDataNode : SNOPS.objects(node, RB.HAS_CONTACT_DATA)) {
				if(contactDataNode.isResourceNode()) {
					ResourceNode contactResource = contactDataNode.asResource();
					String type = nsString(contactResource, RB.HAS_VCARD_TYPE).toUpperCase();
					String typePrfx = "";
					if(!type.isEmpty()) {
						typePrfx = ";" +type;
					}
					sb.append("TEL" +typePrfx +":" +nsString(contactResource, RB.HAS_VALUE) +"\r\n");
				}
			}
			
			sb.append("UID:" +node.getQualifiedName() +"\r\n");
			sb.append("REV:" +rev +"\r\n");
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
