package com.yo1000.pdf2img.service

import com.yo1000.pdf2img.util.Encoder
import com.yo1000.pdf2img.util.PdfConverter
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException

/**
 *
 * @author yo1000
 */
@Service
class ConvertPdfService(val encoder: Encoder, val pdfConverter: PdfConverter) {
    fun convertToBinaries(base64: Boolean, vararg inputFiles: String): List<String> {
        return convertToBinaries(base64, *inputFiles.map(::File).toTypedArray())
    }

    fun convertToBinaries(base64: Boolean, vararg inputFiles: File): List<String> {
        return inputFiles.map {
            if (!it.exists() || !it.isFile) {
                throw FileNotFoundException("File not found. (${it.absolutePath})")
            }

            encoder.encode(pdfConverter.convertToBinary(it), base64)
        }
    }

    fun convertToFiles(format: String, outputDir: String, vararg inputFiles: String) {
        convertToFiles(format, File(outputDir), *inputFiles.map(::File).toTypedArray())
    }

    fun convertToFiles(format: String, outputDir: File, vararg inputFiles: File) {
        if (!outputDir.exists() || !outputDir.isDirectory) {
            throw FileNotFoundException("Directory not found. (${outputDir.absolutePath})")
        }

        inputFiles.forEach {
            if (!it.exists() || !it.isFile) {
                throw FileNotFoundException("File not found. (${it.absolutePath})")
            }

            pdfConverter.convertToFile(format, outputDir, it)
        }
    }
}
