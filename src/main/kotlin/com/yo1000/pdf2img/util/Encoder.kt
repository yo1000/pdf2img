package com.yo1000.pdf2img.util

import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex
import org.springframework.stereotype.Component

/**
 *
 * @author yo1000
 */
@Component
class Encoder {
    fun encode(bytes: ByteArray, base64: Boolean): String {
        if (base64) {
            return encodeToBase64(bytes)
        } else {
            return encodeToHex(bytes)
        }
    }

    protected fun encodeToHex(bytes: ByteArray): String {
        return Hex.encodeHexString(bytes)
    }

    protected fun encodeToBase64(bytes: ByteArray): String {
        return Base64.encodeBase64String(bytes)
    }
}