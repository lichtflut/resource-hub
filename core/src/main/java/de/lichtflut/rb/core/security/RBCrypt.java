/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import de.lichtflut.infra.security.Crypt;


/**
 * <p>
 *  Safe password encryption with salting, hashing and stretching.
 * </p>
 *
 * <p>
 * 	Created 21.02.2012
 * </p>
 *
 * @author Erik Aderhold
 */
public abstract class RBCrypt {

	private static final String DEFAULT_SALT = "d3F4uLt-5alT";

	/**
	 * Encrypts a password with a salt.
	 * @param password The password to encrypt.
	 * @param salt The salt (default-salt if null).
	 * @return The encrypted password.
	 */
	public static String encrypt(final String password, final String salt) {
		String crypted = password;
		String checkedSalt;
		
		if(salt == null || salt.isEmpty()) {
			checkedSalt = DEFAULT_SALT;
		} else {
			checkedSalt = salt;
		}
						
		for(int i = 0; i < 1000; i++) {
			if(i % 2 == 0) {
				crypted = crypted + checkedSalt;
			} else {
				crypted = checkedSalt + crypted;
			}
			crypted = Crypt.md5Hex(crypted);
		}
		return crypted;
	}
	
	/**
	 * Encrypts a password with a default-salt.
	 * @param password The password to encrypt.
	 * @return the encrypted password
	 */
	public static String encrypt(final String password) {
		return encrypt(password, null);
	}
	
	/**
	 * Checks a password against a given md5-hash. 
	 * @param password The password to check.
	 * @param salt The salt for the password.
	 * @param md5Hash The md5-hash to check against.
	 * @return true if the password is verified, false otherwise.
	 */
	public static boolean verify(final String password, final String salt, final String md5Hash) {
		return md5Hash.equals(encrypt(password, salt));
	}
	
	/**
	 * Checks a password against a given md5-hash. !!default-salt is used!! 
	 * @param password The password to check.
	 * @param md5Hash The md5-hash to check against.
	 * @return true if the password is verified, false otherwise.
	 */
	public static boolean verify(final String password, final String md5Hash) {
		return verify(password, null, md5Hash);
	}
}
