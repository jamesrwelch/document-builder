package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.block.Paragraph
import com.craigburke.document.core.dom.block.table.Cell

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
class CellApi extends ParagraphApi implements TableApi<Cell> {

    Cell cell

    CellApi(DocumentBuilder builder, Cell cell) {
        super(builder)
        this.cell = cell
    }

    @Override
    Paragraph getParagraph() {
        if (cell.children && cell.children.first() instanceof Paragraph) {
            return cell.children.first() as Paragraph
        }
        Paragraph paragraph = new Paragraph(font: cell.font.clone() as Font, parent: cell, align: cell.align)
        paragraph.setNodeProperties(margin: Margin.NONE)
        paragraph
    }

    @Override
    Cell getCurrentNode() {
        cell
    }
}
