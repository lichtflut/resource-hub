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

	private final long maximum;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor
	 */
	public FileSizeValidator(final long bytes) {
		this.maximum = bytes;
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onValidate(final IValidatable<List<FileUpload>> validatable) {
		if(maximum < getUploadSize(validatable)){
			error(validatable);
		}
	}

	/**
	 * Overwrite to support multifile-uploads.
	 * @param validatable
	 * @return the total size of the upload
	 */
	protected long getUploadSize(final IValidatable<List<FileUpload>> validatable) {
		// Supports only single upload by default
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
