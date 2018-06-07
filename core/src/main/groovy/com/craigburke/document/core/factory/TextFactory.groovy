package com.craigburke.document.core.factory

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.block.text.TextBlock
import com.craigburke.document.core.dom.text.Text

/**
 * Factory for text nodes
 * @author Craig Burke
 */
class TextFactory extends AbstractFactory {

    boolean isLeaf() { true }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, node, Map attributes) { false }

    def newInstance(FactoryBuilderSupport builder, name, value, Map attributes) {
        TextBlock paragraph
        if (builder.parentName == 'paragraph') {
            paragraph = builder.current
        } else {
            paragraph = builder.getColumnParagraph(builder.current)
        }

        List<BaseNode> elements = paragraph.add(value as String)

        elements.findAll { it instanceof Text }.each { Text text ->
            text.style = attributes.style
            builder.setNodeProperties(text, attributes, 'text')
        }

        elements
    }

}
