
var GraphVisConfig = GraphVisConfig || {
	laneHeight : 100,
	chartStart : 100
};

var LFRB = LFRB || {};

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
			paper.path('M0 ' + y + 'L5000 ' + y)
				.attr({'stroke':'#777'});
			paper.text(10, lane.center, lane.title)
				.attr({'text-anchor': 'start', 'font-size':'14pt'});
			y += GraphVisConfig.laneHeight;
		}
	},
	calculateNodes : function(nodeset) {
		var offsetX = GraphVisConfig.chartStart;
		var scale = 40;
		var margin = 10;
		for (id in nodeset) {
			var node = nodeset[id];
			var lane = node.lane;
			
			node.x = offsetX + node.start * scale;
			node.y = lane.offset + margin;
			node.width = (node.end - node.start) * scale;
			node.height = lane.height - 2 * margin;
			node.centerX = offsetX + (node.start + (node.end - node.start)/2) * scale;
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
			var lane = node.lane;
			var clip = (node.x + 1) + ' ' + node.y + ' '+ (node.width -2) + ' ' + node.height;
			
			paper.rect(node.x, node.y, node.width, node.height, 5)
				.attr({'fill': '#d5d5df', 'stroke':'#889', 'title' : node.name });
			
			paper.text(node.centerX, lane.center, node.name)
				.attr({'font-size':'12pt', 'clip-rect': clip});
			
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
	}
}


