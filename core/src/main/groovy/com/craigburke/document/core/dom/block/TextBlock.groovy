package com.craigburke.document.core.dom.block

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.LineBreak
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.Bookmarkable
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.text.Link
import com.craigburke.document.core.dom.text.Text

import groovy.transform.AutoClone

/**
 * Block element that holds text and images
 * @author Craig Burke
 */
@AutoClone
class TextBlock extends BlockNode<BaseNode> implements Bookmarkable, BackgroundAssignable {
    static Margin DEFAULT_MARGIN = new Margin(top: 12, bottom: 12, left: 0, right: 0)

    Integer lineSpacing
    BigDecimal lineSpacingMultiplier = 1.15

    String name

    String getText() {
        children.findAll {it instanceof Text}*.value.join('')
    }

    List<BaseNode> add(String value, boolean link = false) {
        List<BaseNode> elements = []
        String[] textSections = value.split('\n')

        textSections.each { String section ->
            Text element = link ? new Link(value: section, parent: this) : new Text(value: section, parent: this)

            elements << element

            if (section != textSections.last()) {
                elements << new LineBreak(parent: this)
            }
        }

        if (value.endsWith('\n')) {
            elements << new LineBreak(parent: this)
        }

        elements
    }

    @Override
    Margin getDefaultMargin() {
        DEFAULT_MARGIN
    }
}
