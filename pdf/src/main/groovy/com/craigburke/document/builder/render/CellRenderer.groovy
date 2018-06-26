package com.craigburke.document.builder.render

import com.craigburke.document.builder.PdfDocument
import com.craigburke.document.core.dom.block.Paragraph
import com.craigburke.document.core.dom.block.Table
import com.craigburke.document.core.dom.block.table.Cell

/**
 * Rendering element for the cell node
 * @author Craig Burke
 */
class CellRenderer implements Renderable {

    float currentRowHeight = 0
    float renderedHeight = 0

    Cell cell
    List<Renderable> childRenderers = []

    CellRenderer(Cell cell, PdfDocument pdfDocument, float startX) {
        this.cell = cell
        this.startX = startX
        this.pdfDocument = pdfDocument

        Table table = cell.parent.parent
        BigDecimal renderWidth = cell.width - (table.padding * 2)
        BigDecimal childStartX = startX + table.padding
        cell.children.each { child ->
            if (child instanceof Paragraph) {
                childRenderers << new TextBlockRenderer(child, pdfDocument, childStartX, renderWidth)
            }
            else if (child instanceof Table) {
                childRenderers << new TableRenderer(child, pdfDocument, childStartX)
            }
        }
    }

    float getRowspanHeight() {
        cell.rowspanHeight + currentRowHeight
    }

    private float getPadding() {
        cell.parent.parent.padding
    }

    boolean getFullyParsed() {
        if (cell.rowspan > 1 && !onLastRowspanRow) {
            return true
        }
        childRenderers.every { it.fullyParsed }
    }

    float getTotalHeight() {
        (childRenderers*.totalHeight.sum() ?: 0f) + (padding * 2)
    }

    float getParsedHeight() {
        if (!childRenderers || !onLastRowspanRow) {
            return 0
        }
        float parsedHeight = (childRenderers*.currentHeight.sum() ?: 0f) as float

        if (onFirstPage && parsedHeight) {
            parsedHeight += padding
        }
        if (fullyParsed) {
            parsedHeight += padding
        }
        if (cell.rowspan > 1) {
            parsedHeight -= cell.rowspanHeight
        }
        parsedHeight
    }

    void renderElement(float startY) {
        float childY = startY
        if (cell.rowspan > 1) {
            childY -= cell.rowspanHeight
        }
        if (onFirstPage) {
            childY += padding
        }
        if (onLastRowspanRow) {
            childRenderers*.render(childY)
        }
        else {
            cell.rowspanHeight += currentRowHeight
            currentRowHeight = 0
        }
        renderedHeight = parsedHeight
    }

    void parse(float height) {
        if (height < 0) {
            return
        }
        float parseHeight = height - padding
        childRenderers*.parse(parseHeight)
    }

    boolean isOnLastRowspanRow() {
        (cell.rowspan == 1) || (cell.rowsSpanned == (cell.rowspan - 1))
    }

}

