package com.craigburke.document.core.dsl

import com.craigburke.document.core.dom.attribute.Align
import com.craigburke.document.core.dom.attribute.EmbeddedFont
import com.craigburke.document.core.dom.block.Document
import com.craigburke.document.core.dom.block.Paragraph

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 31/05/2018
 */
class DocumentApi implements TableApi<Document> {

    DocumentBuilder builder
    Document document

    DocumentApi(DocumentBuilder builder, Document document) {
        this.document = document
        this.builder = builder
    }

    void addFont(Map params, String location) {
        EmbeddedFont embeddedFont = new EmbeddedFont(params)
        embeddedFont.file = new File(location)
        addFont(embeddedFont)
    }

    void addFont(EmbeddedFont embeddedFont) {
        document.embeddedFonts << embeddedFont
        if (builder.addEmbeddedFont) {
            builder.addEmbeddedFont(embeddedFont)
        }
    }

    DocumentApi paragraph(@DelegatesTo(ParagraphApi) Closure closure) {
        paragraph([:], closure)
    }

    DocumentApi paragraph(Map attributes = [:], String text) {
        handleParagraph(attributes, null, text)
    }

    DocumentApi paragraph(Map attributes = [:], @DelegatesTo(ParagraphApi) Closure closure = null) {
        handleParagraph(attributes, closure, null)
    }

    private DocumentApi handleParagraph(Map attributes, Closure closure, String text) {
        Paragraph paragraph = new Paragraph(attributes)
        document.addToChildren(paragraph)
        paragraph.setNodeProperties(attributes)

        if (paragraph.parent instanceof Document) {
            paragraph.align = paragraph.align ?: Align.LEFT
        }
        ParagraphApi paragraphApi = new ParagraphApi(builder, paragraph)
        if (closure) {
            callClosure closure, paragraphApi
        } else if (text) {
            paragraphApi.text(attributes, text)
        }

        if (builder.onTextBlockComplete) {
            builder.onTextBlockComplete(paragraph)
        }

        this
    }

    @Override
    Document getCurrentNode() {
        document
    }
}
