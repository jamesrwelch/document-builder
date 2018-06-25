package com.craigburke.document.core.dom.block.table

import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Table

/**
 * Table row node
 * @author Craig Burke
 */
class Row extends BlockNode<Table, Cell> implements BackgroundAssignable {
    Integer width

    List<Cell> getCells() {
        children
    }

    Integer getNumberOfColumns() {
        cells.size()
    }

    Row addToCells(Integer indexToAddAt, Cell cell) {
        addToChildren(indexToAddAt, cell) as Row
    }

    Table getTable() {
        parent
    }

    Cell cell(Integer index) {
        children[index]
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
