/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * Tries to retrieve an image URL for an {@link RBEntity}.
 * </p>
 * 
 * <p>
 * Created Dec 17, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class RBEntityImageUrlModel extends DerivedDetachableModel<String, RBEntity> {

	/**
	 * @param source
	 */
	public RBEntityImageUrlModel(IModel<RBEntity> source) {
		super(source);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String derive(RBEntity entity) {
		final ResourceID type = entity.getType();
		// TODO: check direct image URL rb:hasImage
		if (RB.PERSON.equals(type)) {
			return getEmailHash(entity);
		} else {
			return null;	
		}
	}
	
	// ----------------------------------------------------
	
	private String getEmailHash(RBEntity entity) {
		final RBField field = entity.getField(RB.HAS_EMAIL);
		if (field == null) {
			return null;
		}
		Object value = field.getValue(0);
		if (value instanceof String) {
			final String email = ((String) value).trim().toLowerCase();
			final String md5 = md5Hex(email);
			return "http://www.gravatar.com/avatar/" + md5 + "?d=retro&s=48";
		} else {
			return null;
		}
		
	}

	private String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	private String md5Hex(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

}
