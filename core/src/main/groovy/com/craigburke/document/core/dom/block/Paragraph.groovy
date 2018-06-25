package com.craigburke.document.core.dom.block

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.Image
import com.craigburke.document.core.dom.LineBreak
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.Bookmarkable
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.attribute.TextBlockType
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.text.Link
import com.craigburke.document.core.dom.text.Text
import com.craigburke.document.core.dom.text.TextNode

import groovy.transform.AutoClone

/**
 * Block element that holds text and images
 * @author Craig Burke
 */
@AutoClone
class Paragraph extends BlockNode<Document, BaseNode> implements Bookmarkable, BackgroundAssignable, TextBlockType {
    static Margin DEFAULT_MARGIN = new Margin(top: 12, bottom: 12, left: 0, right: 0)

    BigDecimal lineSpacing
    BigDecimal lineSpacingMultiplier = 1.15

    String getText() {
        children.findAll {it instanceof Text}*.value.join('')
    }

    List<BaseNode> add(String text, Map attributes, String url = null) {
        List<BaseNode> elements = []
        String[] textSections = text.split('\n')

        textSections.each { String section ->
            TextNode element = url ? new Link(value: section, parent: this, url: url) : new Text(value: section, parent: this)
            element.style = attributes.style
            element.setNodeProperties(attributes)

            elements << element

            if (section != textSections.last()) {
                elements << new LineBreak(parent: this)
            }
        }

        if (text.endsWith('\n')) {
            elements << new LineBreak(parent: this)
        }

        elements
    }

    List<Image> getAllImages() {
        children?.findAll {it instanceof Image} as List<Image>
    }

    @Override
    Margin getDefaultMargin() {
        DEFAULT_MARGIN
    }
}
