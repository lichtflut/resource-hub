
var GraphVisConfig = GraphVisConfig || {
	nodeWidth : 150,	
	nodeHeight : 50,	
	nodeMargin : 10,
	levelMargin : 160
};

function showTree(root, paper) {
	var dim = calcDimensions(root, 100, 100);
	addRecursive(root, paper);
}

function addRecursive(node, paper) {
	if (node.children != undefined && node.children.length > 0) {
		for (var i = 0; i < node.children.length; i++) {
			renderLine(node, node.children[i], paper);
		}
		for (var i = 0; i < node.children.length; i++) {
		 	addRecursive(node.children[i], paper);
		}
	} 
	renderNode(node, paper);
}

function calcDimensions(node, offsetX, offsetY) {
	var dim = {
		width: 0,
		height: 0,
		depth :0
	};
	if (node.children == undefined || node.children.length == 0) {
		dim.width = node.width ? Math.max(GraphVisConfig.nodeWidth, node.width) : GraphVisConfig.nodeWidth;
		dim.width += GraphVisConfig.nodeMargin;
		dim.height = node.height ? Math.max(GraphVisConfig.nodeHeight, node.height) : GraphVisConfig.nodeHeight;
	} else {
		var childOffsetX = offsetX;
		var childOffsetY = offsetY + GraphVisConfig.levelMargin;
		for (var i = 0; i < node.children.length; i++) {
			var childDim = calcDimensions(node.children[i], childOffsetX, childOffsetY);
			childOffsetX += childDim.width;
			dim.width += childDim.width;
			dim.height = Math.max(dim.height, childDim.height);
			dim.depth = Math.max(dim.depth, childDim.depth);
		}
		dim.width += Math.max(0, node.children.length -1) * GraphVisConfig.nodeMargin;
	}
	node.width = node.width ? Math.max(GraphVisConfig.nodeWidth, node.width) : GraphVisConfig.nodeWidth;
	node.height = node.height ? Math.max(GraphVisConfig.nodeHeight, node.height) : GraphVisConfig.nodeHeight;
	node.outerWidth = Math.max(dim.width, node.width);
	node.outerHeight = dim.height;
	node.centerX = offsetX + node.outerWidth/2;
	node.centerY = offsetY + node.outerHeight/2;
	node.depth =  dim.depth;
	dim.depth++;
	return dim;
}

function renderNode(node, paper) {
	var x = node.centerX - node.width / 2;
	var y = node.centerY - node.height / 2;
	var rect = paper.rect(x, y, node.width, node.height, 5);
	rect.attr({'fill': '#d5d5df', 'stroke':'#889', 'title' : node.name, 'cursor':'pointer' });
	rect.click(function() {
		onSelectNode(node, rect);
	});
	
	var clipWidth = node.width -2;
	var clip = (x + 1) + ' ' + y + ' '+ clipWidth + ' ' + node.height;
	
	var text = paper.text(node.centerX, node.centerY);
	text.attr({'font-size':'10pt', 'clip-rect': clip, 'title' : node.name, 'cursor':'pointer'});
	text.click(function() {
		onSelectNode(node, rect);
	});
	
	var words = node.name.split(" ");

	var tempText = "";
	for (var i=0; i<words.length; i++) {
	  text.attr("text", tempText + " " + words[i]);
	  if (text.getBBox().width > clipWidth) {
	    tempText += "\n" + words[i];
	  } else {
	    tempText += " " + words[i];
	  }
	}

	text.attr("text", tempText.substring(1));
	
}

function renderLine(a, b, paper) {
	paper.path('M' + a.centerX + ',' + a.centerY + 'L' + b.centerX + ',' + b.centerY)
		.attr({'stroke':'#762226'});
}

function onSelectNode(node, icon) {
	if (LFRB.InfoVis.currentNode !== undefined) {
		LFRB.InfoVis.currentNode.attr({'stroke':'#889', 'stroke-width':1});
	}
	LFRB.InfoVis.currentNode = icon;
	LFRB.InfoVis.updateNodeInfo(node.id);
	icon.attr({'stroke':'#762226', 'stroke-width':2});
}
