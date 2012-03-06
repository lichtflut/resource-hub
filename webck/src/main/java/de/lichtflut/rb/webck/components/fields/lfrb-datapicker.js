var LFRB = LFRB || {};

LFRB.Datapicker = {
	accept : function(hiddenId, displayId, item) {
		var hiddenField = jQuery(hiddenId);
		var displayField = jQuery(displayId);
		if (item) {
			hiddenField.attr('value', item.id);
			displayField.attr('value', item.label);
			displayField.removeClass('status-error').removeClass('status-warning');
		} else { 
			alert ('internal error nothing selected')
		};
		return false;
	},
	onChange : function(displayId) {
		var displayField = jQuery(displayId);
		if (displayField.hasClass('status-warning')) { 
			displayField.addClass('status-error');
		} 
	},
	onSearch : function(displayId) {
		var displayField = jQuery(displayId);
		displayField.removeClass('status-error').addClass('status-warning');
	},
	onOpen : function(displayId) {
		var displayField = jQuery(displayId);
		displayField.attr('lfrb-state', 'open');
		var clickHandler = function(e) { 
	        var clickedAt = jQuery(e.target);
	        if (clickedAt.parents(displayId).length == 0) { 
	            displayField.autocomplete("close");
	        } 
	        jQuery(document).unbind('click', clickHandler);
	    }
		jQuery(document).bind('click', clickHandler);
		var keyHandler = function(evt) { 
		    if (evt.keyCode == 27) {
	            displayField.autocomplete("close");
		    }
	        jQuery(document).unbind('keypress', keyHandler);
	    }
		jQuery(document).bind('keypress', keyHandler);
	},
	onClose : function(displayId) {
		jQuery(displayId).attr('lfrb-state', 'closed');
	},
	toggle : function(displayId) {
		var displayField = jQuery(displayId);
		var status = displayField.attr("lfrb-state");
		if (status == 'open') {
			displayField.autocomplete('close');
		} else {
			var term = displayField.val();
			if (term == undefined || term.length == 0) {
				term = "#!*";
			}
			displayField.autocomplete('search', term);			
		}
	}
}
