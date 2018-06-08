package com.craigburke.document.core.dom.attribute

import com.craigburke.document.core.dom.block.TextBlock

trait BackgroundAssignable implements ParentAware {
    Color background

    void setBackground(String value) {
        if (value) {
            background = background ?: new Color()
            background.color = value
        }
    }

    void setNodeBackground(List<Map> nodeProperties) {
        if (parent instanceof BackgroundAssignable && !(parent instanceof TextBlock)) {
            if (!background && parent.background) {
                setBackground("#${parent.background.hex}")
            }
        }
        nodeProperties.each {
            background = it.background ?: background
        }

    }
}
