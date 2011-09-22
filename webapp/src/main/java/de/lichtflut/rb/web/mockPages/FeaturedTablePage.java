/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.mock.MockRBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.CKLink;
import de.lichtflut.rb.web.ck.components.CKLinkType;
import de.lichtflut.rb.web.ck.components.ResourceTableView;

/**
 * Sample Page of a {@link ResourceTableView} using its features.
 *
 * Created: Sep 14, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class FeaturedTablePage extends RBSuperPage {

	
	private static ServiceProvider provider = null;
	
	/**
	 * Singleton pattern: There will be only one instance per runtime.
	 * @return {@link ServiceProvider}
	 */
	public static ServiceProvider getRBServiceProvider(){
		if(provider==null) {
			provider= new MockRBServiceProvider();
		}
		return provider;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @param title
	 */
	public FeaturedTablePage() {
		super("Full Featured Preview");
		ResourceID type = MockNewRBEntityFactory.createPerson().getType();
		ResourceTableView view =
			new ResourceTableView("mockEmployeeView", getRBServiceProvider().getEntityManager().findAllByType(type)){
			@Override
			public ServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
			public CKComponent setViewMode(final ViewMode mode) {return null;}

			protected void onShowDetails(RBEntity entity) {
			}
		};
		view.addBehavior(ResourceTableView.SHOW_DETAILS, CKBehavior.VOID_BEHAVIOR);
		view.addBehavior(ResourceTableView.UPDATE_ROW_ITEM, CKBehavior.VOID_BEHAVIOR);
		view.addBehavior(ResourceTableView.DELETE_ROW_ITEM, CKBehavior.VOID_BEHAVIOR);
		view.addBehavior(ResourceTableView.RESOURCE_FIELD_BEHAVIOR, new CKBehavior() {
			@Override
					public Object execute(final Object... objects) {
						RBField e = (RBField) objects[2];
						RepeatingView view = new RepeatingView(
								(String) objects[0]);
						Set<Constraint> list = e.getConstraints();
						for (Constraint constraint : list) {
							if (constraint.getResourceTypeConstraint()
									==	MockNewRBEntityFactory.createAddress().getType()) {
								String url = "<iframe src='http://www.map-generator."
									+"net/extmap.php?name=&amp;address=";
								for (Object o : e.getFieldValues()) {
									if(o instanceof RBEntity){
										o = ((RBEntity) o).getLabel();
										view.add(new Label(view.newChildId(), o.toString()));
									}
									String temp = o.toString();
									temp = temp.replace(" ", "%20");
									url = url.concat(temp);
								}
								url = url.concat("&amp;width=200&amp;height=100&amp;maptype="
									+"map&amp;zoom=12&amp;hl=en&amp;t=1315996793' width='200'"
									+" height='100' marginwidth='0'"
									+ " marginheight='0' frameborder='0' scrolling='no'></iframe>");
								Label label = new Label(view.newChildId(),
										url);
								label.setEscapeModelStrings(false);
								view.add(label);
							}else{
								List<Object> values = e.getFieldValues();
								for (Object o : values) {
								if(o != null){
									RBEntity e1 = (RBEntity) o;
									view.add(new Label(view.newChildId(),
											e1.getLabel()));
								}else{
									view.add(new Label(view.newChildId(),""));
								}
								}
							}
						}
						view.setEscapeModelStrings(false);
						return view;
					}
		});
		view.addBehavior(ResourceTableView.ADD_CUSTOM_ROW_ITEM, new CKBehavior() {
			@Override
			public Object execute(final Object... objects) {
				String id = (String) objects[0];
				RBEntity e = (RBEntity) objects[1];
				CKLink link = new CKLink(id, "Search online",
						"http://google.com/search?q=" + e.getLabel().replace(",", ""), CKLinkType.EXTERNAL_LINK);
				return link;
			}
		});
		add(view);
	}

}
