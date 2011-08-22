/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class GenericResourcesViewPanel extends CKComponent {

	private Set<String> tableHeader = new HashSet<String>();

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entites - List of {@link IRBEntity}
	 */
	public GenericResourcesViewPanel(final String id,final List<IRBEntity> entites){
		super(id);
		RepeatingView header = new RepeatingView("ckTableHeader");
		RepeatingView rows = new RepeatingView("ckTableRow");

		for (IRBEntity entity : entites) {
			for (IRBField field : entity.getAllFields()) {
				if(!tableHeader.contains(field.getFieldName())) {
					tableHeader.add(field.getFieldName());
				}
				Fragment f = new Fragment(header.newChildId(), "header", this);
				f.add(new Label("headerData", field.getFieldValues().toString()));
				header.add(f);
			}
		}
//		add(header);
//		add(rows);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
