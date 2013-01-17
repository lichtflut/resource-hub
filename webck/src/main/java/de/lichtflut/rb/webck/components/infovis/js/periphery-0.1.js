function initGraph() {
	//init RGraph
    var rgraph = new $jit.RGraph({
        //Where to append the visualization
        injectInto: 'infovis',
     
        //Optional: create a background canvas that plots
        //concentric circles.
        background: {
          CanvasStyles: {
            strokeStyle: '#777'
          }
        },
        //Add navigation capabilities:
        //zooming by scrolling and panning.
        Navigation: {
          enable: true,
          panning: true,
          zooming: 5
        },
        //Set Node and Edge styles.
        Node: {
            color: '#762226',
            height: 30
        },
        
        Edge: {
          color: '#762226',
          lineWidth:1
        },

        onBeforeCompute: function(node){
            $jit.id('node-details').innerHTML = node.name;
        },
        
        //Add the name of the node in the correponding label
        //and a click handler to move the graph.
        //This method is called once, on label creation.
        onCreateLabel: function(domElement, node){
            domElement.innerHTML = node.name;
            domElement.onclick = function(){
                rgraph.onClick(node.id, {
                    onComplete: function() {
                    	LFRB.InfoVis.updateNodeInfo(node.id);
                    }
                });
            };
        },
        //Change some label dom properties.
        //This method is called each time a label is plotted.
        onPlaceLabel: function(domElement, node){
            var style = domElement.style;
            style.display = '';
            style.cursor = 'pointer';

            if (node._depth <= 1) {
                style.fontSize = "0.8em";
                style.color = "#555";
            
            } else if(node._depth == 2){
                style.fontSize = "0.7em";
                style.color = "#777";
            
            } else {
                style.display = 'none';
            }

            var left = parseInt(style.left);
            var w = domElement.offsetWidth;
            style.left = (left - w / 2) + 'px';
            
            var top = parseInt(style.top);
            var h = domElement.offsetHeight;
            style.top = (top - h / 2) + 'px';
        }
    });

    //load JSON data and initialize graph
    jQuery.getJSON(LFRB.InfoVis.serviceURI, function(json){
        rgraph.loadJSON(json);
        //trigger small animation
        rgraph.graph.eachNode(function(n) {
            var pos = n.getPos();
            pos.setc(-200, -200);
        });
        rgraph.canvas.scale(1.45, 1.4);
        rgraph.compute('end');
        rgraph.fx.animate({
            modes:['polar'],
            duration: 1000
        });
        //end
        //append information about the root relations in the right column
        $jit.id('node-details').innerHTML = rgraph.graph.getNode(rgraph.root).name;
    });

}
