package com.craigburke.document.core.dom.attribute

enum Align {
    LEFT('left'),
    RIGHT('right'),
    CENTER('center')

    String value

    Align(String value) {
        this.value = value
    }
}
