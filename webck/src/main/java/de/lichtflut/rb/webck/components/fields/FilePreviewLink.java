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
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.impl.LinkProvider;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;
import de.lichtflut.rb.core.content.ContentDescriptor;
import de.lichtflut.rb.core.content.ContentDescriptorBuilder;

/**
 * <p>
 * 
 * This Link creates a link that provides Thumbnails for:
 * <ul>
 * <li>
 * jpeg</li>
 * <li>
 * png</li>
 * </ul>
 * If the File Format is not supported, a simple {@link ExternalLink} pointing to the resources is
 * provided.
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
	 * Constructor. The File will be fetched by through the {@link FileService}
	 * 
	 * @param id - wicket:id
	 * @param model - the id pointing the file.
	 */
	public FilePreviewLink(final String id, final IModel<String> model) {
		super(id, model);
		add(createPreview(model));
	}

	// ------------------------------------------------------

	private Component createPreview(final IModel<String> model) {
		if (fileService.exists(model.getObject())) {
			IModel<ContentDescriptor> descriptor = new LoadableDetachableModel<ContentDescriptor>() {

				@Override
				protected ContentDescriptor load() {
					return fileService.getData(model.getObject());
				}
			};
			return createFragment(descriptor);
		} else {
			ContentDescriptor dummy = new ContentDescriptorBuilder().name(model.getObject()).build();
			Component fragment = new Fragment("valuefield", "linkFragment", this).add(createLink(Model.of(dummy)));
			return fragment;
		}
	}

	private Component createFragment(final IModel<ContentDescriptor> descriptor) {
		switch (descriptor.getObject().getMimeType()) {
			case JPEG:
			case JPG:
			case PNG:
				return new Fragment("valuefield", "thumbnailFragment", this).add(createThumbnailLink(descriptor));
			default:
				return new Fragment("valuefield", "linkFragment", this).add(createLink(descriptor));
		}
	}

	private Component createThumbnailLink(final IModel<ContentDescriptor> descriptor) {
		String contextPath = RequestCycle.get().getRequest().getContextPath();
		String href = contextPath + "/" + getLinkLocation(descriptor.getObject().getID());
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

	private IResource getIResource(final IModel<ContentDescriptor> descriptor) {
		IResource unscaledResource = new DynamicImageResource() {

			@Override
			protected byte[] getImageData(final Attributes attributes) {
				try {
					return IOUtils.toByteArray(descriptor.getObject().getData());
				} catch (IOException e) {
					throw new RuntimeException("Error converting Inputstream to byte[] in " + FilePreviewLink.class, e);
				}
			}
		};
		return unscaledResource;
	}

	private Component createLink(final IModel<ContentDescriptor> descriptor) {
		IModel<String> href = new DerivedModel<String, ContentDescriptor>(descriptor) {

			@Override
			protected String derive(final ContentDescriptor original) {
				String contextPath = RequestCycle.get().getRequest().getContextPath();
				return contextPath + "/" + (original.getID());
			}
		};

		IModel<String> simpleName = new DerivedModel<String, ContentDescriptor>(descriptor) {

			@Override
			protected String derive(final ContentDescriptor original) {
				return LinkProvider.getSimpleNameFor(original.getID());
			}
		};

		ExternalLink link = new ExternalLink("link", href, simpleName);
		link.add(new AttributeModifier("target", "_blank"));
		return link;
	}
}
