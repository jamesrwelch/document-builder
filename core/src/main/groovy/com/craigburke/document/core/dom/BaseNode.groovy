package com.craigburke.document.core.dom

import com.craigburke.document.core.dom.attribute.ParentAware
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Document

/**
 * The base node for all document nodes
 * @author Craig Burke
 */
abstract class BaseNode<P extends BaseNode> implements ParentAware<P> {
    P parent
    String name

    BaseNode() {
        name = Document.isAssignableFrom(getClass()) ? 'document' : getClass().simpleName.toLowerCase()
    }

    List<String> getTemplateKeys(String nodeKey) {
        [nodeKey]
    }

    @Override
    Document getDocument() {
        parent.getDocument()
    }

    void setParent(P parent) {
        this.@parent = parent
        if (parent instanceof BlockNode) {
            parent.children << this
        }
    }

    void setNodeProperties(Map attributes) {
        String[] templateKeys = getTemplateKeys(name)
        List<Map> nodeProperties = []

        templateKeys.each {String key ->
            if (document.templateMap.containsKey(key)) {
                nodeProperties << document.templateMap[key]
            }
        }
        nodeProperties << attributes

        setNodeProperties(nodeProperties)
    }

    void setNodeProperties(List<Map> nodePropertiesMap) {
    }
}
