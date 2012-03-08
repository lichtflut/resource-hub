function initTree(treeOrientation){
    //init Spacetree
    var st = new $jit.ST({
		orientation: treeOrientation, 
		constrained: false, 
    	offsetY: 130,
		levelsToShow: 20,
        injectInto: 'infovis',
        duration: 500,
        transition: $jit.Trans.Quart.easeInOut,
        levelDistance: 50,
        Navigation: {
          enable:true,
          panning:true
        },
        Node: {
            width: 110,
			height:40,
            type: 'rectangle',
            color: '#fff',
            overridable: true,
			collapsed: false
        },
        Edge: {
            type: 'bezier',
            overridable: true
        },
        onCreateLabel: function(label, node){
            label.id = node.id;            
            label.innerHTML = node.name;
            label.onclick = function(){
              	st.onClick(node.id);
            };
        },
        onBeforePlotLine: function(adj){
            if (adj.nodeFrom.selected && adj.nodeTo.selected) {
                adj.data.$color = "#eed";
                adj.data.$lineWidth = 3;
            }
            else {
                delete adj.data.$color;
                delete adj.data.$lineWidth;
            }
        }
    });

    st.loadJSON(root);
    st.compute();
    st.geom.translate(new $jit.Complex(1, -280), "current");
    st.onClick(st.root);   
}
