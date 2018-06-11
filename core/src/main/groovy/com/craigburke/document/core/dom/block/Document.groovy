package com.craigburke.document.core.dom.block

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.Dimension
import com.craigburke.document.core.dom.attribute.EmbeddedFont
import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.attribute.Margin
import com.craigburke.document.core.dom.attribute.PaperSize

import static com.craigburke.document.core.unit.UnitUtil.inchToPoint

/**
 * Document node
 * @author Craig Burke
 */
class Document extends BlockNode<Document, BaseNode> {

    static final Margin DEFAULT_MARGIN = new Margin(top: 72, bottom: 72, left: 72, right: 72)

    private static final String PORTRAIT = 'portrait'
    private static final String LANDSCAPE = 'landscape'

    int pageCount
    BigDecimal width = inchToPoint(PaperSize.LETTER.width)
    BigDecimal height = inchToPoint(PaperSize.LETTER.height)
    String orientation = PORTRAIT

    def template
    def header
    def footer

    private Map templateMap

    List<EmbeddedFont> embeddedFonts = []

    Map<String, Map> getTemplateMap() {
        if (templateMap == null) {
            loadTemplateMap()
        }
        templateMap
    }

    private void loadTemplateMap() {
        templateMap = [:]
        if (template && template instanceof Closure) {
            def templateDelegate = new Expando()
            templateDelegate.metaClass.methodMissing = { name, args ->
                templateMap[name] = args[0]
            }
            template.delegate = templateDelegate
            template()
        }
    }

    /**
     * Set width and height of the document.
     *
     * @param arg name of a standard paper size ("a4", "letter", "legal")
     */
    void setSize(String arg) {
        setSize(PaperSize.valueOf(arg.toUpperCase()).dimension)
    }

    /**
     * Set width and height of the document.
     *
     * @param arg a Dimension instance
     */
    void setSize(Dimension arg) {
        width = inchToPoint(arg.width)
        height = inchToPoint(arg.height)
    }

    /**
     * Set width and height of the document.
     *
     * @param args width, height
     */
    void setSize(List<Number> args) {
        width = args[0]
        height = args[1]
    }

    /**
     * Set document orientation.
     *
     * @param arg "portrait" or "landscape"
     */
    void setOrientation(String arg) {
        arg = arg.toLowerCase()
        if (arg != PORTRAIT && arg != LANDSCAPE) {
            throw new IllegalArgumentException("invalid orientation: $arg, only '$PORTRAIT' or '$LANDSCAPE' allowed")
        }
        if (this.@orientation != arg) {
            this.@orientation = arg
            def tmp = width
            width = height
            height = tmp
        }
    }

    boolean isLandscape() {
        this.orientation == LANDSCAPE
    }

    @Override
    Document getDocument() {
        this
    }

    @Override
    Margin getDefaultMargin() {
        DEFAULT_MARGIN
    }

    @Override
    void setNodeFont(List<Map> nodeProperties) {
        font = new Font()
        nodeProperties.each {
            font << it.font
        }
    }
}
