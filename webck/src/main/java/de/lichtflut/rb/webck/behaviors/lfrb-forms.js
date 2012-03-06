var LFRB = LFRB || {};

LFRB.Forms = {
	submitOnEnter : function(formId, buttonId) {
		var form = jQuery(formId); 
		form.keypress(function(event)  {
			var button = document.getElementById(buttonId);
			if (event.keyCode != 13 || button == null || button.onclick == null || typeof(button.onclick) == 'undefined') {
				return;
			}
			alert("enter");
			var tag = event.target.tagName;
			if (tag == 'INPUT' || tag == 'SELECT') {
				var qButton = jQuery(buttonId);
				button.onclick.bind(qButton)(); 
				qButton.focus();
				qButton.click();
				return false;
			}
		});
	}
}
