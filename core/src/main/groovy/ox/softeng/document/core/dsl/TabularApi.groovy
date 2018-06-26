package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.block.Table
import com.craigburke.document.core.dom.block.table.Row
import groovy.transform.TypeChecked

/**
 * @since 07/06/2018
 */
@TypeChecked
trait TabularApi implements Api {

    abstract Table getTable()

    TabularApi row(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Row) Closure closure) {
        row([:], closure)
    }

    TabularApi row(Map attributes, @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Row) Closure closure) {
        Row row = Row.create(attributes)
        table.addToChildren(row)
        row.setNodeProperties(attributes)

        callClosure closure, row

        this
    }
}
