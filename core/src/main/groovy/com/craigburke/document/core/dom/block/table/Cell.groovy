package com.craigburke.document.core.dom.block.table

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.Alignable
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.Stylable

/**
 * An individual cell for the Table node
 * @author Craig Burke
 */
class Cell extends BaseNode implements Stylable, Alignable, BackgroundAssignable {
    List children = []
    Integer width
    Integer colspan = 1
    
    Integer rowspan = 1
    Integer rowsSpanned = 0
    BigDecimal rowspanHeight = 0
}
