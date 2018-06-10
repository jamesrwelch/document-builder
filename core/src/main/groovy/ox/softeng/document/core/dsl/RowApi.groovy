package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.block.table.Cell
import com.craigburke.document.core.dom.block.table.Row

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
class RowApi implements Api {

    DocumentBuilder builder
    Row row

    RowApi(DocumentBuilder builder, Row row) {
        this.row = row
        this.builder = builder
    }

    RowApi cell(Map attributes = [:], String text) {
        handleCell(attributes, null, text)
    }

    RowApi cell(@DelegatesTo(CellApi) Closure closure) {
        cell([:], closure)
    }

    RowApi cell(Map attributes = [:], @DelegatesTo(CellApi) Closure closure = null) {
        handleCell(attributes, closure, null)
    }

    private RowApi handleCell(Map attributes, Closure closure, String text) {
        Cell cell = new Cell(attributes)
        row.addToChildren(cell)
        cell.setNodeProperties(attributes)
        CellApi cellApi = new CellApi(builder, cell)
        if (closure) {
            callClosure closure, cellApi
        } else if (text) {
            cellApi.text(attributes, text)
        }

        this
    }
}
