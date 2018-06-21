package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.attribute.HeaderFooterOptions
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.builder.DocumentBuilder
import com.craigburke.document.core.builder.HeaderFooterDocument

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

/**
 * @since 31/05/2018
 */
class CreateApi implements Api {

    DocumentBuilder builder

    CreateApi(DocumentBuilder builder) {
        this.builder = builder
    }

    CreateApi document(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DocumentApi) Closure closure) {
        document([:], closure)
    }

    CreateApi document(Map<String, Object> attributes = [:],
                       @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DocumentApi) Closure closure = null) {
        try {
            Document document = builder.createDocument(attributes)
            document.setNodeProperties(attributes)

            callClosure closure, new DocumentApi(builder, document)

            builder.writeDocument()
        } finally {
            builder.close()
        }
        this
    }

    BlockNode header(HeaderFooterOptions headerFooterOptions,
                     @ClosureParams(value = SimpleType, options = 'com.craigburke.document.core.dom.attribute.HeaderFooterOptions')
                     @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DocumentApi) Closure closure) {
        HeaderFooterDocument document = new HeaderFooterDocument()
        document.setNodeProperties([:])
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = new DocumentApi(builder, document)
        closure.call(headerFooterOptions)
        document.children.first() as BlockNode
    }

    BlockNode footer(HeaderFooterOptions headerFooterOptions,
                     @ClosureParams(value = SimpleType, options = 'com.craigburke.document.core.dom.attribute.HeaderFooterOptions')
                     @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DocumentApi) Closure closure) {
        HeaderFooterDocument document = new HeaderFooterDocument()
        document.setNodeProperties([:])
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = new DocumentApi(builder, document)
        closure.call(headerFooterOptions)
        document.children.first() as BlockNode
    }
}
