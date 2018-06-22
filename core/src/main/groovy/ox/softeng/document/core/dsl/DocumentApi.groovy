package ox.softeng.document.core.dsl

import com.craigburke.document.core.dom.attribute.EmbeddedFont
import com.craigburke.document.core.dom.block.Document

import com.craigburke.document.core.builder.DocumentBuilder

/**
 * @since 31/05/2018
 */
class DocumentApi extends SectionApi implements TableApi<Document> {

    DocumentApi(DocumentBuilder builder, Document document) {
        super(builder, document)
    }

    @Override
    Document getCurrentNode() {
        document
    }

    DocumentApi addFont(Map params, String location) {
        EmbeddedFont embeddedFont = new EmbeddedFont(params)
        embeddedFont.file = new File(location)
        addFont(embeddedFont)
    }

    DocumentApi addFont(EmbeddedFont embeddedFont) {
        document.addToEmbeddedFonts embeddedFont
        this
    }
}
