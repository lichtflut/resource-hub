/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.util.UUID;

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
	public static final String DELIMITER = "#";

	/**
	 * Encrypts a password with a salt.
	 * @param password The password to encrypt.
	 * @param salt The salt (default-salt if null).
	 * @return credential-String: crypt with the salt included (separated by delimiter '#').
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
		return buildCredentialString(crypted, checkedSalt);
	}
	
	/**
	 * Encrypts a password with a default-salt.
	 * @param password The password to encrypt.
	 * @return credential-String: crypt with the salt included (separated by delimiter '#').
	 */
	public static String encrypt(final String password) {
		return encrypt(password, null);
	}
	
	/**
	 * Encrypts a password with a random salt.
	 * @param password The password to encrypt.
	 * @return credential-String: crypt with the salt included (separated by delimiter '#').
	 */
	public static String encryptWithRandomSalt(final String password) {
		final String salt = UUID.randomUUID().toString();
		return encrypt(password, salt);
	}
	
	/**
	 * Builds a credential-String: crypt with the delimiter '#' and the salt.
	 * @param crypt The return value of {@link RBCrypt}.encrypt()
	 * @param salt The salt that was used for encryption
	 * @return The credential string "crypt#salt"
	 */
	public static String buildCredentialString(final String crypt, final String salt) {
		return crypt +DELIMITER +salt;
	}
	
	/**
	 * Extracts the salt out of a credential string.
	 * @param credentialString The credential string: "crypt#salt".
	 * @return The salt (if credential string has no salt, an empty string is returned).
	 */
	public static String extractSalt(final String credentialString) {
		int index = credentialString.indexOf(DELIMITER);
		String salt = "";
		if(index >= 0) {
			salt = credentialString.substring(index+1);
		}
		return salt;
	}
	
	/**
	 * Checks a password against a given credential.
	 * If the credential doesn't include the salt (separated by the delimiter '#') the default-salt is used. 
	 * @param password The password to check.
	 * @param credential The credential to check against.
	 * @return true if the password is verified, false otherwise.
	 */
	public static boolean verify(final String password, final String credential) {
		String checkCredential = credential;
		String salt = extractSalt(credential);
		if(salt.isEmpty()) {
			checkCredential = buildCredentialString(credential, DEFAULT_SALT);
		}
		return checkCredential.equals(encrypt(password, salt));
	}
}
