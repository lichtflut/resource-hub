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
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.web.models.ReferencedEntityModel;

/**
 * Component for searching and displaying of referenced resources.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public abstract class ResourcePicker extends CKComponent {

	//TODO: REFACTOR
	private static final long serialVersionUID = 1L;
	private IModel<IRBEntity> entity;
	private String inputSnippet = "";
	private ResourceID type;

	// ------------------------------------------------------------

	/**
	 * @param id - The wicket ID.
	 * @param entity - The existing entity to be displayed.
	 * @param type - {@link ResourceID} defining the RDF:TYPE of the ResourcePicker
	 */
	public ResourcePicker(final String id, final IModel<IRBEntity> entity,final ResourceID type) {
		super(id);
		this.entity = entity;
		this.type = type;
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
		final List<IRBEntity> entites = getServiceProvider().getEntityManager().findAllByType(type);
		IAutoCompleteRenderer<IRBEntity> inforenderer = new AbstractAutoCompleteRenderer<IRBEntity>() {
			@Override
			protected void renderChoice(final IRBEntity entity, final Response response,
					final String criteria) {
				String popupInfo = "";
				for(IRBField field : entity.getAllFields()){
					if(field.getFieldValues().toString().contains(inputSnippet)){
						popupInfo = field.getLabel() + ": ";
						for (Object o : field.getFieldValues()) {
							if(o != null){
								if(field.isResourceReference()){
									IRBEntity e = (IRBEntity) o;
									popupInfo = popupInfo.concat(e.getLabel() + ", ");
								}else{
									popupInfo = popupInfo.concat(o.toString() + ", ");
								}
							}
						}
						popupInfo = popupInfo.substring(0, popupInfo.length()-2);
						break;
					}
				}
				response.write("<div class='resource-picker-value'>");
				response.write(entity.getLabel());
				response.write("</div><div class='resource-picker-info'>");
				response.write(popupInfo);
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
				for (IRBEntity e : entites) {
					String s = "";
					for (IRBField field : e.getAllFields()) {
						if((field.isResourceReference()) && (!field.getFieldValues().isEmpty())){
							for (Object o : field.getFieldValues()) {
								IRBEntity e1 = (IRBEntity) o;
								if(e1 != null){
									s = s.concat(e1.getLabel().toLowerCase());
								}
							}
						}else{
							s = s.concat(field.getFieldValues().toString().toLowerCase());
						}
					}
					if(s.contains(input.toLowerCase())){
						entityList.add(e);
					}
				}
				inputSnippet = input;
			return entityList.iterator();
			}
		};
		autoTextfield.setType(ResourceID.class);
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
				return ResourcePicker.this.getServiceProvider().getEntityManager().find(id);
			}
		});
	}

}
