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
		String md5Hash = "bd445095f4a3311eeb71a474c28c4305";
		
		String wrongPassword = "aWr0ngPa$$word";
		String wrongSalt = "badSalt";
		
		boolean result;
		
		// everything is fine
		result = RBCrypt.verify(password, salt, md5Hash);
		Assert.assertTrue(result);
		
		// wrong password
		result = RBCrypt.verify(wrongPassword, salt, md5Hash);
		Assert.assertFalse(result);
		
		// wrong salt
		result = RBCrypt.verify(password, wrongSalt, md5Hash);
		Assert.assertFalse(result);
		
		// wrong password and wrong salt
		result = RBCrypt.verify(wrongPassword, wrongSalt, md5Hash);
		Assert.assertFalse(result);
		
		// right password but no salt
		result = RBCrypt.verify(password, null, md5Hash);
		Assert.assertFalse(result);
	}
}
