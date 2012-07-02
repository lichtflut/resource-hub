var LFRB = LFRB || {};

LFRB.RichText = {
	init : function(editorId) {
		var iframe = jQuery('#' + editorId + '_ifr');
		var editor = iframe.contents().find('#tinymce');
		editor.bind('blur', function(){
			$('#' + editorId).html(editor.html());
			if(editor.html() != $(editorId).html()){
				alert('An Error ocurred.<br />Text could notbe saved!<br />'+editor.html() + '<br /><br />' +  $(editorId).html());
			}else{
				alert('saved');
			}
		})
	}
}
