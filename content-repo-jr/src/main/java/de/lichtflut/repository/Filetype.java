/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository;

/**
 * <p>
 * Represents various standard filetypes.
 * </p>
 * Created: Sep 13, 2012
 *
 * @author Ravi Knox
 */
public enum Filetype {

	// Image Types
	JPEG,
	PNG,

	// Text Types
	TXT,

	// Not Specified
	OTHER;

	// ------------------------------------------------------

	public static Filetype getCorrespondingFiletypeFor(final String filetype){
		String s = filetype.replace(".", "").toUpperCase();
		if(JPEG.name().equals(s)){
			return Filetype.JPEG;
		} else if (PNG.name().equals(s)){
			return Filetype.PNG;
		} else if(TXT.name().equals(s)){
			return Filetype.TXT;
		}
		else{
			return Filetype.OTHER;
		}
	}

	@Override
	public String toString(){
		return name().toLowerCase();
	}

}
