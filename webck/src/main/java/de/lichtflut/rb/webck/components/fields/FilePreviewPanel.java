/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.impl.FileServiceImpl;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.Filetype;


/**
 * <p>
 * This Panel supports Thumbnails for:
 * <ul>
 * <li>
 * .jpeg
 * </li>
 * </ul>
 * If the FileFormat is not supported, a link pointing to the resources is provided.
 * </p>
 * Created: Sep 12, 2012
 *
 * @author Ravi Knox
 * @param <T>
 */
public class FilePreviewPanel<T> extends Panel {

	@SpringBean
	private FileService fileService;

	// ---------------- Constructor -------------------------

	/**
	 * @param id
	 * @param model
	 */
	public FilePreviewPanel(final String id, final IModel<RBFieldValueModel<?>> model) {
		super(id, model);
		add(createPreview(model));
	}

	/**
	 * @param model
	 * @return
	 */
	private Component createPreview(final IModel<RBFieldValueModel<?>> model) {
		Object locations = model.getObject();
		String location = locations.toString();
		if(fileService.exists(location)){
			ContentDescriptor descriptor = fileService.getData(location);
			Filetype mimeType = descriptor.getMimeType();
			switch (mimeType) {
			case JPEG:
			case PNG:
				return new Fragment("valuefield", "thumbnailFragment", this).add(createThumbnailLink(descriptor));
			default:
				return new Fragment("valuefield", "linkFragment", this).add(createLink(location));
			}
		}else{
			return new Fragment("valuefield", "linkFragment", this).add(createLink(location));
		}
	}

	/**
	 * @param descriptor.getName()
	 * @return
	 */
	private Component createThumbnailLink(final ContentDescriptor descriptor) {
		IModel<String> hrefModel = new Model<String>(descriptor.getPath());
		String href = hrefModel.getObject() + "?domain=" + new CurrentDomainModel().getObject().getQualifiedName();
		hrefModel.setObject("service/content/" + hrefModel.getObject());

		IResource unscaledResource = new DynamicImageResource() {

			@Override
			protected byte[] getImageData(final Attributes attributes) {
				try {
					return IOUtils.toByteArray(descriptor.getData());
				} catch (IOException e) {
					throw new RuntimeException("Error converting Inputstream to byte[] in " + FilePreviewPanel.class, e);
				}
			}
		};
		IModel<String> simpleName = Model.of(FileServiceImpl.getSimpleName(descriptor.getName()));

		ExternalLink link = new ExternalLink("link", hrefModel);
		link.add(new AttributeModifier("target", "_blank"));

		ThumbnailImageResource resource = new ThumbnailImageResource(unscaledResource, 100);
		NonCachingImage thumbnail = new NonCachingImage("thumbnail", resource);

		link.add(thumbnail);

		return link;
	}

	// ------------------------------------------------------

	private Component createLink(final String location) {
		IModel<String> hrefModel = new Model<String>(location);
		String href = hrefModel.getObject() + "?domain=" + new CurrentDomainModel().getObject().getQualifiedName();
		hrefModel.setObject("service/content/" + hrefModel.getObject());

		IModel<String> simpleName = Model.of(FileServiceImpl.getSimpleName(location));
		ExternalLink link = new ExternalLink("link", hrefModel, simpleName);
		link.add(new AttributeModifier("target", "_blank"));
		return link;
	}
}
