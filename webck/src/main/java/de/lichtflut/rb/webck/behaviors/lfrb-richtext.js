var LFRB = LFRB || {};

LFRB.RichText = {
	init : function(editorId) {
		var iframe = jQuery('#' + editorId + '_ifr');
		var editor = iframe.contents().find('#tinymce');
		editor.bind('blur', function(){
				$('#' + editorId).html(editor.html());
			})
	}
}
