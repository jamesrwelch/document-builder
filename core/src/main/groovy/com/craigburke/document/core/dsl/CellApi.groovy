package com.craigburke.document.core.dsl

import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.block.TextBlock
import com.craigburke.document.core.dom.block.table.Cell

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
class CellApi implements SectionApi, TableApi<Cell> {

    DocumentBuilder builder
    Cell cell

    CellApi(DocumentBuilder builder, Cell cell) {
        this.cell = cell
        this.builder = builder
    }

    @Override
    TextBlock getParagraph() {
        if (cell.children && cell.children.first() instanceof TextBlock) {
            cell.children.first() as TextBlock
        } else {
            TextBlock paragraph = new TextBlock(font: cell.font.clone() as Font, parent: cell, align: cell.align)
            paragraph.setNodeProperties(margin: Margin.NONE)
        }
    }

    @Override
    Cell getCurrentNode() {
        cell
    }
}
