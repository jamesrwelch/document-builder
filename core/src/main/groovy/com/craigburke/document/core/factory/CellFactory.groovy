package com.craigburke.document.core.factory

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.block.table.Cell
import com.craigburke.document.core.dom.block.table.Row
import com.craigburke.document.core.dom.block.text.TextBlock
import com.craigburke.document.core.dom.text.Text

/**
 * Factory for column nodes
 * @author Craig Burke
 */
class CellFactory extends AbstractFactory {

    boolean isLeaf() { false }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, node, Map attributes) { false }

    def newInstance(FactoryBuilderSupport builder, name, value, Map attributes) {
        Cell cell = new Cell(attributes)
        Row row = builder.current
        cell.parent = row
        builder.setNodeProperties(cell, attributes, 'cell')

        if (value) {
            TextBlock paragraph = builder.getColumnParagraph(cell)
            paragraph.add(value as String).each { BaseNode node ->
                if (node instanceof Text) {
                    builder.setNodeProperties(node, [:], 'text')
                }
            }
        }

        cell
    }

}
