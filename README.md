# PDF2IMG

Convert to image from PDF.

## Usage

```
usage:
pdf2img [-b64 | -o <outputDirectory> [-f <format>]] <inputFiles>

 -?,--help           Print this message.
 -b64,--base64       Base64 format output.
 -f,--format <arg>   Output file format. Select from "png" or "jpg".
 -o,--out <arg>      Output directory.
```

### Run from Java using jar

Convert files.

```
$ ls /dir/path
A.pdf   B.pdf

$ java -jar pdf2img.jar -o /dir/path /dir/path/A.pdf /dir/path/B.pdf

$ ls /dir/path
A.pdf   A.png   B.pdf   B.png
```

### Run from Maven using sources

Compare files.

```
$ ls /dir/path
A.pdf   B.pdf

$ ./mvnw clean spring-boot:run -U -Drun.arguments=-o,/dir/path,/dir/path/A.pdf,/dir/path/B.pdf

$ ls /dir/path
A.pdf   A.png   B.pdf   B.png
```

## Dependencies

### Apache Commons CLI

- http://commons.apache.org/proper/commons-cli/
- https://mvnrepository.com/artifact/commons-cli/commons-cli

### Apache Commons Codec

- https://commons.apache.org/proper/commons-codec/
- https://mvnrepository.com/artifact/commons-codec/commons-codec

### Apache PDFBox

- https://pdfbox.apache.org/
- https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
