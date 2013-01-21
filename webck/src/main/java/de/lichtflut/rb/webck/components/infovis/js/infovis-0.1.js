
var LFRB = LFRB || {};

LFRB.InfoVis = {
	contextPath : '',
    serviceURI : '',
	currentNode : undefined,	
	callbackURL : '',
	updateNodeInfo : function (node) {
		 var wcall = wicketAjaxGet(this.callbackURL + '&selected=' + wicketEncode(node), function() { }, function() { });
	},
	switchToNode : function (nodeID, mode) {
		 var host = window.location.href.match(/^[^#]*?:\/\/[^/]+/);
		 window.location.href= host + this.contextPath + '/flowchart?rid=' + wicketEncode(nodeID);
	},
	setWrappingText : function (textElement, text, clipWidth) {
		var words = text.split(" ");
		var tempText = "";
		for (var i=0; i<words.length; i++) {
			textElement.attr("text", tempText + " " + words[i]);
			if (textElement.getBBox().width > clipWidth) {
				tempText += "\n" + words[i];
			} else {
				tempText += " " + words[i];
			}
		}
		textElement.attr("text", tempText.substring(1));
	}
}


