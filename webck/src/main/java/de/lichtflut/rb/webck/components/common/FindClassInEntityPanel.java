/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ListAction;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 * Search instances of a given RDF.CLASS in a RBEntity.
 * </p>
 * Created: Mar 18, 2013
 *
 * @author Ravi Knox
 */
//TODO Find better name for this class
public class FindClassInEntityPanel extends Panel {

	@SpringBean
	private SemanticNetworkService networkService;
	@SpringBean
	private SchemaManager schemaManager;

	private final IModel<ResourceID> wanted;
	private final IModel<ResourceID> target;
	private final IModel<List<ResourceNode>> result;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param id Component ID
	 */
	public FindClassInEntityPanel(final String id) {
		super(id);
		this.wanted = new Model<ResourceID>();
		this.target = new Model<ResourceID>();
		this.result = new ListModel<ResourceNode>(new ArrayList<ResourceNode>());

		initPanel();
	}

	/**
	 * Constructor.
	 * @param id Component ID
	 */
	public FindClassInEntityPanel(final String id, final IModel<ResourceID> wanted, final IModel<ResourceID> target) {
		super(id);
		this.wanted = wanted;
		this.target = target;
		this.result = new ListModel<ResourceNode>(new ArrayList<ResourceNode>());

		initPanel();
	}

	// ------------------------------------------------------

	protected void initPanel() {
		add(new PanelTitle("header", new ResourceModel("header.title")));

		Form<?> form = new Form<Void>("form");
		form.add(new ClassPickerField("classPicker", wanted));
		form.add(new EntityPickerField("entityPicker", target));
		form.add(new AjaxButton("search"){
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				buildResult();
				RBAjaxTarget.add(FindClassInEntityPanel.this);
			}
		});
		add(form);
		createResultList("result");

		setOutputMarkupId(true);
	}

	private void createResultList(final String string) {
		ColumnConfiguration conf = new ColumnConfiguration(ListAction.VIEW);
		conf.addColumnByPredicate(RDFS.LABEL);
		ResourceListPanel list = new ResourceListPanel(string, result, new Model<ColumnConfiguration>(conf));
		list.add(ConditionalBehavior.visibleIf(ConditionalModel.isNotEmpty(result)));
		add(list);
	}

	private void buildResult() {
		ResourceNode node = networkService.find(target.getObject().getQualifiedName());
		List<ResourceNode> instances = new ArrayList<ResourceNode>();

		findInstancesRecursive(node, instances);
		result.getObject().addAll(instances);

	}

	protected void findInstancesRecursive(final ResourceNode node, final List<ResourceNode> instances) {
		SNClass schemaType = SchemaIdentifyingType.of(node);
		ResourceSchema schema = schemaManager.findSchemaForType(schemaType);


		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			if(decl.getConstraint() != null){
				if(decl.getConstraint().getTypeConstraint() != null){
					if (!SNOPS.objects(node, decl.getPropertyDescriptor()).isEmpty()) {
						ResourceID typeConstraint = decl.getConstraint().getTypeConstraint();
						SNClass typeConstraintSchema = SchemaIdentifyingType.of(typeConstraint);
						for (ResourceNode resourceNode : SNOPS.objectsAsResources(node, decl.getPropertyDescriptor())) {

							SNOPS.objectsAsResources(resourceNode, RDFS.CLASS);
							for (ResourceNode  rdfType : SNOPS.objectsAsResources(resourceNode, RDF.TYPE)) {
								if(rdfType.equals(wanted.getObject())){
									instances.add(resourceNode);

								}
							}
							findInstancesRecursive(resourceNode, instances);
						}
					}
				}
			}
		}
	}
}
