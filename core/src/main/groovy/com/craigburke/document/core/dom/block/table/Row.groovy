package com.craigburke.document.core.dom.block.table

import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.block.BlockNode

/**
 * Table row node
 * @author Craig Burke
 */
class Row extends BlockNode<Cell> implements BackgroundAssignable {
    Integer width
}
