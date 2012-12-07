/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.common.EntityType;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

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
public class RBEntityImageUrlModel extends DerivedDetachableModel<String, ResourceNode> {

	/**
	 * @param source
	 */
	public RBEntityImageUrlModel(IModel<ResourceNode> source) {
		super(source);
	}

	// ----------------------------------------------------

	@Override
	public String derive(ResourceNode entity) {
		final ResourceID type = EntityType.typeOf(entity);
		// TODO: check direct image URL rb:hasImage
		if (RB.PERSON.equals(type)) {
			return getEmailHash(entity);
		} else {
			return null;	
		}
	}
	
	// ----------------------------------------------------
	
	private String getEmailHash(ResourceNode entity) {
        SemanticNode emailNode = SNOPS.fetchObject(entity, RB.HAS_EMAIL);
		if (emailNode == null || !emailNode.isValueNode()) {
			return null;
		}
		String email = emailNode.toString().trim().toLowerCase();
        final String md5 = md5Hex(email);
        return "http://www.gravatar.com/avatar/" + md5 + "?d=retro&s=48";
	}

	private String hex(byte[] array) {
		StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
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
