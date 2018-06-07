package com.craigburke.document.core.dom.attribute

import com.craigburke.document.core.dom.attribute.Font

trait Stylable implements ParentAware {
    Font font
    String style
}
