package com.craigburke.document.core.builder

import com.craigburke.document.core.dom.attribute.HeaderFooterOptions
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.unit.UnitCategory

import ox.softeng.document.core.dsl.CreateApi
import ox.softeng.document.core.dsl.DocumentApi

/**
 * Document Builder base class
 * @author Craig Burke
 */
abstract class DocumentBuilder<T extends Document> {

    T document
    OutputStream out
    RenderState renderState = RenderState.PAGE
    List<String> imageFileNames = []
    Closure headerClosure
    Closure footerClosure
    Map templateMap

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
            buildNode(headerFooterOptions, headerClosure)
        }
    }

    BlockNode buildFooterNode(HeaderFooterOptions headerFooterOptions) {
        use(UnitCategory) {
            buildNode(headerFooterOptions, footerClosure)
        }
    }

    protected BlockNode buildNode(HeaderFooterOptions headerFooterOptions, Closure closure) {
        HeaderFooterDocument document = new HeaderFooterDocument()
        document.setNodeProperties([:])
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = new DocumentApi(this, document)
        closure.call(headerFooterOptions)
        document.children.first() as BlockNode
    }
}

