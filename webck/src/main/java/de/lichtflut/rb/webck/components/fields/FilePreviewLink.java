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
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.impl.FileServiceImpl;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.Filetype;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;


/**
 * <p>
 * 
 * This Link creates a link that provides Thumbnails for:
 * <ul>
 * <li>
 * 	jpeg
 * </li><li>
 * 	png
 * </li>
 * </ul>
 * If the File Format is not supported, a simple {@link ExternalLink} pointing to the resources is provided.
 * </p>
 * Created: Sep 12, 2012
 *
 * @author Ravi Knox
 */
public class FilePreviewLink extends Panel {

	@SpringBean
	private FileService fileService;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * The File will be fetched by through the {@link FileService}
	 * @param id - wicket:id
	 * @param model - the path pointing the file.
	 */
	public FilePreviewLink(final String id, final IModel<String> model) {
		super(id, model);
		add(createPreview(model));
	}

	// ------------------------------------------------------

	private Component createPreview(final IModel<String> model) {
		String location = model.getObject();
		if(fileService.exists(location)){
			ContentDescriptor descriptor = fileService.getData(location);
			return createFragment(location, descriptor, descriptor.getMimeType());
		}else{
			ContentDescriptor dummy = new ContentDescriptorBuilder().name(location).build();
			return new Fragment("valuefield", "linkFragment", this).add(createLink(dummy));
		}
	}

	private Component createFragment(final String location, final ContentDescriptor descriptor, final Filetype mimeType) {
		switch (mimeType) {
		case JPEG:
		case PNG:
			return new Fragment("valuefield", "thumbnailFragment", this).add(createThumbnailLink(descriptor));
		default:
			return new Fragment("valuefield", "linkFragment", this).add(createLink(descriptor));
		}
	}

	private Component createThumbnailLink(final ContentDescriptor descriptor) {
		String href = getLinkLocation(descriptor.getPath());
		IResource unscaledResource = getIResource(descriptor);

		ThumbnailImageResource resource = new ThumbnailImageResource(unscaledResource, 100);
		NonCachingImage thumbnail = new NonCachingImage("thumbnail", resource);

		ExternalLink link = new ExternalLink("link", href);
		link.add(new AttributeModifier("target", "_blank"));
		link.add(thumbnail);

		return link;
	}

	private String getLinkLocation(final String location) {
		String href = location;
		// For Authorization purposes in REST service
		href = "service/content/" + href + "?domain=" + new CurrentDomainModel().getObject().getQualifiedName();
		return href;
	}

	private IResource getIResource(final ContentDescriptor descriptor) {
		IResource unscaledResource = new DynamicImageResource() {

			@Override
			protected byte[] getImageData(final Attributes attributes) {
				try {
					return IOUtils.toByteArray(descriptor.getData());
				} catch (IOException e) {
					throw new RuntimeException("Error converting Inputstream to byte[] in " + FilePreviewLink.class, e);
				}
			}
		};
		return unscaledResource;
	}

	// ------------------------------------------------------

	private Component createLink(final ContentDescriptor descriptor) {
		String href = getLinkLocation(descriptor.getPath());

		String simpleName = FileServiceImpl.getSimpleName(descriptor.getPath());

		ExternalLink link = new ExternalLink("link", href, simpleName);
		link.add(new AttributeModifier("target", "_blank"));
		return link;
	}
}
