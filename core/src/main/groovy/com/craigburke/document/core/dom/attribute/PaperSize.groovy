package com.craigburke.document.core.dom.attribute

import com.craigburke.document.core.dom.attribute.Dimension

/**
 * Standard paper size utility. Returned dimensions are inches.
 */
enum PaperSize {

    A1(23.4, 33.1),
    A2(16.5, 23.4),
    A3(11.7, 16.5),
    A4(8.27, 11.7),
    A5(5.83, 8.27),
    A6(4.13, 5.83),
    LETTER(8.5, 11.0),
    LEGAL(8.5, 14.0)

    Dimension dimension

    PaperSize(BigDecimal width, BigDecimal height) {
        dimension = new Dimension(width, height)
    }

    BigDecimal getWidth() {
        dimension.width
    }

    BigDecimal getHeight() {
        dimension.height
    }
}
