package com.craigburke.document.core.dsl

import com.craigburke.document.core.dom.block.TextBlock

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 07/06/2018
 */
class ParagraphApi implements SectionApi {

    DocumentBuilder builder
    TextBlock paragraph

    ParagraphApi(DocumentBuilder builder, TextBlock paragraph) {
        this.paragraph = paragraph
        this.builder = builder
    }

}
