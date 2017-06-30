package com.example.Utils;

import com.example.Objects.EmailObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Sigal on 1/27/2017.
 */


public class PdfUtils {

    private static final String PDF_INVOICE_TEMPLATE = "http://127.0.0.1:8080/invoice-pdf.vm";

    /**
     *  Directory for saving PDF files
     */
    private static final String PDF_DIRECTORY = "C:/dumps/";

    @Autowired
    private EmailUtils emailUtils;

    /**
     * Converts HTML content to PDF and directs to the outputstream
     */
    public void convertHtml(OutputStream os, String html) {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)));
            document.close();
        }
        catch (Exception e) {
            System.out.println("Error while convert html to pdf");
            e.printStackTrace();
        }
    }

    /**
     * Converts HTML content to PDF and saves to the file
     */
    public void convertHtml(File file, String html) {
        try {
            convertHtml(new FileOutputStream(file), html);
        } catch (FileNotFoundException e) {
            System.out.println("Error while convert html to pdf file");
            e.printStackTrace();
        }
    }

    /**
     * Generates HTML from template
     */
    public String generateHtml(MultiValueMap<String, String> map) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> html = restTemplate.postForEntity(
                PDF_INVOICE_TEMPLATE,
                prepareRequest(map),
                String.class );
        return html.getBody();
    }

    /**
     * Generates HTML -> Converts PDF -> Sends by email
     */
    public void sendPdf(MultiValueMap<String, String> map, EmailObject mail) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM_HH-mm");
        String date = simpleDateFormat.format(new Date());
        File pdfFile = new File(PDF_DIRECTORY + "report_" + date + ".pdf");
        String html = generateHtml(map);
        convertHtml(pdfFile, html);
        mail.getFilesPaths().add(pdfFile.getAbsolutePath());
        emailUtils.sendEmail(mail);
    }

    /**
     * Prepares HTTP request to template
     */
    private HttpEntity<MultiValueMap<String, String>> prepareRequest(MultiValueMap<String, String> map) {
        HttpHeaders headers;
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(map, headers);
    }

    public void buildRequest(String fileName, String url, MultiValueMap<String, String>  params, HttpServletResponse response) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> html = new RestTemplate().postForEntity( "http://127.0.0.1:8080" + url, request, String.class );
        response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(
                writer,
                document,
                new ByteArrayInputStream(html.getBody().getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
        document.close();

    }



}
