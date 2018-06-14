package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.builder.DocumentBuilder

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
}
