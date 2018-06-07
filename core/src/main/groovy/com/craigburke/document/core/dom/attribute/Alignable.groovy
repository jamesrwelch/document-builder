package com.craigburke.document.core.dom.attribute

trait Alignable {
    Align align = Align.LEFT

    void setAlign(String value) {
        align = Align.valueOf(value.toUpperCase())
    }
}
