package com.craigburke.document.core.dom.text

import com.craigburke.document.core.dom.block.Paragraph
import groovy.transform.TypeChecked

/**
 * The node that can be associated with a URL link
 * @author Craig Burke
 */
@TypeChecked
class Link extends TextNode<Paragraph> {
    String url
}
