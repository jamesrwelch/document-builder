package com.craigburke.document.core.dom.attribute

/**
 * Font config object
 * @author Craig Burke
 */
class Font implements ColorAssignable, Cloneable {
    String family = 'Helvetica'
    BigDecimal size = 12.0
    Boolean bold = false
    Boolean italic = false
    Boolean underline = false

    def leftShift(Map properties) {
        properties?.each { key, value -> this[key] = value }
    }

    @Override
    Font clone() {
        Font result = new Font(family: family, size: size, bold: bold, italic: italic, underline: underline)
        result.color = "#${color.hex}"
        result
    }
}
