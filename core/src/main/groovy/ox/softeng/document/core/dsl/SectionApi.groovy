package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.PageBreak
import com.craigburke.document.core.dom.attribute.Align
import com.craigburke.document.core.dom.block.Document
import com.craigburke.document.core.dom.block.Paragraph
import com.craigburke.document.core.dom.text.Heading

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 31/05/2018
 */
class SectionApi {

    DocumentBuilder builder
    Document document

    SectionApi(DocumentBuilder builder, Document document) {
        this.document = document
        this.builder = builder
    }

    SectionApi paragraph(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParagraphApi) Closure closure) {
        paragraph([:], closure)
    }

    SectionApi paragraph(Map attributes = [:], String text) {
        handleParagraph(attributes, null, text)
    }

    SectionApi paragraph(Map attributes = [:], String text,
                         @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParagraphApi) Closure closure) {
        handleParagraph(attributes, closure, text)
    }

    SectionApi paragraph(Map attributes = [:], @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParagraphApi) Closure closure = null) {
        handleParagraph(attributes, closure, null)
    }

    SectionApi pageBreak() {
        document.addToChildren(new PageBreak())
        this
    }

    SectionApi heading1(Map attributes = [:], String text) {
        handleHeader(attributes, 1, text)
    }

    SectionApi heading2(Map attributes = [:], String text) {
        handleHeader(attributes, 2, text)
    }

    SectionApi heading3(Map attributes = [:], String text) {
        handleHeader(attributes, 3, text)
    }

    SectionApi heading4(Map attributes = [:], String text) {
        handleHeader(attributes, 4, text)
    }

    SectionApi heading5(Map attributes = [:], String text) {
        handleHeader(attributes, 5, text)
    }

    SectionApi heading6(Map attributes = [:], String text) {
        handleHeader(attributes, 6, text)
    }

    protected SectionApi handleHeader(Map attributes, Integer level, String text) {
        Heading heading = new Heading(attributes)
        heading.level = level
        document.addToChildren(heading)
        heading.setNodeProperties(attributes)
        heading.value = text
        this
    }

    protected SectionApi handleParagraph(Map attributes, Closure closure, String text) {
        Paragraph paragraph = new Paragraph(attributes)
        document.addToChildren(paragraph)
        paragraph.setNodeProperties(attributes)

        if (paragraph.parent instanceof Document) {
            paragraph.align = paragraph.align ?: Align.LEFT
        }
        ParagraphApi paragraphApi = new ParagraphApi(builder, paragraph)
        if (text) {
            paragraphApi.text(attributes, text)
        }
        if (closure) {
            callClosure closure, paragraphApi
        }
        this
    }
}
