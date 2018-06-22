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
abstract class Document extends BlockNode<Document, BaseNode> {

    static final Margin DEFAULT_MARGIN = new Margin(top: 72, bottom: 72, left: 72, right: 72)

    private static final String PORTRAIT = 'portrait'
    private static final String LANDSCAPE = 'landscape'

    int pageCount
    BigDecimal width = inchToPoint(PaperSize.LETTER.width)
    BigDecimal height = inchToPoint(PaperSize.LETTER.height)
    String orientation = PORTRAIT

    private Closure template
    private Closure header
    private Closure footer

    Map templateMap

    private List<EmbeddedFont> embeddedFonts = []

    Map<String, Map> getTemplateMap() {
        if (templateMap == null) {
            templateMap = loadTemplateMap()
        }
        templateMap
    }

    List<EmbeddedFont> getEmbeddedFonts() {
        return embeddedFonts
    }

    @Deprecated
    protected Map loadTemplateMap() {
        Map loadedTemplateMap = [:]
        if (template) {
            if (template instanceof Closure) {
                Expando templateDelegate = new Expando()
                templateDelegate.metaClass.methodMissing = {name, args ->
                    setProperty(name, args)
                }
                template.resolveStrategy = Closure.DELEGATE_FIRST
                template.delegate = templateDelegate
                template.call()
                loadedTemplateMap = templateDelegate.getProperties()
            } else if (template instanceof Map) {
                loadedTemplateMap = template
            }
        }
        loadedTemplateMap
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

    Document addToEmbeddedFonts(EmbeddedFont embeddedFont) {
        embeddedFonts << embeddedFont
        this
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

    @Deprecated
    Closure getTemplate() {
        return template
    }

    @Deprecated
    void setTemplate(Closure template) {
        this.template = template
    }

    @Deprecated
    Closure getHeader() {
        return header
    }

    @Deprecated
    void setHeader(Closure header) {
        this.header = header
    }

    @Deprecated
    Closure getFooter() {
        return footer
    }

    @Deprecated
    void setFooter(Closure footer) {
        this.footer = footer
    }
}
