package com.craigburke.document.core.dom.text

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.Bookmarkable
import com.craigburke.document.core.dom.attribute.Stylable

/**
 * Text node
 * @author Craig Burke
 */
class Text extends BaseNode implements Stylable, Bookmarkable, BackgroundAssignable {
    String value
}
