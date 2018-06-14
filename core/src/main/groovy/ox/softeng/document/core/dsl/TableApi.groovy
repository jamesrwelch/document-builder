package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Document
import com.craigburke.document.core.dom.block.Table

import com.craigburke.document.core.builder.RenderState

/**
 * @since 07/06/2018
 */
trait TableApi<K extends BlockNode> implements Api {

    abstract K getCurrentNode()

    TableApi table(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TabularApi) Closure closure) {
        table([:], closure)
    }

    TableApi table(Map attributes = [:], @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TabularApi) Closure closure = null) {
        Table table = new Table(attributes)
        currentNode.addToChildren(table)

        table.setNodeProperties(attributes)

        callClosure closure, new TabularApi(builder, table)

        if (currentNode instanceof Document || builder.renderState != RenderState.PAGE) {
            table.normalizeColumnWidths()
        }

        this
    }
}