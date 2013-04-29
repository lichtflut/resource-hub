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
package de.lichtflut.rb.application.custom;

import org.junit.Test;

import de.lichtflut.rb.application.RBWebTest;

/**
 * <p>
 * Testclass for {@link RequestAccountPage}.
 * </p>
 * Created: Jan 23, 2013
 *
 * @author Ravi Knox
 */
public class RequestAccountPageTest extends RBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.application.custom.RequestAccountPage#RequestAccountPage()}.
	 */
	@Test
	public void testRequestAccountPage() {
		tester.startPage(RequestAccountPage.class);

		tester.assertRenderedPage(RequestAccountPage.class);
	}

}
