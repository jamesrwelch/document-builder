package com.craigburke.document.core.dom.block.table

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.block.BlockNode

/**
 * An individual cell for the Table node
 * @author Craig Burke
 */
class Cell extends BlockNode<BaseNode> implements BackgroundAssignable {
    Integer width
    Integer colspan = 1
    
    Integer rowspan = 1
    Integer rowsSpanned = 0
    BigDecimal rowspanHeight = 0
}
