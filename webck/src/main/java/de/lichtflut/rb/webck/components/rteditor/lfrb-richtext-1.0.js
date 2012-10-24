var LFRB = LFRB || {};

LFRB.RichText = {
    simple:function (editorSelector) {
        jQuery(editorSelector).wysiwyg({
            rmUnusedControls:true,
            autoGrow:false,
            initialContent:"<p>&nbsp;</p>",
            removeHeadings:true,
            controls:{
                bold:{ visible:true },
                italic:{ visible:true },
                html:{ visible:false }
            }
        });
    },
    standard:function (editorSelector) {
        jQuery(editorSelector).wysiwyg({
            rmUnusedControls:true,
            autoGrow:false,
            initialContent:"<p>&nbsp;</p>",
            removeHeadings:true,
            controls:{
                bold:{ visible:true },
                italic:{ visible:true },
                underline:{ visible:true },
                strikeThrough:{ visible:true },
                justifyLeft:{ visible:true },
                justifyCenter:{ visible:true },
                justifyRight:{ visible:true },
                justifyFull:{ visible:true },
                insertOrderedList:{ visible:true },
                insertUnorderedList:{ visible:true },
                h1:{ visible:true },
                h2:{ visible:true },
                h3:{ visible:true },
                removeFormat:{ visible:true },
                html:{ visible:false }
            }
        });
    },
    fullFeatured:function (editorSelector) {
        jQuery(editorSelector).wysiwyg({
            rmUnusedControls:false,
            removeHeadings:true,
            autoGrow:false,
            initialContent:"<p>&nbsp;</p>"
        });
    }
};
