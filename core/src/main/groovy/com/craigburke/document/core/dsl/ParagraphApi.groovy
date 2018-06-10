package com.craigburke.document.core.dsl

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.Image
import com.craigburke.document.core.dom.LineBreak
import com.craigburke.document.core.dom.block.Paragraph
import com.craigburke.document.core.dom.text.Link
import com.craigburke.document.core.dom.text.Text

import com.craigburke.document.core.builder.DocumentBuilder

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * @since 07/06/2018
 */
class ParagraphApi implements Api {

    DocumentBuilder builder
    Paragraph paragraph

    ParagraphApi(DocumentBuilder builder) {
        this.builder = builder
    }

    ParagraphApi(DocumentBuilder builder, Paragraph paragraph) {
        this(builder)
        this.paragraph = paragraph
    }

    ParagraphApi text(Map attributes = [:], String text) {

        List<BaseNode> elements = paragraph.add(text, false)

        (elements.findAll {it instanceof Text} as List<Text>).each {textNode ->
            textNode.style = attributes.style
            textNode.setNodeProperties(attributes)
        }

        this
    }

    ParagraphApi link(Map attributes = [:], String value) {
        link(attributes, attributes.url ?: value, attributes.value ?: value)

    }

    ParagraphApi link(Map attributes = [:], String url, String displayText) {

        List elements = paragraph.add(displayText, true)

        (elements.findAll {it instanceof Link} as List<Link>).each {link ->
            link.url = url
            link.style = attributes.style
            link.setNodeProperties(attributes)
        }

        this
    }

    ParagraphApi image(Map attributes = [:], String url) {
        attributes.url = url
        image(attributes)
    }

    ParagraphApi image(Map attributes = [:], byte[] data) {
        attributes.data = data
        image(attributes)
    }

    ParagraphApi image(Map attributes) {
        Image image = new Image(attributes)

        if (!image.width || !image.height) {
            BufferedImage bufferedImage = image.withInputStream {ImageIO.read(it)} as BufferedImage
            if (bufferedImage == null) {
                throw new IllegalStateException("could not read image $attributes")
            }
            if (image.width) {
                image.height = image.width * (bufferedImage.height / bufferedImage.width)
            } else if (image.height) {
                image.width = image.height * (bufferedImage.width / bufferedImage.height)
            } else {
                image.width = bufferedImage.width
                image.height = bufferedImage.height
            }
        }

        image.hashName = "${image.hash}.${image.type.value}"
        builder.imageFileNames << image.hashName

        paragraph.addToChildren(image)

        this
    }

    ParagraphApi lineBreak() {
        paragraph.addToChildren(new LineBreak())
        this
    }
}
