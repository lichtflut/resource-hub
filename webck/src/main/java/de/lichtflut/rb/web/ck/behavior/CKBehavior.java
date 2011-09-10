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

	CKBehavior VOID_BEHAVIOR = new CKBehavior() {
		private static final long serialVersionUID = 1L;
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object execute(final Object... objects) {
			return null;
		}
	};

	/**
	 * TODO: DESCRIPTION.
	 * @param objects -
	 * @return {@link Object}
	 */
	Object execute(Object...objects);

}
