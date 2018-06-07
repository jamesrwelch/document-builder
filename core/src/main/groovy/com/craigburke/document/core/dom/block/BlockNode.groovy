package com.craigburke.document.core.dom.block

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.Alignable
import com.craigburke.document.core.dom.attribute.Border
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.attribute.Stylable

/**
 * The base node for all block nodes
 * @author Craig Burke
 */
class BlockNode extends BaseNode implements Stylable, Alignable {
    static Margin defaultMargin = new Margin(top: 0, bottom: 0, left: 0, right: 0)
    Margin margin = new Margin()
    Border border = new Border()
}
