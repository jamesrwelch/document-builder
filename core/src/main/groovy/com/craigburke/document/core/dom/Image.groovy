package com.craigburke.document.core.dom

import com.craigburke.document.core.dom.attribute.ImageType

import java.security.MessageDigest

/**
 * Image node
 * @author Craig Burke
 */
class Image extends BaseNode {
    String hashName
    ImageType type = ImageType.JPG
    BigDecimal width
    BigDecimal height
    String url
    byte[] data

    void setType(String value) {
        type = Enum.valueOf(ImageType, value.toUpperCase())
    }

    byte[] getData() {
        if(this.@data == null && url != null) {
            this.data = new URL(url).bytes
        }
        this.@data
    }

    def withInputStream(Closure work) {
        work.call(new ByteArrayInputStream(getData()))
    }

    String getHash() {
        Formatter hexHash = new Formatter()
        MessageDigest.getInstance('SHA-1').digest(getData()).each {
            b -> hexHash.format('%02x', b)
        }
        hexHash.toString()
    }
}
