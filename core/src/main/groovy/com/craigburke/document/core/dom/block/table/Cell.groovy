package com.craigburke.document.core.dom.block.table

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Table

/**
 * An individual cell for the Table node
 * @author Craig Burke
 */
class Cell extends BlockNode<Row, BaseNode> implements BackgroundAssignable {
    Integer width
    Integer colspan = 1
    
    Integer rowspan = 1
    Integer rowsSpanned = 0
    BigDecimal rowspanHeight = 0

    List<Table> findAllChildTables() {
        children.findAll {it instanceof Table} as List<Table>
    }

    Table getTable() {
        parent.parent
    }

    @Override
    Margin getDefaultMargin() {
        Margin.NONE
    }

    @Override
    void setNodeProperties(List<Map> nodePropertiesMap) {
        super.setNodeProperties(nodePropertiesMap)
        setNodeBackground(nodePropertiesMap)
    }
}
