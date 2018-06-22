package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.builder.DocumentBuilder

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
            // The following should be defined before the document is actually created
            if (attributes.header) header attributes.remove('header') as Closure
            if (attributes.footer) footer attributes.remove('footer') as Closure
            if (attributes.template) template attributes.remove('template') as Closure

            Document document = builder.createDocument(attributes)
            document.templateMap = builder.templateMap ?: [:]
            document.setNodeProperties(attributes)

            callClosure closure, new DocumentApi(builder, document)

            builder.writeDocument()
        } finally {
            builder.close()
        }
        this
    }

    CreateApi template(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TemplateApi) Closure closure) {
        if (builder.document) throw new IllegalStateException('Cannot define global template after document is defined')
        TemplateApi templateApi = new TemplateApi()
        callClosure(closure, templateApi)
        builder.templateMap = templateApi.templateMap
        this

    }

    CreateApi header(@ClosureParams(value = SimpleType, options = 'com.craigburke.document.core.dom.attribute.HeaderFooterOptions')
                     @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DocumentApi) Closure closure) {
        builder.headerClosure = closure
        this
    }

    CreateApi footer(@ClosureParams(value = SimpleType, options = 'com.craigburke.document.core.dom.attribute.HeaderFooterOptions')
                     @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DocumentApi) Closure closure) {
        builder.footerClosure = closure
        this
    }
}
