/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
