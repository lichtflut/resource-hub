/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 * This class provides some methods to create URLs of {@link RBEntity}s.
 * </p>
 * Created: Aug 15, 2012
 *
 * @author Ravi Knox
 */
public class LinkProvider {

	private final String hostUrl;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param hostURL - the host-url to which the path will be dynamically added.
	 */
	public LinkProvider(final String hostURL){
		Validate.notNull(hostURL);
		Validate.notEmpty(hostURL);

		this.hostUrl = hostURL;
	}

	// ------------------------------------------------------

	public String getRepositoryLinkFor(final IModel<RBEntity> model, final Object value) {
		String fileName = ((FileUpload) value).getClientFileName();
		String path = model.getObject().getType().toURI();
		path = path + "/" + model.getObject().getID();
		path = path.replace("http://", "").replace(".", "/").replace("#", "/");
		if(!hostUrl.endsWith("/")){
			path = "/" + path;
		}
		path = hostUrl + path;
		path = path.concat("/" + fileName);
		return path;
	}

}
