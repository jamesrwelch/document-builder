package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.Image
import com.craigburke.document.core.dom.LineBreak
import com.craigburke.document.core.dom.block.Paragraph

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
        getParagraph().add(text, attributes)
        this
    }

    ParagraphApi link(Map attributes = [:], String value) {
        link(attributes, attributes.url ?: value, attributes.value ?: value)

    }

    ParagraphApi link(Map attributes = [:], String url, String displayText) {
        getParagraph().add(displayText, attributes, url)
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

        getParagraph().addToChildren(image)

        this
    }

    ParagraphApi lineBreak(Integer height = 0) {
        getParagraph().addToChildren(new LineBreak(height: height))
        this
    }
}
