
var LFRB = LFRB || {};

LFRB.InfoVis = {
	currentNode : undefined,	
	callbackURL : '',
	updateNodeInfo : function (node) {
		 var wcall = wicketAjaxGet(this.callbackURL + '&selected=' + wicketEncode(node), function() { }, function() { });
	}
	
}


