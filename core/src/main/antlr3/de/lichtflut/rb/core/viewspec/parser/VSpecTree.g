tree grammar VSpecTree;

options {
  tokenVocab = VSpec;
  ASTLabelType = CommonTree;
}

@header{
/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Generated with AntLR.
 */
package de.lichtflut.rb.core.viewspec.parser;

import de.lichtflut.rb.core.viewspec.reader.VSpecCollector;
}

@members{
    private final VSpecCollector ctx = new VSpecCollector();

    private String unquote(String s) {
        return s.replaceAll("\"", "");
    }

    public VSpecCollector getCollector() {
        return ctx;
    }
}

// Input contains 1 or more statements
statements : ^(STATEMENTS statement+);

// A statement is either a namespace or a perspective
statement : namespace_decl | perspective_decl;

// NAMESPACE
namespace_decl: ^(NAMESPACE (ns=STRING pr=STRING {
	ctx.addNamespace(unquote($ns.text), unquote($pr.text));
}));

// PERSPECTIVE
perspective_decl : ^(PERSPECTIVE (name=STRING {
    ctx.newPerspective(unquote($name.text));
} perspective_title_decl? port_decl*  {
    ctx.currentPerspective().setName($name.text);
    ctx.perspectiveFinished();
}));

perspective_title_decl : ^(PERSPECTIVE_TITLE_DECL (title=STRING{
    ctx.currentPerspective().setTitle(unquote($title.text));
}));

// PORT
port_decl : ^(PORT_DECL(widget_decl*{
    ctx.portFinished();
}));

// WIDGET

widget_decl : ^(WIDGET_DECL(widget_property* action_decl? selection_decl? column_decl* {
    ctx.widgetFinished();
}));

widget_property : ^(WIDGET_PROPERTY(key=. value=STRING{
    ctx.currentWidgetProperty($key.getText(), unquote($value.text));
}));

// ACTION

action_decl : ^(ACTION_DECL(action_property+ )) {
    ctx.actionFinished();
};

action_property : ^(ACTION_PROPERTY(key=. value=STRING{
    ctx.currentActionProperty($key.getText(), unquote($value.text));
}));

// COLUMN

column_decl : ^(COLUMN_DECL(column_property+ )) {
    ctx.columnFinished();
};

column_property : ^(COLUMN_PROPERTY(key=. value=STRING{
    ctx.currentColumnProperty($key.getText(), unquote($value.text));
}));


// SELECTION

selection_decl : query_decl | query_by_type_decl | query_by_value_decl | query_by_ref_decl ;

query_decl : ^(QUERY_DECL(query_string=STRING {
    ctx.setSelectionQuery(unquote($query_string.text));
}));

query_by_type_decl : ^(QUERY_BY_TYPE(type=STRING {
    ctx.setSelectionQueryByType(unquote($type.text));
}));

query_by_value_decl : ^(QUERY_BY_VALUE(val=STRING {
    ctx.setSelectionQueryByValue(unquote($val.text));
}));

query_by_ref_decl : ^(QUERY_BY_REF(ref=STRING {
    ctx.setSelectionQueryByRef(unquote($ref.text));
}));


