package com.yo1000.pdf2img.util

import org.apache.pdfbox.io.RandomAccessFile
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.rendering.PDFRenderer
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import javax.imageio.ImageIO

/**
 *
 * @author yo1000
 */
@Component
open class PdfConverter {
    fun convertToBinary(input: File): ByteArray {
        return ByteArrayOutputStream().use {
            ImageIO.write(convertAndMerge(input), "png", it)
            it.toByteArray()
        }
    }

    fun convertToFile(format: String, outputDir: File, inputFile: File) {
        return ByteArrayOutputStream().use {
            ImageIO.write(convertAndMerge(inputFile), format,
                    outputDir.resolve("${inputFile.nameWithoutExtension}.$format"))
        }
    }

    protected fun convertAndMerge(file: File): BufferedImage {
        val images = convert(file)
        val mergedImage = BufferedImage(
                images.maxBy { it.width }!!.width,
                images.sumBy { it.height },
                images.first().type)

        val graphics = mergedImage.createGraphics()
        var y = 0
        images.forEach {
            graphics.drawImage(it, null, 0, y)
            y = it.height
        }

        return mergedImage
    }

    protected fun convert(file: File): List<BufferedImage> {
        return RandomAccessFile(file, "r").use<RandomAccessFile, List<BufferedImage>> {
            val parser = PDFParser(it)
            parser.parse()
            val doc = parser.pdDocument

            val renderer = PDFRenderer(doc)
            val pageImages = ArrayList<BufferedImage>(doc.pages.count)

            doc.pages.forEachIndexed { i, page ->
                pageImages.add(renderer.renderImage(i))
            }

            return pageImages
        }
    }
}
