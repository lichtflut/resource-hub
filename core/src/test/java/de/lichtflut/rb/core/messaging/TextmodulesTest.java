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
package de.lichtflut.rb.core.messaging;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 *  Testclass for {@link TextModules}
 * </p>
 *
 * <p>
 * 	Created Feb 17, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class TextmodulesTest {

	private TextModules tm;

	@Before
	public void setUp(){
		tm = new TextModules() {
			protected ResourceBundle getResourceBundle(Locale locale) {
				 ResourceBundle bundle = ResourceBundle.getBundle("de/lichtflut/rb/core/messaging/TextModulesTestBundle", locale);
				 return bundle;
			}
		};
	}
	
	@Test
	public void insertMailForPasswordInformation(){
		String pwdInfo = "Hello, password-information";
		String pwdInfoSub = "Password Information";
		MessageDescription desc = new MessageDescription(Locale.ENGLISH);
		desc.setType(MessageType.PASSWORD_INFORMATION_MAIL);
		tm.insertMailFor(desc);
		
		assertEquals(pwdInfo, desc.getContent());
		assertEquals(pwdInfoSub, desc.getSubject());
	}
	
	@Test
	public void insertMailForAccountActivation(){
		String accActivatedInfo = "Hello, account-activated-information";
		String accActivatedInfoSub = "Account Activation";
		MessageDescription desc = new MessageDescription(Locale.ENGLISH);
		desc.setType(MessageType.ACCOUNT_ACTIVATED_MAIL);
		tm.insertMailFor(desc);
		
		assertEquals(accActivatedInfo, desc.getContent());
		assertEquals(accActivatedInfoSub, desc.getSubject());
	}
	
	@Test
	public void insertMailForRegistrConfirmation(){
		String regInfo = "Hello ,registration-confirmation";
		String regInfoSub = "Registration confirmation";
		MessageDescription desc = new MessageDescription(Locale.ENGLISH);
		desc.setType(MessageType.REGISTRATION_CONFIRMATION_MAIL);
		tm.insertMailFor(desc);
		
		assertEquals(regInfo, desc.getContent());
		assertEquals(regInfoSub, desc.getSubject());
	}
	
	@Test
	public void insertGermanMailforAccActivated(){
		String accActivated = "Hallo, account-aktiviert-information";
		String accActivatedsub = "Account Aktiviert";
		MessageDescription desc = new MessageDescription(Locale.GERMANY);
		desc.setType(MessageType.ACCOUNT_ACTIVATED_MAIL);
		tm.insertMailFor(desc);
		
		assertEquals(accActivated, desc.getContent());
		assertEquals(accActivatedsub, desc.getSubject());
	}
	
}
