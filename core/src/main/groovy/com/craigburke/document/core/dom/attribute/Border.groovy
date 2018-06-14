package com.craigburke.document.core.dom.attribute

/**
 * border settings for document block nodes
 * @author Craig Burke
 */
class Border implements ColorAssignable {
    BigDecimal size = 1.0

    def leftShift(Map properties) {
        properties?.each { key, value -> this[key] = value }
    }
}
