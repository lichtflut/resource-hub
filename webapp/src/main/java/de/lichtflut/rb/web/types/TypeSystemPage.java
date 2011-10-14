/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.types;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.mock.MockServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.webck.components.modaldialog.ModalDialog;
import de.lichtflut.rb.webck.components.typesystem.CreateTypePanel;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.SchemaEditorPanel;
import de.lichtflut.rb.webck.components.typesystem.TypeBrowserPanel;

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
public class TypeSystemPage extends RBSuperPage {

	/**
	 * Constructor.
	 */
	public TypeSystemPage() {
		super("Type System");
		
		final IModel<List<SNClass>> typeModel = new ListModel<SNClass>();
		typeModel.setObject(getServiceProvider().getTypeManager().findAll());
		
		final ModalDialog dialog = new ModalDialog("createTypeDialog", Model.of("create type"));
		
		final CreateTypePanel ctp = new CreateTypePanel(ModalDialog.CONTENT_ID) {
			@Override
			public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
				dialog.close();
				final SNClass newType = getServiceProvider().getTypeManager().create(qn);
				typeModel.getObject().add(newType);
				setResponsePage(TypeSystemPage.this);
			}
		};
		dialog.setContent(ctp);
		add(dialog);
		
		add(new WebMarkupContainer("schemaEditor"));
		
		add(new TypeBrowserPanel("typeBrowser", typeModel) {
			@Override
			public void onCreateType(AjaxRequestTarget target) {
				dialog.show();
			}
			
			@Override
			public void onTypeSelected(final SNClass type, final AjaxRequestTarget target) {
				displayEditor(type);
			}
		});
	}
	
	// -----------------------------------------------------
	
	protected void displayEditor(final SNClass type) {
		final ResourceSchema schema = getServiceProvider().getSchemaManager().findByType(type);
		final IModel<List<? extends PropertyRow>> model = Model.ofList(PropertyRow.toRowList(schema));
		final SchemaEditorPanel editor = new SchemaEditorPanel("schemaEditor", model) {
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
	
	// -----------------------------------------------------
	
	protected ServiceProvider getServiceProvider() {
		//return new DefaultRBServiceProvider();
		return MockServiceProvider.getDefaultInstance();
	}
	
}
