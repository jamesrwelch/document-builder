package com.craigburke.document.core.dsl

import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.block.Paragraph
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
    Paragraph getParagraph() {
        if (cell.children && cell.children.first() instanceof Paragraph) {
            cell.children.first() as Paragraph
        } else {
            Paragraph paragraph = new Paragraph(font: cell.font.clone() as Font, parent: cell, align: cell.align)
            paragraph.setNodeProperties(margin: Margin.NONE)
        }
    }

    @Override
    Cell getCurrentNode() {
        cell
    }
}
