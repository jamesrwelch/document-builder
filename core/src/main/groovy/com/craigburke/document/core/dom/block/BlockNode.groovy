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
abstract class BlockNode<P extends BaseNode, C extends BaseNode> extends BaseNode<P> implements Stylable, Alignable {

    Margin margin = new Margin()
    Border border = new Border()

    List<C> children = []

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

    BlockNode addToChildren(C child) {
        child.parent = this
        this
    }

    BlockNode addToChildren(Integer indexToAddAt, C child) {
        children.add(indexToAddAt, child)
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
