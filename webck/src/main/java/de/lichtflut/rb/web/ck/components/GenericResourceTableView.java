/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class GenericResourceTableView extends CKComponent {

	private Map<String, String> tableHeader = new HashMap<String, String>();

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entites - List of {@link IRBEntity}
	 */
	public GenericResourceTableView(final String id,final List<IRBEntity> entites){
		super(id);


		indexTableHeader(entites);
		addHeader();
		addRows(entites);
	}

	/**
	 * @param entites -
	 */
	@SuppressWarnings("rawtypes")
	private void addRows(final List<IRBEntity> entites) {
		this.add(new ListView<IRBEntity>("row", entites) {
			@Override
			protected void populateItem(final ListItem item) {
				final IRBEntity e = (IRBEntity) item.getModelObject();
				// for (final IRBEntity entity : entites) {
				List<Object> fields = prepareFields(e);
				ListView<Object> data = new ListView<Object>("cell", fields) {
					@SuppressWarnings("unchecked")
					@Override
					protected void populateItem(final ListItem item) {
						List<Object> values = (List<Object>) item.getModelObject();
						String output = "";
						for (Object s : values) {
							if(s == null){
								s = "";
							}
							output += s;
						}
						item.add(new Label("data", output));
					}
				};
				item.add(data);
				// }
			}

			private List<Object> prepareFields(final IRBEntity e) {
				List<Object> fields = new ArrayList<Object>();
				for (String s : tableHeader.keySet()) {
					fields.add(e.getField(s).getFieldValues());
				}
				return fields;
			};
		});
	}

	/**
	 * Fills the Tableheader with the appropriate Label.
	 */
	@SuppressWarnings({ "rawtypes"})
	private void addHeader() {
		List<String> titles = new ArrayList<String>();
		for (String string : tableHeader.values()) {
			titles.add(string);
		}
		this.add(new ListView<String>("tableHeader", titles){

			@Override
			protected void populateItem(final ListItem item) {
					item.add(new Label("headerData", (String) item.getModelObject()));
			}
		});
	}

	/**
	 * Indexes all the {@link IRBField} names.
	 * @param entites -
	 */
	private void indexTableHeader(final List<IRBEntity> entites) {
		for (IRBEntity entity : entites) {
			for (IRBField field : entity.getAllFields()) {
				if(!tableHeader.containsKey(field.getFieldName())) {
					tableHeader.put(field.getFieldName(), field.getLabel());
				}
			}
		}
	}
}
