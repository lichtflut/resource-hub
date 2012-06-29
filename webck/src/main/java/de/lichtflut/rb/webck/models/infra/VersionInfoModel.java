/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.infra;

import de.lichtflut.rb.webck.common.VersionInfo;
import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * <p>
 *  Model providing Version Info.
 * </p>
 *
 * <p>
 * 	Created Mar 2, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class VersionInfoModel extends AbstractReadOnlyModel<VersionInfo> {

	private static final MetaDataKey<VersionInfo> key = new MetaDataKey<VersionInfo>() {};

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public VersionInfo getObject() {
		return getVersionInfo();
	}
	
	// ----------------------------------------------------
	
	private static VersionInfo getVersionInfo() {
		final Application application = Application.get();
		VersionInfo info = application.getMetaData(key);
		if (info == null) {
			info = new VersionInfo();
			application.setMetaData(key, info);
		}
		return info;
	}

}
