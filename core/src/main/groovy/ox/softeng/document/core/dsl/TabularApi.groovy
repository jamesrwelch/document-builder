package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.block.Table
import com.craigburke.document.core.dom.block.table.Row

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
class TabularApi implements Api {

    DocumentBuilder builder
    Table table

    TabularApi(DocumentBuilder builder, Table table) {
        this.table = table
        this.builder = builder
    }

    TabularApi row(@DelegatesTo(RowApi) Closure closure) {
        row([:], closure)
    }

    TabularApi row(Map attributes, @DelegatesTo(RowApi) Closure closure) {
        Row row = new Row(attributes)
        table.addToChildren(row)
        row.setNodeProperties(attributes)

        callClosure closure, new RowApi(builder, row)

        this
    }
}
