/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.models.basic.AbstractDerivedModel;

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
public class RBEntityImageUrlModel extends AbstractDerivedModel<String, RBEntity> {

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
	public String derive(IModel<RBEntity> source) {
		if (source.getObject() == null) {
			return null;
		}
		final RBEntity entity = source.getObject();
		final ResourceID type = entity.getType();
		// TODO: check direct image URL rb:hasImage
		if (RB.PERSON.equals(type)) {
			final RBField field = entity.getField(RB.HAS_EMAIL);
			Object value = field.getValue(0);
			if (value instanceof String) {
				final String email = ((String) value).trim().toLowerCase();
				final String md5 = md5Hex(email);
				return "http://www.gravatar.com/avatar/" + md5 + "?d=retro&s=48";
			}
		}
		return null;
	}

	public static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	public static String md5Hex(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

}
