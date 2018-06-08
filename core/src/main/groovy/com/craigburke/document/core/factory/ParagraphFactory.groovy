package com.craigburke.document.core.factory

import com.craigburke.document.core.dom.attribute.Align
import com.craigburke.document.core.dom.block.Document
import com.craigburke.document.core.dom.block.Paragraph
import com.craigburke.document.core.dom.text.Text

/**
 * Factory for paragraph nodes
 * @author Craig Burke
 */
@Deprecated
class ParagraphFactory extends AbstractFactory {

    boolean isLeaf() { false }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, node, Map attributes) { false }

    def newInstance(FactoryBuilderSupport builder, name, value, Map attributes) {
        Paragraph paragraph = new Paragraph(attributes)
        paragraph.parent = builder.parentName == 'create' ? builder.document : builder.current
        builder.setNodeProperties(paragraph, attributes, 'paragraph')

        if (paragraph.parent instanceof Document) {
            paragraph.align = paragraph.align ?: Align.LEFT
        }

        if (value) {
            List elements = paragraph.add(value.toString())
            elements.each { node ->
                if (node instanceof Text) {
                    builder.setNodeProperties(node, [:], 'text')
                }
            }
        }

        paragraph
    }

    void onNodeCompleted(FactoryBuilderSupport builder, parent, child) {
        if (builder.onTextBlockComplete) {
            builder.onTextBlockComplete(child)
        }
    }

}
