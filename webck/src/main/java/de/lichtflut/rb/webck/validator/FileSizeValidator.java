/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.validator;

import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

/**
 * <p>
 * Validates the maximum Filesize of an {@link FileUploadField}.
 * Supports only single-uploads by default.
 * </p>
 * Created: Sep 14, 2012
 *
 * @author Ravi Knox
 */
public class FileSizeValidator extends AbstractValidator<List<FileUpload>> {

	private final long maxlimit;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor
	 */
	public FileSizeValidator(final long bytes) {
		this.maxlimit = bytes;
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onValidate(final IValidatable<List<FileUpload>> validatable) {
		if(maxlimit < getUploadSize(validatable)){
			error(validatable);
		}
	}

	/**
	 * Overwrite to support multifile-uploads.
	 * @param validatable
	 * @return the total size of the upload
	 */
	protected long getUploadSize(final IValidatable<List<FileUpload>> validatable) {
		// Suppurtsonly single upload by default
		return validatable.getValue().get(0).getSize();
	}

	/**
	 * @see AbstractValidator#resourceKey()
	 */
	@Override
	protected String resourceKey()
	{
		return "FileSizeValidator.maximum";
	}
}
