/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var GraphVisConfig = GraphVisConfig || {
	nodeWidth : 150,	
	nodeHeight : 50,	
	nodeMargin : 10,
	levelMargin : 160
};

function showTree(paper) {
    jQuery.getJSON(LFRB.InfoVis.serviceURI, function(graph) {
        var dim = calcDimensions(graph, 100, 100);
        addRecursive(graph, paper);
    });

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
	
	LFRB.InfoVis.setWrappingText(text, node.name, clipWidth);
	
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
