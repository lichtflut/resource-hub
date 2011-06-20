/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.IValidator;

/**
 * ComponentFactory f�r Standard Ein- und Ausgabekompenenten inkl. ihrer Labels, FeedbackPanels, etc.
 *
 * @author Erik Aderhold
 */
//Turn checkstyle off - it's O.K. here to instantiate so many classes
// CHECKSTYLE:OFF
public abstract class ComponentFactory {

    /**
     * Erzeugt ein neues Textfield mit Label und Feedbackpanel.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Das initialisierte {@link TextField}.
     */
    @SuppressWarnings("rawtypes")
	public static TextField addTextField(final String name, final MarkupContainer container, final Object... additives) {
        return addTypedTextField(name, container, String.class, additives);
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
	public static <T> TextField<T> addTypedTextField(final String name, final MarkupContainer container, final T clasz, final Object... additives) {
        final TextField field = new TextField<T>(name);
        initializeField(name, container, field, true);
        addAll(field, additives);
        return field;
    }

    /**
     * Erzeugt ein neues Textfield mit Feedbackpanel und <b>wahlweise</b> Label.
     *
     * @param name        Der technische Name.
     * @param container   Der Container, dem das Feld hinzugef�gt werden soll.
     * @param clasz       Der Typ.
     * @param createLabel Das createLabel Flag
     * @param additives   Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Das initialisierte {@link TextField}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> TextField<T> addTypedTextField(final String name, final MarkupContainer container, final T clasz,
                                                     final boolean createLabel, final Object... additives) {
        final TextField field = new TextField<T>(name);
        initializeField(name, container, field, createLabel);
        addAll(field, additives);
        return field;
    }

    /**
     * Erzeugt ein neues Textfield mit Label (mit eigener Id) und Feedbackpanel.
     *
     * @param name      Der technische Name.
     * @param labelName Der spezielle LabelName.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param clasz     Die Typ-Klasse. (z.B. Integer.class, String.class,...)
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Das initialisierte {@link TextField}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> TextField<T> addTypedTextField(final String name, final String labelName, final MarkupContainer container,
                                                            final T clasz, final Object... additives) {
        final TextField field = new TextField<T>(name);
        initializeField(name, labelName, container, field, true);
        addAll(field, additives);
        return field;
    }


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
	public static TextField addTextField(final String name, final IModel<?> model, final MarkupContainer container, final Object... additives) {
        final TextField field = new TextField(name, model);
        initializeField(name, container, field, true);
        addAll(field, additives);
        return field;
    }

    /**
     * Erzeugt ein neues AutoCompleteTextfield mit Label und Feedbackpanel.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param handler   the handler
     * @param additives Behaviours und Validatoren die hinzugef�gt werden sollen.
     * @return Das initialisierte {@link TextField}.
     */
    @SuppressWarnings({ "rawtypes", "serial" })
	public static AutoCompleteTextField addAutoCompleteTextField(final String name, final MarkupContainer container,
                                                                 final StringAutoCompleteHandler handler, final Object... additives) {
        final AutoCompleteTextField field = new AutoCompleteTextField(name) {

            @Override
            protected Iterator<String> getChoices(final String input) {
                return handler.getChoices(input);
            }
        };
        initializeField(name, container, field, true);
        addAll(field, additives);
        return field;
    }

    /**
     * Erzeugt eine neue TextArea mit Label und Feedbackpanel.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link TextArea}.
     */
    @SuppressWarnings("rawtypes")
	public static TextArea addTextArea(final String name, final MarkupContainer container, final Object... additives) {
        final TextArea textArea = new TextArea(name);
        initializeField(name, container, textArea, true);
        addAll(textArea, additives);

        return textArea;
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
    @SuppressWarnings("rawtypes")
	public static DropDownChoice addDropDownChoice(final String name, final MarkupContainer container, final Object[] values, final Object... additives) {
        return addDropDownChoice(name, container, values, Boolean.TRUE, additives);
    }

    /**
     * Erzeugt ein neues Auswahlfeld, es wird anhand des Flags isEnum gepr�ft, ob die Values aus einer Ratingenumration
     * stammen.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param values    Die m�glichen Werte.
     * @param isEnum    Flag, ob die Values aus einer Enumeration genommen werden
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link DropDownChoice}.
     */
    @SuppressWarnings("rawtypes")
	public static DropDownChoice addDropDownChoice(final String name, final MarkupContainer container,
                                                   final Object[] values, final boolean isEnum, final Object... additives) {
        return addDropDownChoice(name, container, values, isEnum, true, true, additives);
    }

    /**
     * Erzeugt ein neues Auswahlfeld, mit wahlweise Label
     *
     * @param name        Der technische Name.
     * @param container   Der Container, dem das Feld hinzugef�gt werden soll.
     * @param values      Die m�glichen Werte.
     * @param isEnum      Flag, ob die Values aus einer Enumeration genommen werden
     * @param createLabel Flag ob Label generiert werden soll.
     * @param additives   Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link DropDownChoice}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static DropDownChoice addDropDownChoice(final String name, final MarkupContainer container, final Object[] values,
                                                   final boolean isEnum, final boolean createLabel, final Object... additives) {
        final DropDownChoice field;

//        Enum spezifische I18N-Valueaufl�sung auskommentiert!!
//        if (!isEnum) {
            field = new DropDownChoice<Object>(name, Arrays.asList(values), new ChoiceRenderer() {
                private static final long serialVersionUID = 1L;

                /**
                 * {@inheritDoc}
                 */
                public Object getDisplayValue(final Object object) {
                    return object;
                }

                /**
                 * {@inheritDoc}
                 */
                @SuppressWarnings("unused")
				public Object getIdValue(final Object object) {
                    return object;
                }
            });
//        } else {

//            field = new DropDownChoice(name, Arrays.asList(values), new I18nChoiceRenderer());
//        }

        initializeField(name, container, field, createLabel);
        addAll(field, additives);
        return field;
    }

    /**
     * Erzeugt ein neues RadioChoiceFeld.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param values    Die m�glichen Werte.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link DropDownChoice}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static RadioChoice addRadioChoice(final String name, final MarkupContainer container, final Object[] values, final Object... additives) {
        final RadioChoice field = new RadioChoice(name, Arrays.asList(values)/*, new I18nChoiceRenderer()*/);
        initializeField(name, container, field, true);
        addAll(field, additives);
        return field;
    }

    /**
     * Erzeugt ein neues RadioChoiceFeld bei dem ein seperates Label gesetzt wird.
     *
     * @param name      Der technische Name.
     * @param label     Das internationalisierte Label.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param values    Die m�glichen Werte.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link DropDownChoice}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static RadioChoice addRadioChoice(final String name, final String label, final MarkupContainer container, final Object[] values, final Object... additives) {
        final RadioChoice field = new RadioChoice(name, Arrays.asList(values)/*, new I18nChoiceRenderer()*/);
        initializeField(name, label, container, field, true);
        addAll(field, additives);
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
     * Erzeugt eine neue Checkbox.
     *
     * @param name      Der technische Name.
     * @param container Der Container, dem das Feld hinzugef�gt werden soll.
     * @param additives Behaviours und Validatored die hinzugef�gt werden sollen.
     * @return Die initialisierte {@link CheckBox}.
     */
    public static CheckBox addCheckBox(final String name, final MarkupContainer container, final boolean createLabel, final Object... additives) {
        final CheckBox field = new CheckBox(name);
        initializeField(name, container, field, createLabel);
        addAll(field, additives);
        return field;
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static void addAll(final FormComponent comp, final Object... additives) {
        for (Object obj : additives) {
            if (obj instanceof Behavior) {
                comp.add((Behavior) obj);
            } else if (obj instanceof IValidator) {
                comp.add((IValidator) obj);
            } else if (obj instanceof Component) {
                comp.add((Component) obj);
            } else {
                throw new IllegalStateException("Cannot add object of type " + obj.getClass());
            }
        }
    }

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
//        field.add(new ValidationUpdateBehaviour());
//        field.add(new AjaxValidationUpdateBehavior(feedback));
    }
}

/*

// Anwendung benutzt:
addGenericResourceView(String wicketpanelID, ResourceType resourceType);    <wicket:panel wicket:id="...">[Panel eigenes generisches Markup]</wicket:panel>

// addGenericResourceView benutzt:
addTextfieldPanel("textfeld-ID");  <wicket:panel wicket:id="textfeld-ID">[Panel eigenes Markup]</wicket:panel>


//Beispiel HTML f�r Factory-Methoden

// Anrede ist DropDownChoice
<div class="form-field">
    <label wicket:id="anredeLabel">[anrede]</label>
	<select wicket:id="anrede"></select>
    <span wicket:id="anredeFeedback"></span>
</div>

// Nachname ist Textfield
<div class="form-field">
    <label wicket:id="name.nachnameLabel">[name.nachname]</label>
    <input wicket:id="name.nachname" type="text"/>
    <span wicket:id="name.nachnameFeedback"></span>
</div>
*/

