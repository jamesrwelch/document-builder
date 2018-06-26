package com.craigburke.document.core.dom.attribute

enum ImageType {
    PNG('png'),
    JPG('jpg')

    String value

    ImageType(String value) {
        this.value = value
    }
}
