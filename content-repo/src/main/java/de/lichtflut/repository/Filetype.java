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
