var LFRB = LFRB || {};

LFRB.Datapicker = {
    initAllDatapickers : function() {
        jQuery('.data-picker').each( function (idx, picker) {
            var picker = jQuery(picker);
            var hidden = picker.children('.data-picker-store');
            var display = picker.children('.data-picker-field');
            LFRB.Datapicker.init(hidden, display);
        });
    },
    init : function(hidden, display) {
        display.autocomplete (
            {
                source : display.attr('lfrb-source'),
                search : function (even, ui) { LFRB.Datapicker.onSearch(display);} ,
                select : function (even, ui) { LFRB.Datapicker.accept(hidden, display, ui.item);} ,
                change : function (even, ui) { LFRB.Datapicker.onChange(display);} ,
                open   : function (even, ui) { LFRB.Datapicker.onOpen(display);},
                close  : function (even, ui) { LFRB.Datapicker.onClose(display);}
            }
        );
    },
	accept : function(hidden, display, item) {
		if (item) {
			hidden.attr('value', item.id);
			display.attr('value', item.label);
			display.removeClass('status-error').removeClass('status-warning');
		} else { 
			alert ('internal error nothing selected')
		}
		return false;
	},
	onChange : function(display) {
		if (display.hasClass('status-warning')) {
			display.addClass('status-error');
		} 
	},
	onSearch : function(display) {
		display.removeClass('status-error').addClass('status-warning');
	},
	onOpen : function(display) {
		display.attr('lfrb-state', 'open');
		var clickHandler = function(e) { 
	        var clickedAt = jQuery(e.target);
	        if (clickedAt.parents(displayId).length == 0) { 
	            display.autocomplete("close");
	        } 
	        jQuery(document).unbind('click', clickHandler);
	    };
		jQuery(document).bind('click', clickHandler);
		var keyHandler = function(evt) { 
		    if (evt.keyCode == 27) {
	            display.autocomplete("close");
		    }
	        jQuery(document).unbind('keypress', keyHandler);
	    };
		jQuery(document).bind('keypress', keyHandler);
	},
	onClose : function(displayField) {
        displayField.attr('lfrb-state', 'closed');
	},
	toggle : function(displayID) {
        var display = jQuery(displayID);
		var status = display.attr("lfrb-state");
		if (status == 'open') {
			display.autocomplete('close');
		} else {
			var term = display.val();
			if (term == undefined || term.length == 0) {
				term = "#!*";
			}
			display.autocomplete('search', term);
		}
	}
};
