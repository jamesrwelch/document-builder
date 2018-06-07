package com.craigburke.document.core.dom.attribute

import com.craigburke.document.core.dom.attribute.Color

trait BackgroundAssignable {
    Color background
    
    void setBackground(String value) {
        if (value) {
            background = background ?: new Color()
            background.color = value
        }
    }
}
