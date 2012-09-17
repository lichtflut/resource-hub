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
	JPG,
	PNG,

	// Text Types
	TXT,

	// Not Specified
	OTHER;

	// ------------------------------------------------------

	public static Filetype getCorrespondingFiletypeFor(final String filetype){
		String original = filetype.replace(".", "").toUpperCase();
		if(JPEG.name().equals(original)){
			return Filetype.JPEG;
		} else if(JPG.name().equals(original)){
			return Filetype.JPG;
		}
		else if (PNG.name().equals(original)){
			return Filetype.PNG;
		} else if(TXT.name().equals(original)){
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
