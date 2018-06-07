package com.craigburke.document.core.dsl

import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 31/05/2018
 */
class CreateApi {

    DocumentBuilder builder

    CreateApi(DocumentBuilder builder) {
        this.builder = builder
    }

    void document(Map attributes = [:], Closure closure = null) {
        Document document = new Document(attributes)
        builder.document = document
        builder.setNodeProperties(document, attributes, 'document')
        builder.initializeDocument(document, builder.out)

        DocumentApi documentApi = new DocumentApi(document)

        closure.delegate = documentApi
        closure.call()

        builder.writeDocument(builder.document, builder.out)
    }
}
