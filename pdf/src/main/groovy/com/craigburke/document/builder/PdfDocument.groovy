package com.craigburke.document.builder

import com.craigburke.document.core.dom.attribute.EmbeddedFont
import com.craigburke.document.core.dom.block.Document

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle

/**
 * Document node element
 * @author Craig Burke
 */
class PdfDocument extends Document {

    float x = 0
    float y = 0

    PDDocument pdDocument
    int pageNumber = 0

    PDPageContentStream contentStream
    List<PDPage> pages = []

    PdfDocument() {
        System.setProperty('sun.java2d.cmm', 'sun.java2d.cmm.kcms.KcmsServiceProvider')
        pdDocument = new PDDocument()
        addPage()
    }

    void scrollToStartPosition() {
        x = document.margin.left ?: 0
        y = document.margin.top ?: 0
    }

    int getPageBottomY() {
        currentPage.mediaBox.height - document.margin.bottom
    }

    void saveAndClosePdf(OutputStream out) {
        contentStream?.close()
        if (out) pdDocument.save(out)
        pdDocument?.close()
    }

    private static PDRectangle getRectangle(BigDecimal width, BigDecimal height) {
        new PDRectangle(width.floatValue(), height.floatValue())
    }

    void addPage() {
        def newPage = new PDPage()
        newPage.setMediaBox(getRectangle(document.width, document.height))
        if (document.isLandscape()) {
            newPage.setRotation(90)
        }
        pages << newPage
        pageNumber++

        contentStream?.close()
        contentStream = new PDPageContentStream(pdDocument, currentPage)

        scrollToStartPosition()
        pdDocument.addPage(newPage)
    }

    PDPage getCurrentPage() {
        pages[pageNumber - 1]
    }

    void setPageNumber(int value) {
        this.pageNumber = value
        contentStream?.close()
        contentStream = new PDPageContentStream(pdDocument, currentPage, true, true)
        scrollToStartPosition()
    }

    float getTranslatedY() {
        currentPage.mediaBox.height - y
    }

    void scrollDownPage(float amount) {
        if (remainingPageHeight < amount) {
            float amountDiff = amount - remainingPageHeight
            addPage()
            y += amountDiff
        } else {
            y += amount
        }

    }

    float translateY(Number value) {
        currentPage.mediaBox.height - value
    }

    float getRemainingPageHeight() {
        (currentPage.mediaBox.height - document.margin.bottom) - y
    }

    @Override
    Document addToEmbeddedFonts(EmbeddedFont embeddedFont) {
        super.addToEmbeddedFonts(embeddedFont)
        PdfFont.addFont(pdDocument, embeddedFont)
        this
    }
}
