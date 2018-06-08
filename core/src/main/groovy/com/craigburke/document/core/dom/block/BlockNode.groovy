package com.craigburke.document.core.dom.block

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.Alignable
import com.craigburke.document.core.dom.attribute.Border
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.attribute.Stylable

/**
 * The base node for all block nodes
 * @author Craig Burke
 */
abstract class BlockNode<T extends BaseNode> extends BaseNode implements Stylable, Alignable {

    Margin margin = new Margin()
    Border border = new Border()

    List<T> children = []

    abstract Margin getDefaultMargin()

    @Override
    String[] getTemplateKeys(String nodeKey) {
        def keys = super.getTemplateKeys(nodeKey)
        if (style) keys += "${nodeKey}.${style}"
        keys
    }

    @Override
    void setNodeFont(List<Map> nodeProperties) {
        font = cloneParentFont()
        nodeProperties.each {
            font << it.font
        }
    }

    BlockNode addToChildren(T child) {
        child.parent = this
        this
    }

    @Override
    void setNodeProperties(List<Map> nodePropertiesMap) {
        super.setNodeProperties(nodePropertiesMap)
        setNodeFont(nodePropertiesMap)
        margin = defaultMargin.clone()
        nodePropertiesMap.each {
            margin << it.margin
            if (it.border) {
                border << it.border
            }
        }
    }
}
