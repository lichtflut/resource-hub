
var GraphVisConfig = GraphVisConfig || {
    nodeHeaderHeight: 60,
    nodeWidth : 180,
    nodeHeight : 120,
    nodeMargin : 10,
    rowBreakHint: 800
};

function LayoutManager() {
    var lm = { rows: new Array, currentRow: null };
    lm.createLayout = function () {

    };
    lm.height = function() {
        return d3.sum(lm.rows, function(row) {return row.height}) + (lm.rows.length-1) * GraphVisConfig.nodeMargin;
    };
    lm.width = function() {
        return d3.max(lm.rows, function(row) {return row.width});
    };
    lm.newRow = function() {
        var offset = lm.height() + GraphVisConfig.nodeMargin;
        lm.currentRow = { width: GraphVisConfig.nodeMargin, offset: offset, height: 0, elements: 0 };
        lm.rows.push(lm.currentRow);
        return lm.currentRow;
    };
    lm.add = function(item) {
        if (lm.currentRow.elements > 0 && (lm.currentRow.width + item.width) > GraphVisConfig.rowBreakHint ) {
            lm.newRow();
        }
        item.offset.x = lm.currentRow.width;
        item.offset.y = lm.currentRow.offset;
        lm.currentRow.elements++;
        lm.currentRow.width += item.width + GraphVisConfig.nodeMargin;
        lm.currentRow.height = Math.max(lm.currentRow.height, item.height);
    };
    lm.newRow();
    return lm;
}

function StyleManager() {
    var styles = {};
    styles.apply = function(item) {
        item.title_font = item.title_font || "normal 14px Calibri, Helvetica Neue";
        item.background = item.background || styles.colorByType(item);
    };
    styles.colorByType = function(item) {
        for(var i = 0; i < item.types.length; i++) {
            var current = item.types[i];
            if(current === 'http://rb.lichtflut.de/devops#DataCenter') {
                return "#ffe";
            }
            if(current === 'http://rb.lichtflut.de/devops#PhysicalMachine') {
                return "#eef";
            }
            if(current === 'http://rb.lichtflut.de/devops#VirtualMachine') {
                return "#fee";
            }
        }
        return "#fff";
    };
    return styles;
}

function drawItem(container, item, offset) {
    new StyleManager().apply(item);

    var x = offset.x + item.offset.x;
    var y = offset.y + item.offset.y;
    var rect = container.append("rect");
    var text = container.append("text")
        .attr("x", x + 10)
        .attr("y", y + 20)
        .attr("dy", ".35em")
        .attr("text-anchor", "left")
        .style("font", item.title_font)
        .text(item.name);
    var textBox = sizeOf(text.node());

    if (LFRB.InfoVis.debug) {
        container.append("text")
        .attr("x", x +12)
        .attr("y", y +34)
        .attr("dy", ".65em")
        .attr("text-anchor", "left")
        .style("font", "12px Courier")
        .text("x:" + item.offset.x + ";y:" + item.offset.y + ";width:" + item.width + ";height:" + item.height + ";text:" +textBox.width);
    }

    container.append("text")
        .attr("x", x +10)
        .attr("y", y +30)
        .attr("dy", ".65em")
        .attr("text-anchor", "left")
        .style("font", "200 9px Calibri, Helvetica Neue")
        .style("fill", "#555")
        .text("type: " + item.primaryType);

    var bbox = text.node().getBBox();

    rect.attr("x", x)
        .attr("y", y )
        .attr("width",  Math.max(textBox.width, item.width))
        .attr("height", Math.max(textBox.height, item.height))
        .attr("rx", 5)
        .style("fill", item.background)
        .style("stroke", "#555")
        .style("stroke-width", "1px");

    var innerOffset = {x : x, y : y};
    innerOffset.y += GraphVisConfig.nodeHeaderHeight;

    drawChildren(container, item, innerOffset);

}

function drawChildren(container, item, offset) {
    if (item.children) {
        item.children.forEach(
            function (sub) {
                if (!item.isTransparent) {
                    drawItem(container, sub, offset);
                } else {
                    drawChildren(container, sub, offset);
                }
            }
        );
    }
}

function sizeOf(text) {
    var bbox = text.getBBox();
    return {
        "width" : bbox.width + GraphVisConfig.nodeMargin*2,
        "height" : bbox.height
    }
}

function layout(item) {
    item.offset = item.offset || {x:0, y: 0}
    var lm = new LayoutManager;
    if (item.children) {
        item.children.forEach(
            function (sub) {
                layout(sub);
                lm.add(sub);
            }
        );
    }
    var fullHeight = lm.height() + GraphVisConfig.nodeHeaderHeight + GraphVisConfig.nodeMargin;
    item.width = item.width || Math.max(lm.width(),GraphVisConfig.nodeWidth);
    item.height = item.height || Math.max(fullHeight, GraphVisConfig.nodeHeight);
}

function showMap() {

    d3.json(LFRB.InfoVis.serviceURI, function(error, json) {
        if (error) return console.warn(error);
        root = json;
        layout(root);
        var svg = d3.select('div#infovis').append("svg:svg")
            .attr("width", root.width + 100)
            .attr("height", root.height + 100);

        drawItem(svg, root, {x: 90, y:90});
    });

}