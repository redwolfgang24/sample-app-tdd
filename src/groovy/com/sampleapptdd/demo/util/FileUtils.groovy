package com.sampleapptdd.demo.util

import com.itextpdf.text.Document
import org.apache.commons.io.IOUtils
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.tool.xml.XMLWorkerHelper
import org.apache.commons.codec.binary.Base64

class FileUtils {

    public static byte[] getConvertedHtmlToPdfBytes(String htmlTemplate) {
        OutputStream outputStream = new ByteArrayOutputStream()
        Document document = new Document()
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream)
        document.open()
        InputStream is = new ByteArrayInputStream(htmlTemplate.getBytes())
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, is)
        document.close()
        outputStream.close()

        return outputStream.toByteArray()
    }

    public static byte[] convertBase64ToByte(String stringBase64) {
        return new Base64().decodeBase64(stringBase64)
    }

    public static byte[] convertFileUrlToByte(String fileUrl) {
        return IOUtils.toByteArray(new URL(fileUrl))
    }
}
