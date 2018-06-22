package ox.softeng.document.core.dsl

/**
 * @since 21/06/2018
 */
class TemplateApi {

    Map templateMap = [:]

    TemplateApi document(Map attributes) {
        methodMissing('document', attributes)
    }

    TemplateApi paragraph(Map attributes) {
        methodMissing('paragraph', attributes)
    }

    TemplateApi table(Map attributes) {
        methodMissing('table', attributes)
    }

    TemplateApi row(Map attributes) {
        methodMissing('row', attributes)
    }

    TemplateApi cell(Map attributes) {
        methodMissing('cell', attributes)
    }

    TemplateApi header(Map attributes) {
        methodMissing('header', attributes)
    }

    TemplateApi header1(Map attributes) {
        methodMissing('header1', attributes)
    }

    TemplateApi header2(Map attributes) {
        methodMissing('header2', attributes)
    }

    TemplateApi header3(Map attributes) {
        methodMissing('header3', attributes)
    }

    TemplateApi header4(Map attributes) {
        methodMissing('header4', attributes)
    }

    TemplateApi header5(Map attributes) {
        methodMissing('header5', attributes)
    }

    TemplateApi header6(Map attributes) {
        methodMissing('header6', attributes)
    }

    TemplateApi text(Map attributes) {
        methodMissing('text', attributes)
    }

    TemplateApi image(Map attributes) {
        methodMissing('image', attributes)
    }

    TemplateApi methodMissing(String name, def args) {
        templateMap[name] = args
        this
    }
}
