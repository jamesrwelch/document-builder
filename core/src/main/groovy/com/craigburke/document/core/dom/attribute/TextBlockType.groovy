package com.craigburke.document.core.dom.attribute

/**
 * @since 11/06/2018
 */
interface TextBlockType extends ParentAware {

    Margin getDefaultMargin()

    Margin getMargin()

    void setMargin(Margin margin)

    String getRef()

    void setRef(String ref)

    Color getBackground()

    void setBackground(Color color)

    void setBackground(String value)

    void setNodeBackground(List<Map> nodeProperties)

    BigDecimal getLineSpacing()

    BigDecimal getLineSpacingMultiplier()

    Align getAlign()

    void setAlign(Align align)

    void setAlign(String value)

}
