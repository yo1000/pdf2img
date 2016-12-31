package com.yo1000.pdf2img.cli

import com.yo1000.pdf2img.service.ConvertPdfService
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 *
 * @author yo1000
 */
@Component
class Pdf2ImgCommandLineRunner(val convertPdfService: ConvertPdfService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val options = Options()
        options.addOption("o", "out", true, "Output directory.")
        options.addOption("f", "format", true, "Output file format. Select from \"png\" or \"jpg\".")
        options.addOption("b64", "base64", false, "Base64 format output.")
        options.addOption("?", "help", false, "Print this message.")

        val parser = DefaultParser()
        val cl = parser.parse(options, args)

        if (cl.hasOption("?")) {
            val formatter = HelpFormatter()
            formatter.printHelp("""
pdf2img [-b64 | -o <outputDirectory> [-f <format>]] <inputFiles>

""",
                    options)

            return
        }

        if (cl.args.isEmpty()) {
            throw IllegalArgumentException("<inputFiles> is required.")
        }

        if (cl.hasOption("b64") && cl.hasOption("o")) {
            throw IllegalArgumentException("\"b64\" option can not use with \"o\" option.")
        }

        if (!cl.hasOption("o") && cl.hasOption("f")) {
            throw IllegalArgumentException("\"f\" option must be used with \"o\" option.")
        }

        if (cl.hasOption("o")) {
            convertPdfService.convertToFiles(
                    if (cl.hasOption("f"))
                        cl.getOptionValue("f")
                    else
                        "png",
                    cl.getOptionValue("o"),
                    *cl.args)

            return
        } else {
            println(convertPdfService.convertToBinaries(cl.hasOption("b64"), *cl.args)
                    .joinToString(" "))
        }
    }
}