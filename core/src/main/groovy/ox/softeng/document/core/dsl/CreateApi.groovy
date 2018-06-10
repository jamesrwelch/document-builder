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

    Document document(@DelegatesTo(DocumentApi) Closure closure) {
        document([:], closure)
    }

    Document document(Map attributes = [:], @DelegatesTo(DocumentApi) Closure closure = null) {
        Document document = new Document(attributes)
        document.setNodeProperties(attributes)

        builder.document = document
        builder.initializeDocument(document, builder.out)

        callClosure closure, new DocumentApi(builder, document)

        builder.writeDocument(document, builder.out)
        document
    }
}
