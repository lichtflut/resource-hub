/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

/**
 * <p>
 *  Testing password encryption.
 * </p>
 *
 * <p>
 * 	Created 22.02.2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class RBCryptTest {

	@Test
	public void testEncryption() {
		String password = "password";
		String salt = UUID.randomUUID().toString();
		
		long startTime = System.currentTimeMillis();
		String crypted1 = RBCrypt.encrypt(password, salt);
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("encrypt1: " +password +" = " +crypted1 +" - " +duration +" ms");
		
		startTime = System.currentTimeMillis();
		String crypted2 = RBCrypt.encrypt(password, salt);
		duration = System.currentTimeMillis() - startTime;
		System.out.println("encrypt2: " +password +" = " +crypted2 +" - " +duration +" ms");

		Assert.assertEquals(crypted1, crypted2);
	}
	
	@Test
	public void testVerification() {
		String password = "an0thErPa$$word1";
		String salt = "some_SALT";
		String credential = "bd445095f4a3311eeb71a474c28c4305" +RBCrypt.DELIMITER +salt;
		
		String wrongPassword = "aWr0ngPa$$word";
		
		boolean result;
		
		// correct password
		result = RBCrypt.verify(password, credential);
		Assert.assertTrue(result);
		
		// wrong password
		result = RBCrypt.verify(wrongPassword, credential);
		Assert.assertFalse(result);
	}
	
	@Test
	public void testBuildCredential() {
		String crypt = "bd445095f4a3311eeb71a474c28c4305";
		String salt = "some_SALT";
		
		String expected = crypt + RBCrypt.DELIMITER +salt;
		String actual = RBCrypt.buildCredentialString(crypt, salt);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testExtractSalt() {
		String credential = "71de01c3b0cd2e2c8b9960176ab9e8c6#ThisIsTheSalt";
		String salt = RBCrypt.extractSalt(credential);
		
		Assert.assertEquals("ThisIsTheSalt", salt);
	}
}
