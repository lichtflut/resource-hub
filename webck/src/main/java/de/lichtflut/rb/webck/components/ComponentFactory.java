/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Factory for in-/output components.
 *
 * @author Erik Aderhold
 */
public abstract class ComponentFactory {

    /**
     * Erzeugt ein neues Textfield mit Label und Feedbackpanel und einem eigenem Model.
     *
     * @param name      Der technische Name.
     * @param model     Das model der Komponente.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Das initialisierte {@link TextField}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static TextField addTextField(final String name, final IModel<?> model, final MarkupContainer container) {
        final TextField field = new TextField(name, model);
        initializeField(name, container, field, true);
        return field;
    }

    /**
     * Erzeugt ein neues Textfield mit Label und Feedbackpanel.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Das initialisierte {@link TextField}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> TextField<T> addTypedTextField(final String name, final MarkupContainer container, final T clazz) {
        final TextField field = new TextField<T>(name);
        initializeField(name, container, field, true);
        return field;
    }

    /**
     * Erzeugt ein neues Auswahlfeld.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param values    Die m�glichen Werte.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link DropDownChoice}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static DropDownChoice addDropDownChoice(final String name, final MarkupContainer container, final IModel model, final List values) {
		final DropDownChoice field = new DropDownChoice<Object>(name, model, values);
        initializeField(name, container, field, true);
        return field;
    }

    /**
     * Erzeugt eine neue Checkbox.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link CheckBox}.
     */
    public static CheckBox addCheckBox(final String name, final MarkupContainer container, final Object... additives) {
        return addCheckBox(name, container, true, additives);
    }

    /**
     * Erzeugt ein neues informatorisches Textfeld.
     *
     * @param name       Der technische Name.
     * @param container  Der Container, dem das Label hinzugef�gt werden soll.
     * @param model      Das Model.
     * @return Das initialisierte {@link Label}.
     */
    public static Label addInfoText(final String name, final MarkupContainer container, final IModel<?> model) {
        final Label label = new Label(name, model);
        container.add(label);

        return label;
    }

    // ----------------------------------------------------

    private static void initializeField(final String fieldName, final MarkupContainer container,
                                        final FormComponent<?> field, final boolean createLabel) {
        initializeField(fieldName, null, container, field, createLabel);
    }

    private static void initializeField(final String fieldName, final String labelName, final MarkupContainer container,
                                        final FormComponent<?> field, final boolean createLabel) {
        if (labelName == null) {
            field.setLabel(new ResourceModel(fieldName));
        } else {
            field.setLabel(new ResourceModel(labelName, labelName));
        }
        field.setMarkupId(fieldName);
        field.setOutputMarkupPlaceholderTag(true);
        container.add(field);
        if (createLabel) {
            container.add(new SimpleFormComponentLabel(fieldName + "Label", field));
        }
        final ComponentFeedbackPanel feedback = new ComponentFeedbackPanel(fieldName + "Feedback", field);
        feedback.setOutputMarkupPlaceholderTag(true);
        container.add(feedback);
    }
}


