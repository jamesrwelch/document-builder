package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.attribute.EmbeddedFont
import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 31/05/2018
 */
class DocumentApi extends SectionApi {

    DocumentApi(DocumentBuilder builder, Document document) {
        super(builder, document)
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
}
