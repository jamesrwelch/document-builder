package com.craigburke.document.builder.element

import com.craigburke.document.core.dom.text.TextNode

import org.apache.pdfbox.pdmodel.font.PDFont

/**
 * Rendering element for the Text node
 * @author Craig Burke
 */
class TextElement implements ParagraphElement<TextNode> {
    PDFont pdfFont
    TextNode node
    String text
    int width
}
