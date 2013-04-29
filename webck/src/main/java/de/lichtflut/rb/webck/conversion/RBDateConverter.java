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
package de.lichtflut.rb.webck.conversion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.DateConverter;

import de.lichtflut.rb.webck.common.RBWebSession;

/**
 * <p>
 * Converter for Date values.
 * </p>
 * Created: Apr 8, 2013
 *
 * @author Ravi Knox
 */
public class RBDateConverter extends DateConverter {

	@Override
	public DateFormat getDateFormat(final Locale locale) {
		if(Locale.ENGLISH.getLanguage() == locale.getLanguage()){
			return new SimpleDateFormat("MM/dd/yyyy", RBWebSession.get().getLocale());
		}else if(Locale.GERMANY.getLanguage() == locale.getLanguage()){
			return new SimpleDateFormat("dd.MM.yyyy", RBWebSession.get().getLocale());
		}
		else{
			return new SimpleDateFormat("MM/dd/yy", RBWebSession.get().getLocale());
		}
	}
}
