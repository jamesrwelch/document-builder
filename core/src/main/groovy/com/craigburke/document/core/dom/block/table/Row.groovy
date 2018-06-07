package com.craigburke.document.core.dom.block.table

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.Stylable

/**
 * Table row node
 * @author Craig Burke
 */
class Row extends BaseNode implements Stylable, BackgroundAssignable {
    List<Cell> children = []
    Integer width
}
