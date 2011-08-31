/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.web.models.ReferencedEntityModel;

/**
 * Component for searching and displaying of referenced resources.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public abstract class ResourcePicker extends CKComponent {

	private static final long serialVersionUID = 1L;
	private IModel<IRBEntity> entity;

	// ------------------------------------------------------------

	/**
	 * @param id The wicket ID.
	 * @param entity The exitisting entity to be displayed.
	 */
	public ResourcePicker(final String id, final IModel<IRBEntity> entity) {
		super(id);
		this.entity = entity;
		buildComponent();
	}

	// ------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		initModel(entity);
//		@SuppressWarnings("unchecked")
//		AutoCompleteTextField autoTextfield = new AutoCompleteTextField("inputField",
//				new AbstractAutoCompleteRenderer<IRBEntity>() {
//
//			@Override
//			protected void renderChoice(final IRBEntity object, final Response response,
//					final String criteria) {
//				response.write(getTextValue(object));
//			}
//
//			@Override
//			protected String getTextValue(final IRBEntity object) {
//				String s = "";
//				for (IRBField field : object.getAllFields()) {
//					for (Object o : field.getFieldValues()) {
//						if(o != null){
//							s +=  (o.toString() + ", ");
//						}
//					}
//				}
//				return s;
//			}
//		}) {
//			@Override
//			protected Iterator getChoices(final String input) {
//				System.out.println(entity.getObject().getRBMetaInfo().getSchemaID() + " -SchemaID");
//				List<IRBEntity> entites = getServiceProvider().getRBEntityManagement()
//					.findAll(entity.getObject().getRBMetaInfo().getSchemaID());
//				return entites.iterator();
//			}
//		};
		final List<IRBEntity> entites = getServiceProvider().getRBEntityManagement()
		.findAll(entity.getObject().getRBMetaInfo().getSchemaID());

		IAutoCompleteRenderer<IRBEntity> inforenderer = new AbstractAutoCompleteRenderer<IRBEntity>() {

			@Override
			protected void renderChoice(final IRBEntity entity, final Response response,
					final String criteria) {
				response.write("<div style='float:left; color:red; '>");
				response.write(entity.getID().toString());
				response.write("</div><div style='text-align:right; width:100%;'>");
				response.write(entity.getRBMetaInfo().getSchemaID().toString());
				response.write("</div>");
			}

			@Override
			protected String getTextValue(final IRBEntity entity) {
				return entity.getID().toString();
			}
		};

		TextField<IRBEntity> autoTextfield = new AutoCompleteTextField<IRBEntity>("inputField",
				(IModel<IRBEntity>) getDefaultModel(), inforenderer) {

			@Override
			protected Iterator<IRBEntity> getChoices(final String input) {
				List<IRBEntity> entityList = new ArrayList<IRBEntity>();
				Iterator i = entites.iterator();
				while(i.hasNext()){
					IRBEntity s = (IRBEntity) i.next();
					if(s.toString().contains(input)){
						entityList.add(s);
					}
				}
			return entityList.iterator();
			}
		};

//			new TextField<IRBEntity>("inputField", (IModel<IRBEntity>) getDefaultModel());
//		autoTextfield.add(new AutoCompleteBehavior(new StringAutoCompleteRenderer()){
//
//			@Override
//			protected Iterator getChoices(final String input) {
//				List<IRBEntity> entityList = new ArrayList<IRBEntity>();
//				Iterator i = entites.iterator();
//				while(i.hasNext()){
//					IRBEntity s = (IRBEntity) i.next();
//					if(s.toString().contains(input)){
//						entityList.add(s);
//					}
//				}
//			return entityList.iterator();
//			}
//		});
		add(autoTextfield);

	}

	// ------------------------------------------------------------

	/**
	 * Initializes the Component's DefaultModel.
	 * @param entity - instance of {@link IRBEntity}
	 */
	@SuppressWarnings("serial")
	private void initModel(final IModel<IRBEntity> entity) {
		setDefaultModel(new ReferencedEntityModel(entity) {
			@Override
			public IRBEntity resolve(final ResourceID id) {
				return ResourcePicker.this.getServiceProvider().getRBEntityManagement().find(id);
			}
		});
	}

}
