package com.craigburke.document.builder

import com.craigburke.document.core.dom.Image
import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.block.Document
import com.craigburke.document.core.dom.block.Table
import com.craigburke.document.core.dom.block.table.Cell
import com.craigburke.document.core.dom.block.table.Row
import com.craigburke.document.core.dom.block.text.TextBlock
import com.craigburke.document.core.dom.text.Text

import org.apache.poi.xwpf.usermodel.XWPFDocument

import static com.craigburke.document.core.unit.UnitUtil.twipToPoint

/**
 * Creates a Document object based on byte content of Word file
 * @author Craig Burke
 */
class WordDocumentLoader {

    static Document load(byte[] data) {
        Document document = new Document()
        document.element = new XWPFDocument(new ByteArrayInputStream(data))

        def documentMargin = document.element.document.body.sectPr.pgMar
        document.margin.top = twipToPoint(documentMargin.top)
        document.margin.bottom = twipToPoint(documentMargin.bottom)
        document.margin.left = twipToPoint(documentMargin.left)
        document.margin.right = twipToPoint(documentMargin.right)

        loadParagraphs(document)
        loadTables(document)
        document
    }

    static private loadParagraphs(Document document) {
        document.children = getParagraphs(document.element.paragraphs)
    }

    static private loadTables(Document document) {

        document.element.tables.each { tableItem ->
            Table table = new Table(element: tableItem, width: twipToPoint(tableItem.width), parent: document)

            tableItem.rows.each { rowItem ->
                Row row = new Row(element: rowItem, parent: table)
                table.children << row
                rowItem.tableCells.each { columnItem ->
                    Cell column = new Cell(element: columnItem, parent: row)
                    int padding = columnItem.CTTc.tcPr.tcMar.left.w
                    int width = columnItem.CTTc.tcPr.tcW.w
                    column.width = twipToPoint(width + (padding * 2))

                    column.children = getParagraphs(columnItem.paragraphs)

                    row.children << column
                }
            }

            document.children << table
        }
    }

    static private List getParagraphs(paragraphs) {
        def items = []

        paragraphs.each { paragraph ->
            if (paragraph.runs) {
                TextBlock p = new TextBlock()
                p.margin.bottom = twipToPoint(paragraph.spacingAfter)
                p.margin.top = twipToPoint(paragraph.spacingBefore)
                def indent = paragraph.CTP.PPr.ind
                p.margin.left = twipToPoint(indent?.left ?: 0)
                p.margin.right = twipToPoint(indent?.right ?: 0)

                items << p

                paragraph.runs.each { run ->
                    Font font = new Font(family: run.fontFamily, size: run.fontSize)
                    p.font = p.font ?: font

                    if (run.embeddedPictures) {
                        p.children << new Image(data: run.embeddedPictures[0].pictureData.data, parent: p)
                    } else {
                        def text = new Text(value: run.toString(), parent: p)
                        text.font = font
                        p.children << text
                    }
                }
            }

        }

        items
    }

}
