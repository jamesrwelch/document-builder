package com.craigburke.document.builder

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.LineBreak
import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.block.Document
import com.craigburke.document.core.dom.block.text.TextBlock
import com.craigburke.document.core.dom.text.Text

import com.craigburke.document.builder.render.ParagraphRenderer

import spock.lang.Specification

/**
 * Base test for all Render Elements
 * @author Craig Burke
 */
class RendererTestBase extends Specification {

    final float defaultLineHeight = 19f

    Document makeDocument() {
        new Document(margin: Document.DEFAULT_MARGIN, font: new Font())
    }

    TextBlock makeParagraph(TextBlock paragraph, BaseNode parent = makeDocument()) {
        TextBlock newParagraph = paragraph.clone()
        newParagraph.parent = parent
        parent.children << newParagraph
        newParagraph
    }

    TextBlock makeParagraph(int lineCount, BaseNode parent = makeDocument()) {
        TextBlock paragraph = new TextBlock(margin: Margin.NONE, font: new Font())
        lineCount.times {
            paragraph.children << new Text(value: "Line${it}", font: new Font())
            if (it != lineCount - 1) {
                paragraph.children << new LineBreak()
            }
        }
        paragraph.parent = parent
        parent.children << paragraph
        paragraph
    }

    ParagraphRenderer makeParagraphElement(TextBlock paragraph, Document document = makeDocument()) {
        PdfDocument pdfDocument = new PdfDocument(document)
        float width = paragraph.parent.width
        new ParagraphRenderer(paragraph, pdfDocument, 0f, width)
    }

}
