package com.craigburke.document.core.builder

import com.craigburke.document.core.dom.BaseNode
import com.craigburke.document.core.dom.attribute.BackgroundAssignable
import com.craigburke.document.core.dom.attribute.EmbeddedFont
import com.craigburke.document.core.dom.attribute.Font
import com.craigburke.document.core.dom.attribute.Stylable
import com.craigburke.document.core.dom.block.BlockNode
import com.craigburke.document.core.dom.block.Document
import com.craigburke.document.core.dom.block.Paragraph
import com.craigburke.document.core.dom.block.table.Cell
import com.craigburke.document.core.dom.text.Heading

import com.craigburke.document.core.factory.CellFactory
import com.craigburke.document.core.factory.CreateFactory
import com.craigburke.document.core.factory.DocumentFactory
import com.craigburke.document.core.factory.HeadingFactory
import com.craigburke.document.core.factory.ImageFactory
import com.craigburke.document.core.factory.LineBreakFactory
import com.craigburke.document.core.factory.LinkFactory
import com.craigburke.document.core.factory.PageBreakFactory
import com.craigburke.document.core.factory.ParagraphFactory
import com.craigburke.document.core.factory.RowFactory
import com.craigburke.document.core.factory.TableFactory
import com.craigburke.document.core.factory.TextFactory

import com.craigburke.document.core.unit.UnitCategory

import ox.softeng.document.core.dsl.CreateApi

/**
 * Document Builder base class
 * @author Craig Burke
 */
abstract class DocumentBuilder extends FactoryBuilderSupport {

    Document document
    OutputStream out
    RenderState renderState = RenderState.PAGE
    List<String> imageFileNames = []

    Closure addPageBreakToDocument
    Closure onTextBlockComplete
    Closure onTableComplete
    Closure addEmbeddedFont

    DocumentBuilder(OutputStream out) {
        super(true)
        this.out = out
    }

    DocumentBuilder(File file) {
        super(true)
        this.out = new FileOutputStream(file)
    }

    abstract void initializeDocument(Document document, OutputStream out)

    abstract void writeDocument(Document document, OutputStream out)

    DocumentBuilder create(@DelegatesTo(CreateApi) Closure closure) {
        use(UnitCategory) {
            CreateApi createApi = new CreateApi(this)
            closure.delegate = createApi
            closure.call()
        }
        this
    }

    def invokeMethod(String name, args) {
        use(UnitCategory) {
            super.invokeMethod(name, args)
        }
    }

    @Deprecated
    Font getFont() {
        current.font
    }

    @Deprecated
    public <T extends BaseNode> T setNodeProperties(T node, Map attributes, String nodeKey) {
        String[] templateKeys = getTemplateKeys(node, nodeKey)
        def nodeProperties = []

        templateKeys.each { String key ->
            if (document.template && document.templateMap.containsKey(key)) {
                nodeProperties << document.templateMap[key]
            }
        }
        nodeProperties << attributes

        node.name = nodeKey
        if (node instanceof Stylable) {
            setNodeFont(node, nodeProperties)
        }
        if (node instanceof BlockNode) {
            setBlockProperties(node, nodeProperties)
        }
        if (node instanceof BackgroundAssignable) {
            setNodeBackground(node, nodeProperties)
        }
        node
    }

    @Deprecated
    protected void setNodeFont(Stylable node, nodeProperties) {
        node.font = (node instanceof Document) ? new Font() : node.parent.font.clone()
        node.font.size = (node instanceof Heading) ? null : node.font.size
        nodeProperties.each {
            node.font << it.font
        }
        if (node instanceof Heading && !node.font.size) {
            node.font.size = document.font.size * Heading.FONT_SIZE_MULTIPLIERS[node.level - 1]
        }
    }

    @Deprecated
    protected void setBlockProperties(BlockNode node, nodeProperties) {
        node.margin = node.defaultMargin.clone()
        nodeProperties.each {
            node.margin << it.margin
            if (it.border) {
                node.border << it.border
            }
        }
    }

    @Deprecated
    protected void setNodeBackground(BackgroundAssignable node, nodeProperties) {
        nodeProperties.each { Map properties ->
            if (properties.containsKey('background')) {
                node.background = properties.background
            }
            boolean canCascade = (!(node.parent instanceof Paragraph) && (node.parent instanceof BackgroundAssignable))
            if (canCascade && !node.background && node.parent.background) {
                node.background = "#${node.parent.background.hex}"
            }
        }
    }

    @Deprecated
    static String[] getTemplateKeys(BaseNode node, String nodeKey) {
        def keys = [nodeKey]
        if (node instanceof Heading) {
            keys << "heading${node.level}"
        }
        if (node instanceof Stylable && node.style) {
            keys << "${nodeKey}.${node.style}"
            if (node instanceof Heading) {
                keys << "heading${node.level}.${node.style}"
            }
        }
        keys
    }

    @Deprecated
    Paragraph getColumnParagraph(Cell column) {
        if (column.children && column.children[0] instanceof Paragraph) {
            column.children[0]
        } else {
            Paragraph paragraph = new Paragraph(font: column.font.clone(), parent: column, align: column.align)
            setNodeProperties(paragraph, [margin: [top: 0, left: 0, bottom: 0, right: 0]], 'paragraph')
            column.children << paragraph
            paragraph
        }
    }

    @Deprecated
    void addFont(Map params, String location) {
        EmbeddedFont embeddedFont = new EmbeddedFont(params)
        embeddedFont.file = new File(location)
        addFont(embeddedFont)
    }

    @Deprecated
    void addFont(EmbeddedFont embeddedFont) {
        document.embeddedFonts << embeddedFont
        if (addEmbeddedFont) {
            addEmbeddedFont(embeddedFont)
        }
    }

    @Deprecated
    def registerObjectFactories() {
        registerFactory('create', new CreateFactory())
        registerFactory('document', new DocumentFactory())
        registerFactory('pageBreak', new PageBreakFactory())
        registerFactory('paragraph', new ParagraphFactory())
        registerFactory('lineBreak', new LineBreakFactory())
        registerFactory('image', new ImageFactory())
        registerFactory('text', new TextFactory())
        registerFactory('link', new LinkFactory())
        registerFactory('table', new TableFactory())
        registerFactory('row', new RowFactory())
        registerFactory('cell', new CellFactory())
        registerFactory('heading1', new HeadingFactory())
        registerFactory('heading2', new HeadingFactory())
        registerFactory('heading3', new HeadingFactory())
        registerFactory('heading4', new HeadingFactory())
        registerFactory('heading5', new HeadingFactory())
        registerFactory('heading6', new HeadingFactory())
    }
}

