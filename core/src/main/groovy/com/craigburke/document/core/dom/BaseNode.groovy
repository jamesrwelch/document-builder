package com.craigburke.document.core.dom

import com.craigburke.document.core.dom.attribute.ParentAware
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Document

/**
 * The base node for all document nodes
 * @author Craig Burke
 */
abstract class BaseNode implements ParentAware {
    def element
    BaseNode parent
    String name

    BaseNode() {
        name = getClass().simpleName.toLowerCase()
    }

    String[] getTemplateKeys(String nodeKey) {
        [nodeKey]
    }

    @Override
    Document getDocument() {
        parent instanceof Document ? parent : parent.getDocument()
    }

    void setParent(BaseNode parent) {
        this.@parent = parent
        if (parent instanceof BlockNode) {
            parent.children << this
        }
    }

    void setNodeProperties(Map attributes) {
        String[] templateKeys = getTemplateKeys(name)
        List<Map> nodeProperties = []

        templateKeys.each {String key ->
            if (document.template && document.templateMap.containsKey(key)) {
                nodeProperties << document.templateMap[key]
            }
        }
        nodeProperties << attributes

        setNodeProperties(nodeProperties)
    }

    void setNodeProperties(List<Map> nodePropertiesMap) {
    }
}
