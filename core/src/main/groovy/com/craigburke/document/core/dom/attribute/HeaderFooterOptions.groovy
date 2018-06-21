package com.craigburke.document.core.dom.attribute

import java.time.OffsetDateTime

/**
 * Options for generated page headers and footers
 * @author Craig Burke
 */
class HeaderFooterOptions {
    OffsetDateTime dateGenerated
    String pageCount
    String pageNumber
}
