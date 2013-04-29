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
