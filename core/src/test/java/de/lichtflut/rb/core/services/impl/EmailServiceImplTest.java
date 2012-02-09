///*
// * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
// */
//package de.lichtflut.rb.core.services.impl;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.internet.AddressException;
//
//import org.arastreju.sge.apriori.Aras;
//import org.arastreju.sge.model.ElementaryDataType;
//import org.arastreju.sge.model.nodes.ResourceNode;
//import org.arastreju.sge.model.nodes.SNValue;
//import org.arastreju.sge.model.nodes.views.SNEntity;
//import org.arastreju.sge.naming.QualifiedName;
//import org.arastreju.sge.security.User;
//import org.arastreju.sge.security.impl.UserImpl;
//import org.junit.Test;
//import org.jvnet.mock_javamail.Mailbox;
//
//import de.lichtflut.rb.core.messaging.MessageDescription;
//import de.lichtflut.rb.core.messaging.MessageType;
//import de.lichtflut.rb.core.messaging.TextModules;
//import de.lichtflut.rb.core.services.EmailService;
//
///**
// * <p>
// *  Testclass for {@link EmailServiceImpl}.
// * </p>
// *
// * <p>
// * 	Created Feb 1, 2012
// * </p>
// *
// * @author Ravi Knox
// */
//public class EmailServiceImplTest {
//
//	private static final String SUBJECT = "Testing Email Service";
//	private static final String SENDER = "service@lf.de";
//	private static final String SENDER_NAME = "lf Service Crew";
//	private static final String RECIPIENT = "coolesGuyEver@ms.de";
//	private static final String RECIPIENT_NAME = "Bill Gates";
//	private static final String PASSWORD = "123abc";
//
//	@Test
//	public void sendSimpleMail() {
//		try {
//			MessageDescription desc = new MessageDescription(null);
//			desc.setType(MessageType.PASSWORD_INFORMATION_MAIL);
//			fillMessageDescription(desc);
//			desc.setContent(TextModules.getMailFor(desc));
//			desc.setSubject(SUBJECT);
//			EmailService email = new EmailServiceImpl();
//			email.sendPasswordInformation(user, password, locale)(desc);
//			displayMessageOnConsole();
//			List<Message> inbox = Mailbox.get(RECIPIENT);
//			
//			assertEquals(1,inbox.size());
//			assertEquals(inbox.get(0).getSubject(), SUBJECT);
//		} catch (AddressException e) {
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @throws IOException
//	 * @throws MessagingException
//	 * @throws AddressException
//	 */
//	private void displayMessageOnConsole() {
//		try {
//			System.out.println(Mailbox.get(RECIPIENT).get(0).getContent());
//		} catch (AddressException e) {
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void sendConfirmationMail(){
//		MessageDescription desc = new MessageDescription(null);
//		desc.setType(MessageType.REGISTRATION_CONFIRMATION_MAIL);
//		fillMessageDescription(desc);
//		desc.setContent(TextModules.getMailFor(desc));
//		EmailServiceImpl email = new EmailServiceImpl();
////		email.sendPasswordInformation(user, password)
//	}
//	
//	/**
//	 * @param desc
//	 */
//	private void fillMessageDescription(MessageDescription desc) {
//		desc.setRecipient(RECIPIENT);
//		desc.setRecipientName(RECIPIENT_NAME);
//		desc.setSender(SENDER);
//		desc.setSenderName(SENDER_NAME);
//		desc.setPassword(PASSWORD);
//	}
//}
