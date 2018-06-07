package com.craigburke.document.core.factory

import com.craigburke.document.core.dom.LineBreak
import com.craigburke.document.core.dom.block.text.TextBlock

/**
 * Factory for line break nodes
 * @author Craig Burke
 */
class LineBreakFactory extends AbstractFactory {

    boolean isLeaf() { true }

    def newInstance(FactoryBuilderSupport builder, name, value, Map attributes) {
        LineBreak lineBreak = new LineBreak()

        TextBlock paragraph
        if (builder.parentName == 'paragraph') {
            paragraph = builder.current
        } else {
            paragraph = builder.getColumnParagraph(builder.current)
        }
        lineBreak.parent = paragraph
        paragraph.children << lineBreak

        lineBreak
    }

}
