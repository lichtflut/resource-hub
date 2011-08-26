/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.component;

import junit.framework.TestCase;
import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.application.AbstractResourceBrowserApplication;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.components.TestComponentsPage;

/**
 * Testcase to test the CKBehavior on a child class of {@link CKComponent}.
 *
 * Created: May 20, 2011
 *
 * @author Nils Bleisch
 */
public final class CKBehaviorTest extends TestCase{

	@SuppressWarnings("unused")
	private WicketTester tester;

	@Override
	public void setUp(){
		tester = new WicketTester(new AbstractResourceBrowserApplication(){
			protected void init(){
				getMarkupSettings().setStripWicketTags(true);
			}

			@Override
			public Class<? extends Page> getHomePage() {
				return TestComponentsPage.class;
			}
		});
	}

	/**
	 * Test to persist and find entities.
	 */
	@Test
	public void testCKBehavior(){
		TestComponent tComponent = new TestComponent();
		//Reset all possible existing behaviors
		tComponent.resetBehaviors();
		//Define a final testReference to see how the behaviors are working
		final StringBuffer testValue= new StringBuffer();

		//Define two behaviors

		//behavior 1's execute will add/concatenate a "1" to testValue
		CKBehavior behavior1 = new CKBehavior(){

			@Override
			public Object execute(final Object... objects) {
				testValue.append("1");
				return null;
			}
		};

		CKBehavior behavior2 = new CKBehavior(){
			@Override
			public Object execute(final Object... objects) {
				testValue.append("2");
				return null;
			}
		};
		//No behaviors should be executed, therefor the testValue must be "blank"
		Assert.assertEquals(testValue.toString(),"");
		//Assert behavior1 without execute, testValue should be also "blank"
		tComponent.addBehavior(TestComponent.BEHAVIOR_HOOK1,behavior1);
		Assert.assertEquals(testValue.toString(),"");
		tComponent.executeBehaviors();
		Assert.assertEquals(testValue.toString(),"1");
		tComponent.addBehavior(TestComponent.BEHAVIOR_HOOK2,behavior2);
		Assert.assertEquals(testValue.toString(),"1");
		tComponent.executeBehaviors();
		Assert.assertEquals(testValue.toString(),"112");
		//Change the behaviors
		tComponent.addBehavior(TestComponent.BEHAVIOR_HOOK2,behavior1);
		tComponent.executeBehaviors();
		Assert.assertEquals(testValue.toString(),"11211");
		tComponent.resetBehaviors();
		tComponent.executeBehaviors();
		Assert.assertEquals(testValue.toString(),"11211");
	}

	/**
	 *
	 * @author Nils Bleisch
	 */
	class TestComponent extends CKComponent{


		private static final long serialVersionUID = 1L;
		public static final String BEHAVIOR_HOOK1 = "de.lichtflut.ck.behavior.1",
								   BEHAVIOR_HOOK2 = "de.lichtflut.ck.behavior.2";

		/**
		 *
		 */
		public TestComponent(){
			super("testString");
		}

		@Override
		public RBServiceProvider getServiceProvider(){
			return null;
		}

		@Override
		public CKComponent setViewMode(final ViewMode mode){
			return null;
		}

		/**
		 * process the defined behaviors.
		 */
		public void executeBehaviors(){
			if(this.getBehavior(BEHAVIOR_HOOK1)!=null){
				this.getBehavior(BEHAVIOR_HOOK1).execute(new Object[]{});
			}

			if(this.getBehavior(BEHAVIOR_HOOK2)!=null){
				this.getBehavior(BEHAVIOR_HOOK2).execute(new Object[]{});
			}

		}

		@Override
		protected void initComponent(final CKValueWrapperModel model) {

		}

	}

}
