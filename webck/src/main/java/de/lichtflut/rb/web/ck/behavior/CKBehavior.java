/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.behavior;

import java.io.Serializable;

/**
 * [TODO Insert description here.
 *
 * Created: Jun 20, 2011
 *
 * @author Ravi Knox
 */
public interface CKBehavior extends Serializable {

	/**
	 * TODO: DESCRIPTION.
	 * @param objects -
	 * @return {@link Object}
	 */
	Object execute(Object...objects);

}
