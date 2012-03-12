var LFRB = LFRB || {};

LFRB.ContextMenu = {
	close : function(displayId) {
		var item = jQuery(displayId);
		item.hide();
		item.attr("lfrb-state", "closed");
	},
	toggle : function(displayId) {
		var item = jQuery(displayId);
		var status = item.attr("lfrb-state");
		if (status == 'open') {
			this.close(displayId);
		} else {
			jQuery(displayId).show();
			item.attr("lfrb-state", "open");	
			var clickHandler = function(e) { 
		        var clickedAt = jQuery(e.target);
		        if (clickedAt.parents(displayId).length == 0) { 
		        	LFRB.ContextMenu.close(displayId);
		        } 
		        jQuery(document).unbind('click', clickHandler);
		    }
			var keyHandler = function(evt) { 
			    if (evt.keyCode == 27) {
			    	LFRB.ContextMenu.close(displayId);
			    }
		        jQuery(document).unbind('keypress', keyHandler);
		    }
			setTimeout(function() {
				jQuery(document).bind('click', clickHandler);
				jQuery(document).bind('keypress', keyHandler);
			}, 200)
		}
	}
}
