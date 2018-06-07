package com.craigburke.document.core.dom.attribute

import com.craigburke.document.core.dom.attribute.Color

trait ColorAssignable implements ParentAware {
    Color color = new Color()

    void setColor(String value) {
        color.color = value
    }
}
