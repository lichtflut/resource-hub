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

	protected static final String DELIMITER = "#";
	
	private static final String DEFAULT_SALT = "d3F4uLt-5alT";
	
	// ----------------------------------------------------

	/**
	 * Encrypts a password with a salt.
	 * @param password The password to encrypt.
	 * @param salt The salt (default-salt if null).
	 * @return credential-String: crypt with the salt included (separated by delimiter '#').
	 */
	public static String encrypt(final String password, final String salt) {
		final String checkedSalt;
		if(salt == null || salt.isEmpty()) {
			checkedSalt = DEFAULT_SALT;
		} else {
			checkedSalt = salt;
		}

		String crypted = password;
		for(int i = 0; i < 1000; i++) {
			if(i % 2 == 0) {
				crypted = crypted + checkedSalt;
			} else {
				crypted = checkedSalt + crypted;
			}
			crypted = md5Hex(crypted);
		}
		return buildCredentialString(crypted, checkedSalt);
	}

    /**
	 * Encrypts a password with a default-salt.
	 * @param password The password to encrypt.
	 * @return credential-String: crypt with the salt included (separated by delimiter '#').
	 */
	public static String encrypt(final String password) {
		return encrypt(password, DEFAULT_SALT);
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
		return crypt + DELIMITER +salt;
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

	/**
	 * Generates a random sequence of characters.
	 * @param length The length of the random.
	 * @return The sequence.
	 */
	public static String random(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char c = (char) (65 + (Math.random() * 50));
			sb.append(c);
		}
		return sb.toString();
	}

    public static String md5Hex(String plain) {
        return Crypt.md5Hex(plain);
    }
}
