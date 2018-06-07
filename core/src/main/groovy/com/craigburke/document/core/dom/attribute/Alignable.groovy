package com.craigburke.document.core.dom.attribute

import com.craigburke.document.core.dom.attribute.Align

trait Alignable {
    Align align = Align.LEFT

    void setAlign(String value) {
        align = Enum.valueOf(Align, value.toUpperCase())
    }
}
