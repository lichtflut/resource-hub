var LFRB = LFRB || {};

LFRB.RichText = {
	init : function(editorId) {
        $('#' + editorId).wysiwyg({autoGrow:true, controls:"bold,italic,|,undo,redo"});
	}
}
