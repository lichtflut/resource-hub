/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.SNEntity;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.security.User;
import org.arastreju.sge.security.impl.UserImpl;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import de.lichtflut.rb.core.messaging.MessageDescription;
import de.lichtflut.rb.core.messaging.MessageType;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.services.EmailService;

/**
 * <p>
 *  Testclass for {@link EmailServiceImpl}.
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class EmailServiceImplTest {

	private static final String SUBJECT = "Testing Email Service";
	private static final String SENDER = "service@lf.de";
	private static final String SENDER_NAME = "lf Service Crew";
	private static final String RECIPIENT = "coolesGuyEver@ms.de";
	private static final String RECIPIENT_NAME = "Bill Gates";

	@Test
	public void sendSimpleMail() {
		try {
			MessageDescription desc = new MessageDescription(MessageType.UNDEFINED);
			desc.setContent("Hallo World!");
			desc.setSubject(SUBJECT);
			fillMessageDescription(desc);
			EmailService email = new EmailServiceImpl();
			email.sendMail(desc);
			List<Message> inbox = Mailbox.get(RECIPIENT);
			
			assertEquals(1,inbox.size());
			assertEquals(inbox.get(0).getSubject(), SUBJECT);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendConfirmationMail(){
		MessageDescription desc = new MessageDescription(MessageType.PASSWORD_INFORMATION_MAIL);
		fillMessageDescription(desc);
		EmailServiceImpl email = new EmailServiceImpl();
	}
	
	/**
	 * @param desc
	 */
	private void fillMessageDescription(MessageDescription desc) {
		desc.setRecipient(RECIPIENT);
		desc.setRecipientName(RECIPIENT_NAME);
		desc.setSender(SENDER);
		desc.setSenderName(SENDER_NAME);
	}

	static class Mockuser{
		
		public static User getMockUser(){
			ResourceNode node = new SNEntity(new QualifiedName("http://lf.de"));
			node.addAssociation(Aras.HAS_UNIQUE_NAME, new SNValue(ElementaryDataType.STRING, "John Doe"), Aras.IDENT);
			
			User user = new UserImpl(node);
			return null;
		}
		
	}
}
