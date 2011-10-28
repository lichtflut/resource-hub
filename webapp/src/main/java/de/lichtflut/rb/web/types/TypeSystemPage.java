/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.types;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.web.RBBasePage;
import de.lichtflut.rb.web.util.ServiceProviderLocator;
import de.lichtflut.rb.webck.components.modaldialog.ModalDialog;
import de.lichtflut.rb.webck.components.typesystem.CreateResourcePanel;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.SchemaEditorPanel;
import de.lichtflut.rb.webck.components.typesystem.SchemaIOPanel;
import de.lichtflut.rb.webck.components.typesystem.TypeBrowserPanel;
import de.lichtflut.rb.webck.components.typesystem.TypeDefBrowserPanel;
import de.lichtflut.rb.webck.components.typesystem.TypeDefEditorPanel;

/**
 * <p>
 *  Page presenting the components for management of type system.
 * </p>
 *
 * <p>
 * 	Created Sep 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TypeSystemPage extends RBBasePage {

	/**
	 * Constructor.
	 */
	public TypeSystemPage() {
		super("Type System");
		
		final IModel<List<SNClass>> typeModel = new ListModel<SNClass>();
		typeModel.setObject(getServiceProvider().getTypeManager().findAll());
		
		final ModalDialog dialog = new ModalDialog("dialog", Model.of("create type | create type def"));
		add(dialog);
		
		add(new WebMarkupContainer("editor"));
		
		add(new TypeBrowserPanel("typeBrowser", typeModel) {
			@Override
			public void onCreateType(AjaxRequestTarget target) {
				dialog.setContent(new CreateResourcePanel(ModalDialog.CONTENT_ID) {
					public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
						dialog.close();
						final SNClass newType = getServiceProvider().getTypeManager().create(qn);
						typeModel.getObject().add(newType);
						setResponsePage(TypeSystemPage.this);
					}
				});
				dialog.show();
			}
			
			@Override
			public void onTypeSelected(final SNClass type, final AjaxRequestTarget target) {
				displaySchemaEditor(type);
			}
		});
		
		final IModel<List<TypeDefinition>> typeDefModel = new ListModel<TypeDefinition>();
		typeDefModel.setObject(new ArrayList<TypeDefinition>(getServiceProvider().getSchemaManager().findAllTypeDefinitions()));
		
		add(new TypeDefBrowserPanel("propertyTypeDefBrowser", typeDefModel) {
			@Override
			public void onCreateTypeDef(AjaxRequestTarget target) {
				dialog.setContent(new CreateResourcePanel(ModalDialog.CONTENT_ID) {
					public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
						dialog.close();
						final TypeDefinition typeDef = schemaManager().prepareTypeDefinition(qn, qn.getSimpleName());
						schemaManager().store(typeDef);
						typeDefModel.getObject().add(typeDef);
						setResponsePage(TypeSystemPage.this);
					}
				});
				dialog.show();
			}
			
			@Override
			public void onTypeDefSelected(final TypeDefinition def, final AjaxRequestTarget target) {
				displayTypeDefEditor(def);
			}
		});
		
		add(new SchemaIOPanel("schemaIO") {
			protected SchemaManager getSchemaManager() {
				return schemaManager();
			}
		});
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected Component createSideBar(String id) {
		return super.createSideBar(id);
	}
	
	// -----------------------------------------------------
	
	protected void displaySchemaEditor(final SNClass type) {
		final ResourceSchema schema = getServiceProvider().getSchemaManager().findSchemaForType(type);
		final IModel<List<? extends PropertyRow>> model = Model.ofList(PropertyRow.toRowList(schema));
		final SchemaEditorPanel editor = new SchemaEditorPanel("editor", model) {
			@Override
			public void onSave(final AjaxRequestTarget target) {
				final ResourceSchema schema = new ResourceSchemaImpl(type);
				for (PropertyRow row: model.getObject()) {
					final PropertyDeclaration decl = PropertyRow.toPropertyDeclaration(row);
					schema.addPropertyDeclaration(decl);	
				}
				getServiceProvider().getSchemaManager().store(schema);
			}
		};
		TypeSystemPage.this.replace(editor);
		// force redirect
		setResponsePage(TypeSystemPage.this);
	}
	
	protected void displayTypeDefEditor(final TypeDefinition def) {
		final TypeDefinition reloaded = getServiceProvider().getSchemaManager().findTypeDefinition(def.getID());
		final IModel<PropertyRow> model = Model.of(new PropertyRow(reloaded));
		final TypeDefEditorPanel editor = new TypeDefEditorPanel("editor", model) {
			@Override
			public void onSave(final AjaxRequestTarget target) {
				final TypeDefinition definition = PropertyRow.toTypeDefinition(model.getObject());
				getServiceProvider().getSchemaManager().store(definition);
			}
		};
		TypeSystemPage.this.replace(editor);
		// force redirect
		setResponsePage(TypeSystemPage.this);
	}
	
	// -----------------------------------------------------
	
	protected ServiceProvider getServiceProvider() {
		return ServiceProviderLocator.get();
	}
	
	protected SchemaManager schemaManager() {
		return getServiceProvider().getSchemaManager();
	}
	
}
