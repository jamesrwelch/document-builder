package com.craigburke.document.core.dom

import com.craigburke.document.core.dom.attribute.ParentAware

/**
 * The base node for all document nodes
 * @author Craig Burke
 */
abstract class BaseNode implements ParentAware {
    def element
}
