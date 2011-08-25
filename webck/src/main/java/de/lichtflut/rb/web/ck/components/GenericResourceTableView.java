/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;

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

	//Behavior-Keys
	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The Behavior describes the action which will be executed by the link.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String SHOW_DETAILS = "de.lichtflut.web.ck.show_details.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The Behavior describes the action which will be executed by the link.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String DELETE_ROW_ITEM = "de.lichtflut.web.ck.delete_row_item.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The Behavior describes the action which will be executed by the link.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String UPDATE_ROW_ITEM = "de.lichtflut.web.ck.update_row_item.behavior";

	/**
	 * <p>
	 * This behavior is executed when an attribute of type "resource" is encountered.
	 * It must return a {@link Component} which will be added to the {@link ResourceRegisterPanel}.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 *  <li>{@link String}, wicket-id</li>
	 * 	<li>{@link IRBEntity}, the RBEntity</li>
	 * 	<li>{@link IRBField} instance of {@link IRBField}</li>
	 * </ol>
	 * </p>
	 */
	public static final String RESOURCE_FIELD_BEHAVIOR = "de.lichtflut.web.ck.resource_field_behavior.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The CKBehavior musst return a {@link Component} which will be added
	 * to the {@link GenericResourceTableView}
	 * </p>
	 * <p>
	 * The component-id must be "value".
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 *  <li>{@link String}, wicket-id</li>
	 * 	<li>{@link IRBEntity}, the RBEntity</li>
	 * 	<li>{@link IRBField} instance of {@link IRBField}</li>
	 * </ol>
	 * </p>
	 */
	public static final String ADD_CUSTOM_ROW_ITEM = "de.lichtflut.web.ck.custom_row_item.behavior";

	private Map<String, String> tableHeader = new HashMap<String, String>();
	private List<IRBEntity> entites = new ArrayList<IRBEntity>();

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entites - List of {@link IRBEntity}
	 */
	public GenericResourceTableView(final String id,final List<IRBEntity> entites){
		super(id);
		this.entites = entites;
		buildComponent();
	}

	/**
	 * @param entites -
	 */
	@SuppressWarnings("rawtypes")
	private void addRows(final List<IRBEntity> entites) {
		ListView<IRBEntity> view = new ListView<IRBEntity>("row", entites) {
			@Override
			protected void populateItem(final ListItem item) {
				final IRBEntity e = (IRBEntity) item.getModelObject();
				List<IRBField> fields = sortFieldsForPresentation(e);
				// TODO: Find better solution to display RUD behaviors
				if(getBehavior(SHOW_DETAILS) != null){
					fields.add(null);
				}
//				if(getBehavior(UPDATE_ROW_ITEM) != null){
//					fields.add(null);
//				}
//				if(getBehavior(DELETE_ROW_ITEM) != null){
//					fields.add(null);
//				}
				ListView<Object> data = new ListView<Object>("cell", fields) {
					@Override
					protected void populateItem(final ListItem item) {
						IRBField field =  (IRBField) item.getModelObject();
						String output = "";
						if (field != null){
							for (Object s : field.getFieldValues()) {
								output += s;
							}
							if (getBehavior(ADD_CUSTOM_ROW_ITEM) != null) {
								item.add((Component) getBehavior(
										ADD_CUSTOM_ROW_ITEM).execute("data", e,
										field));
							}else if((getBehavior(RESOURCE_FIELD_BEHAVIOR) != null)
									&& (field.isResourceReference())) {
								item.add((Component) getBehavior(
										RESOURCE_FIELD_BEHAVIOR).execute(
										"data", e, field));
							}else {
								item.add(new Label("data", output));
							}
						}else{
							if(getBehavior(SHOW_DETAILS) != null){
//								item.add(new CKLink("data", "Details", "http://google.com/search=Details",
//										CKLinkType.EXTERNAL_LINK));
//								item.add(new Label("data", "BlaBlaBla"));
//								removeBehavior(SHOW_DETAILS);
								Fragment f = new Fragment("data", "detailsLink",
										getParent().getParent().getParent());
								f.add(new CKLink("details", "Details", "http://google.com/search=Details",
										CKLinkType.EXTERNAL_LINK));
//								f.add(new CKLink("update", "updateLink", "http://google.com/search=Details",
//										CKLinkType.EXTERNAL_LINK));
								item.add(f);
							}
//							if(getBehavior(UPDATE_ROW_ITEM) != null){
//								item.add(new CKLink("data", "Details", "http://google.com/search=Update",
//										CKLinkType.EXTERNAL_LINK));
//								removeBehavior(UPDATE_ROW_ITEM);
//							}
//							if(getBehavior(DELETE_ROW_ITEM) != null){
//								item.add(new CKLink("data", "Details", "http://google.com/search=Delete",
//										CKLinkType.EXTERNAL_LINK));
//								removeBehavior(DELETE_ROW_ITEM);
//							}
						}
					}
				};
				item.add(data);
			}
		};
		this.add(view);
	}

	/**
	 * Sorts the IRBFields according to the table header.
	 *
	 * @param e - instance of {@link IRBEntity}
	 * @return list of {@link IRBField}
	 */
	private List<IRBField> sortFieldsForPresentation(final IRBEntity e) {
		List<IRBField> fields = new ArrayList<IRBField>();
		for (String s : tableHeader.keySet()) {
			fields.add(e.getField(s));
		}
		return fields;
	}

	/**
	 * Fills the Tableheader with the appropriate Label.
	 */
	@SuppressWarnings({ "rawtypes"})
	private void addHeader() {
		//Create List for ListView
		List<String> titles = new ArrayList<String>();
		for (String string : tableHeader.values()) {
			titles.add(string);
		}
		// TODO: FIX
//		if(getBehavior(SHOW_DETAILS) != null){
//			titles.add("Details");
//		}
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

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		indexTableHeader(entites);
		addHeader();
		addRows(entites);
	}
}
