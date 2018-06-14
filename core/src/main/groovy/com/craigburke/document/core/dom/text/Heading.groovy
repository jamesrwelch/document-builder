package com.craigburke.document.core.dom.text

import com.craigburke.document.core.dom.attribute.Alignable
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.attribute.MarginAssignable
import com.craigburke.document.core.dom.attribute.TextBlockType
import com.craigburke.document.core.dom.block.Document

/**
 * Created by craig on 3/25/15.
 */
class Heading extends TextNode<Document> implements MarginAssignable, TextBlockType, Alignable {
    static Margin DEFAULT_MARGIN = new Margin(top: 12, bottom: 12, left: 0, right: 0)
    static final FONT_SIZE_MULTIPLIERS = [2, 1.5, 1.17, 1.12, 0.83, 0.75]
    int level = 1

    BigDecimal lineSpacing
    BigDecimal lineSpacingMultiplier = 1.15

    @Override
    List<String> getTemplateKeys(String nodeKey) {
        def keys = super.getTemplateKeys(nodeKey)
        keys += "heading${level}".toString()
        if (style) keys += "heading${level}.${style}".toString()
        keys
    }

    @Override
    void setNodeFont(List<Map> nodeProperties) {
        font = cloneParentFont()
        font.size = null
        nodeProperties.each {
            font << it.font
        }
        if (!font.size) {
            font.size = document.font.size * Heading.FONT_SIZE_MULTIPLIERS[level - 1]
        }
    }

    @Override
    Margin getDefaultMargin() {
        DEFAULT_MARGIN
    }
}
