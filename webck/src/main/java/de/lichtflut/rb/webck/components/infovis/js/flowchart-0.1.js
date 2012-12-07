
var GraphVisConfig = GraphVisConfig || {
	laneHeight : 80,
	chartStart : 200,
	scale : 60
};

var LFRB = LFRB || {};

function drawicon(node, paper) {
	var icon = paper.rect(node.x, node.y, node.width, node.height, 5);
	icon.attr({'fill': '#d5d5df', 'stroke':'#889', 'title' : node.name, 'cursor':'pointer' });
	icon.click(function() {
		LFRB.FlowChart.onSelectNode(node, icon);
	});
	icon.dblclick(function() {
		LFRB.FlowChart.switchToNode(node);
	});
	
	var clipWidth = node.width -2;
	var clip = (node.x + 1) + ' ' + node.y + ' '+ clipWidth + ' ' + node.height;
	var text = paper.text(node.centerX, node.centerY)
		.attr({'font-size':'10pt', 'clip-rect': clip, 'cursor':'pointer'});
	LFRB.InfoVis.setWrappingText(text, node.name, clipWidth);
	
	text.click(function() {
		LFRB.FlowChart.onSelectNode(node, icon);
	});
	text.dblclick(function() {
		LFRB.FlowChart.switchToNode(node);
	});
}

function drawLaneIcon(lane, paper) {
	paper.path('M0 ' + lane.offset + 'L5000 ' + lane.offset)
		.attr({'stroke':'#777'});
	var text = paper.text(5, lane.center, lane.title)
		.attr({'text-anchor': 'start', 'font-size':'12pt', 'font-weight':'bold', 'cursor':'pointer'});
	
	text.click(function() {
		LFRB.FlowChart.onSelectLane(lane, text);
	});
}

LFRB.FlowChart = {
	drawChart : function(nodeset, lanes, paper) {
		this.drawLanes(lanes, paper);
		this.calculateNodes(nodeset);
		this.drawLines(nodeset,lanes, paper);
		this.drawNodes(nodeset,lanes, paper);	
	},
	drawLanes : function(lanes, paper) {
		var y = 100;
		for (id in lanes) {
			var lane = lanes[id];
			lane.offset = y;
			lane.center = lane.offset + GraphVisConfig.laneHeight/2;
			lane.height = GraphVisConfig.laneHeight;
			drawLaneIcon(lane, paper);
			/*paper.id('M0 ' + y + 'L5000 ' + y)
				.attr({'stroke':'#777'});
			paper.text(5, lane.center, lane.title)
				.attr({'text-anchor': 'start', 'font-size':'12pt', 'font-weight':'bold'});
				*/
			y += GraphVisConfig.laneHeight;
		}
		paper.path('M0 ' + y + 'L5000 ' + y)
			.attr({'stroke':'#777'});
	},
	calculateNodes : function(nodeset) {
		var offsetX = GraphVisConfig.chartStart;
		var scale = GraphVisConfig.scale;
		var margin = 10;
		for (id in nodeset) {
			var node = nodeset[id];
			var lane = node.lane;
			
			node.x = offsetX + node.start * scale;
			node.y = lane.offset + margin;
			node.width = (node.end - node.start) * scale;
			node.height = lane.height - 2 * margin;
			node.centerX = offsetX + (node.start + (node.end - node.start)/2) * scale;
			node.centerY = lane.center;
			node.anchorStart = {x: node.x -1 , y: lane.center};
			node.anchorEnd = {x: node.x + node.width + 1, y: lane.center};
		}
	},
	drawNodes : function(nodeset, lanes, paper) {
		var offsetX = GraphVisConfig.chartStart;
		var scale = 40;
		var margin = 10;
		for (id in nodeset) {
			var node = nodeset[id];
			drawicon(node, paper);
		}
	},
	drawLines : function(nodeset, lanes, paper) {
		for (id in nodeset) {
			var node = nodeset[id];
			var lane = node.lane;
			var count = 0;
			if (node.predecessors !== undefined) {
				count = node.predecessors.length;
			}
			for (i = 0; i < count; i++) {
				var predecessor = node.predecessors[i];
				var middleX =  predecessor.anchorEnd.x + (node.anchorStart.x - predecessor.anchorEnd.x) /2;
				paper.path('M' + predecessor.anchorEnd.x + ',' + predecessor.anchorEnd.y 
						 + 'H' + middleX
						 + 'V' + node.anchorStart.y
						 + 'L' + node.anchorStart.x + ',' + node.anchorStart.y)
					.attr({'stroke':'#762226'});
					
				paper.path('M' + node.anchorStart.x + ',' +  node.anchorStart.y 
							 + 'L' + (node.anchorStart.x - 8) + ',' + (node.anchorStart.y-5)
							 + 'V' + (node.anchorStart.y +5 )
							 + 'Z')
						.attr({'stroke':'#762226', 'fill':'#762226'});
			}			
		}
	},
	onSelectNode : function(node, icon) {
		if (LFRB.InfoVis.currentNode !== undefined) {
			LFRB.InfoVis.currentNode.reset();
		}
		LFRB.InfoVis.currentNode = icon;
		LFRB.InfoVis.updateNodeInfo(node.id);
		icon.attr({'stroke':'#762226', 'stroke-width':2});
		icon.reset = function() {
			icon.attr({'stroke':'#889', 'stroke-width':1})
		}
	},
	onSelectLane : function(lane, icon) {
		if (LFRB.InfoVis.currentNode !== undefined) {
			LFRB.InfoVis.currentNode.reset();
		}
		LFRB.InfoVis.currentNode = icon;
		LFRB.InfoVis.updateNodeInfo(lane.uri);
		icon.attr({'fill':'#762226'});
		icon.reset = function() {
			icon.attr({'fill':'#000'})
		}
	},
	switchToNode : function(node) {
		LFRB.InfoVis.switchToNode(node.id, 'flowchart');
	}
}


