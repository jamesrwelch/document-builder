package com.craigburke.document.core.dom.text

import com.craigburke.document.core.dom.block.Document

/**
 * Created by craig on 3/25/15.
 */
class Heading extends TextNode<Document> {
    static final FONT_SIZE_MULTIPLIERS = [2, 1.5, 1.17, 1.12, 0.83, 0.75]
    int level = 1

    @Override
    String[] getTemplateKeys(String nodeKey) {
        def keys = super.getTemplateKeys(nodeKey)
        keys += "heading${level}"
        if (style) keys += "heading${level}.${style}"
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
}
