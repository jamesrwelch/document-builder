package com.craigburke.document.core.dsl

import com.craigburke.document.core.dom.block.Paragraph

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
class ParagraphApi implements SectionApi {

    DocumentBuilder builder
    Paragraph paragraph

    ParagraphApi(DocumentBuilder builder, Paragraph paragraph) {
        this.paragraph = paragraph
        this.builder = builder
    }

}
