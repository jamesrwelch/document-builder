package com.craigburke.document.builder.element

import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.attribute.TextBlockType

import com.craigburke.document.builder.PdfFont

/**
 * A paragraph line
 * @author Craig Burke
 */
class TextBlockLine {

    TextBlockType textBlock
    final BigDecimal maxWidth
    BigDecimal contentWidth = 0.0
    List<ParagraphElement> elements = []

    TextBlockLine(TextBlockType textBlock, float maxWidth) {
        this.textBlock = textBlock
        this.maxWidth = maxWidth
    }

    BigDecimal getRemainingWidth() {
        maxWidth - contentWidth
    }

    BigDecimal getContentHeight() {
        elements.collect {
            if (it instanceof TextElement) {it.node.font.size} else if (it instanceof ImageElement) {it.node.height} else {0}
        }.max() ?: textBlock.font.size
    }

    BigDecimal getTotalHeight() {
        contentHeight + lineSpacing
    }

    BigDecimal getLineSpacing() {
        if (textBlock.lineSpacing) {
            textBlock.lineSpacing
        } else {
            TextElement largestElement = elements.findAll {it instanceof TextElement}.max {(it as TextElement).node.font.size} as TextElement
            Font maxFont = largestElement?.node?.font ?: textBlock.font

            BigDecimal xHeight = PdfFont.getXHeight(maxFont)
            Math.round((maxFont.size - xHeight) * textBlock.lineSpacingMultiplier)
        }
    }

}
