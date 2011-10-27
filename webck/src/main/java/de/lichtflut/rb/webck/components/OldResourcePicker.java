/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

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

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.models.ReferencedEntityModel;

/**
 * Component for searching and displaying of referenced resources.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
@Deprecated
public abstract class OldResourcePicker extends CKComponent {

	//TODO: REFACTOR
	private static final long serialVersionUID = 1L;
	private IModel<RBEntity> entity;
	private String inputSnippet = "";
	private ResourceID type;

	// ------------------------------------------------------------

	/**
	 * @param id - The wicket ID.
	 * @param entity - The existing entity to be displayed.
	 * @param type - {@link ResourceID} defining the RDF:TYPE of the ResourcePicker
	 */
	public OldResourcePicker(final String id, final IModel<RBEntity> entity,final ResourceID type) {
		super(id);
		this.entity = entity;
		this.type = type;
		buildComponent();
	}

	// ------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({"serial", "unchecked" })
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		initModel(entity);
		final List<RBEntity> entites = getServiceProvider().getEntityManager().findAllByType(type);
		IAutoCompleteRenderer<RBEntity> inforenderer = new AbstractAutoCompleteRenderer<RBEntity>() {
			@Override
			protected void renderChoice(final RBEntity entity, final Response response,
					final String criteria) {
				String popupInfo = "";
				for(RBField field : entity.getAllFields()){
					if(field.getValues().toString().contains(inputSnippet)){
						popupInfo = field.getLabel() + ": ";
						for (Object o : field.getValues()) {
							if(o != null){
								if(field.isResourceReference()){
									RBEntity e = (RBEntity) o;
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
			protected String getTextValue(final RBEntity entity) {
				return entity.getID().toString();
			}
		};
		TextField<RBEntity> autoTextfield = new AutoCompleteTextField<RBEntity>("inputField",
				(IModel<RBEntity>) getDefaultModel(), inforenderer) {
			@Override
			protected Iterator<RBEntity> getChoices(final String input) {
				List<RBEntity> entityList = new ArrayList<RBEntity>();
				for (RBEntity e : entites) {
					String s = "";
					for (RBField field : e.getAllFields()) {
						if((field.isResourceReference()) && (!field.getValues().isEmpty())){
							for (Object o : field.getValues()) {
								RBEntity e1 = (RBEntity) o;
								if(e1 != null){
									s = s.concat(e1.getLabel().toLowerCase());
								}
							}
						}else{
							s = s.concat(field.getValues().toString().toLowerCase());
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
	 * @param entity - instance of {@link RBEntity}
	 */
	@SuppressWarnings("serial")
	private void initModel(final IModel<RBEntity> entity) {
		setDefaultModel(new ReferencedEntityModel(entity) {
			@Override
			public RBEntity resolve(final ResourceID id) {
				return OldResourcePicker.this.getServiceProvider().getEntityManager().find(id);
			}
		});
	}

}
