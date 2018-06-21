package com.craigburke.document.core.builder

import com.craigburke.document.core.dom.attribute.HeaderFooterOptions
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.unit.UnitCategory

import ox.softeng.document.core.dsl.CreateApi

/**
 * Document Builder base class
 * @author Craig Burke
 */
abstract class DocumentBuilder<T extends Document> {

    T document
    OutputStream out
    RenderState renderState = RenderState.PAGE
    List<String> imageFileNames = []

    DocumentBuilder(OutputStream out) {
        this.out = out
    }

    DocumentBuilder(File file) {
        this(new FileOutputStream(file))
    }

    abstract T createDocument(Map attributes)

    abstract void writeDocument()

    void close() {
        out.close()
    }

    DocumentBuilder create(@DelegatesTo(CreateApi) Closure closure) {
        use(UnitCategory) {
            CreateApi createApi = new CreateApi(this)
            closure.delegate = createApi
            closure.call()
        }
        this
    }

    BlockNode buildHeaderNode(HeaderFooterOptions headerFooterOptions) {
        use(UnitCategory) {
            CreateApi createApi = new CreateApi(this)
            createApi.header(headerFooterOptions, document.header)
        }
    }

    BlockNode buildFooterNode(HeaderFooterOptions headerFooterOptions) {
        use(UnitCategory) {
            CreateApi createApi = new CreateApi(this)
            createApi.footer(headerFooterOptions, document.footer)
        }
    }
}

