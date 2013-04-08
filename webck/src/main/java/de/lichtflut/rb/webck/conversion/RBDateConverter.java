/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.DateConverter;

import de.lichtflut.rb.webck.common.RBWebSession;

/**
 * <p>
 * [TODO Insert description here.]
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
