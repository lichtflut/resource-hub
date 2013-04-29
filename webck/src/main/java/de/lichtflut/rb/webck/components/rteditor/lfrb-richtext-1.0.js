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
