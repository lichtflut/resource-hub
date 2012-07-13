/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.security;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.authserver.EmbeddedAuthDomainManager;

/**
 * <p>
 *  Transforms passwordCredentials from old {@link Crypt}-md5Hex() to new {@link RBCrypt}-encrypt() encryption.
 *  Using the default-salt.
 * </p>
 *
 * <p>
 * 	Created 02.03.2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class PasswordCredentialTransformer {

	private final Logger logger = LoggerFactory.getLogger(PasswordCredentialTransformer.class);
	
	private static final List<String> PASSWORD_LIST = new ArrayList<String>();
	private final String storeDirPath;
	
	// ----------------------------------------------------
	
	public PasswordCredentialTransformer(String storeDirPath) {
		this.storeDirPath = storeDirPath;
		
		logger.info("Arastreju store directory given by argument: " +storeDirPath);
		
		// passwords to transform
		PASSWORD_LIST.add("AfdF");
		PASSWORD_LIST.add("root");
		PASSWORD_LIST.add("admin");
		
		// List can be expanded with your own favorite passwords
//		PASSWORD_LIST.add("YOUR_OWN_PWD_HERE");
	}
	
	// -- MAIN ---------------------------------------------
	
	public static void main(String[] args) {
		
		if(args[0].equals(null) || args[0].isEmpty()) {
			throw new IllegalArgumentException("Missing Argument path to arastreju-store directory!!!");
		}
		
		PasswordCredentialTransformer transformer = new PasswordCredentialTransformer(args[0]);
		transformer.transform();
	}
	
	// -----------------------------------------------------
	
	private void transform() {
		final File storeDir = new File(storeDirPath);
		
		ArastrejuProfile profile = ArastrejuProfile.read();
		profile.setProperty(ArastrejuProfile.ARAS_STORE_DIRECTORY, storeDir.getAbsolutePath());
		Arastreju aras = Arastreju.getInstance(profile);
		ArastrejuGate masterGate = aras.openMasterGate();
		
		final EmbeddedAuthDomainManager domainManager = new EmbeddedAuthDomainManager(masterGate.startConversation());
		
		for(RBDomain domain : domainManager.getAllDomains()) {
			logger.info("current domain: " +domain.getName());
			ArastrejuGate gate = aras.openGate(domain.getName());
			changePasswordsInDomain(gate);
		}
	}

	private void changePasswordsInDomain(ArastrejuGate gate) {
		for (String password : PASSWORD_LIST) {
			String oldCredential = Crypt.md5Hex(password);
			
			Query query = gate.startConversation().createQuery();
			query.addField(Aras.HAS_CREDENTIAL, oldCredential);
			
			for(ResourceNode node : query.getResult()) {
				SNOPS.assure(node, Aras.HAS_CREDENTIAL, RBCrypt.encrypt(password));
				logger.info("A password credential for " +password +" was changed!");
			}
		}
	}
	
}
