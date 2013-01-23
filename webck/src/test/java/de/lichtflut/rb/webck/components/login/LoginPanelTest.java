/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.login;

import java.util.Locale;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.webck.components.login.LoginPanel;

public class LoginPanelTest {

	private WicketTester tester;
	//Passwords:
	private final String passFourNumbers = "1234";
	private final String pass4Digits = "abcd";
	private final String pass8Digits = "abcd1234";
	private final String pass20Digits = "qwertz753pasdfghjkly";
	private final String pass21Digits = "qwertzuio357dfghjklya";
	private final String passNothing = " ";

	//Usernames/E-Mails:
	private final String eMailWithoutAt = "abcabc.de";
	private final String eMailCorrect = "abc@domain.de";
	private final String eMailNothing = " ";

	@Before
	public void setUp() {
		tester = new WicketTester();
		tester.getSession().setLocale(Locale.GERMAN);
	}

	private Panel createPanel(){
		Panel p = new LoginPanel("panel") {

			@Override
			public void onLogin(final LoginData loginData) {
			}
		};
		return p;
	}


	/**
	 * Tests if all Components are in the Panel.
	 */
	@Test
	public void testComponentsInPanel(){
		System.out.println(System.getProperty("java.io.tmpdir"));
		Panel panel = createPanel();
		tester.startComponentInPage(panel);
		tester.assertComponent("panel:form:loginID", TextField.class );
		tester.assertComponent("panel:form:feedback", FeedbackPanel.class);
		tester.assertComponent("panel:form:password", PasswordTextField.class);
		tester.assertComponent("panel:form:stayLoggedIn", CheckBox.class);
		tester.assertComponent("panel:form:login", Button.class);
	}


	/**
	 * Submiting without introducing Username and Password.
	 */
	@Test
	@Ignore("Does not work on english OS / include internationalization")
	public void testLoginPanelWithNoInput() {

		Panel panel = createPanel();
		tester.startComponentInPage(panel);

		FormTester formTester = tester.newFormTester("panel:form");
		formTester.submit();
		tester.assertErrorMessages("Bitte tragen Sie einen Wert im Feld 'E-Mail' ein.", "Bitte tragen Sie einen Wert im Feld 'Passwort' ein.");
	}

	/**
	 * Submitting without introducing Username
	 */
	@Test
	@Ignore("Does not work on english OS / include internationalization")
	public void testLoginPanelWithNoUsername(){

		Panel panel = createPanel();
		tester.startComponentInPage(panel);

		FormTester formTester = tester.newFormTester("panel:form");
		formTester.setValue("password", "12345678");
		formTester.submit();
		tester.assertErrorMessages("Bitte tragen Sie einen Wert im Feld 'E-Mail' ein.");

	}

	/**
	 * Submitting without introducing Password
	 */
	@Test
	@Ignore("Does not work on english OS / include internationalization")
	public void testLoginPanelWithNoPassword(){

		Panel panel = createPanel();
		tester.startComponentInPage(panel);

		FormTester formTester = tester.newFormTester("panel:form");
		formTester.setValue("loginID", "abc@abc.de");
		formTester.submit();
		tester.assertErrorMessages("Bitte tragen Sie einen Wert im Feld 'Passwort' ein.");

	}

	/**
	 * Submitting with wrong Password
	 */
	@Ignore
	public void testLoginPanelWithWrongPassword(){

		Panel panel = createPanel();
		tester.startComponentInPage(panel);

		FormTester formTester = tester.newFormTester("panel:form");
		formTester.setValue("password", "passFourNumbers");
		formTester.submit();

		//tester.assertErrorMessages("Bitte tragen Sie einen Wert im Feld 'Passwort' ein.");

	}

}
