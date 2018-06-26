package com.craigburke.document.core.dom

import com.craigburke.document.core.dom.block.Paragraph
import groovy.transform.TypeChecked

/**
 * Line break node
 * @author Craig Burke
 */
@TypeChecked
class LineBreak extends BaseNode<Paragraph> {
    Integer height = 0
}
