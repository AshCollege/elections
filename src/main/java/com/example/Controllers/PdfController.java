package com.example.Controllers;

import com.example.Objects.EmailObject;
import com.example.Utils.PdfUtils;
import com.example.Utils.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sigal on 1/26/2017.
 */
@Controller
public class PdfController {

    @Autowired
    private PdfUtils pdfUtils;

    @RequestMapping(value = "/email-invoice")
    @ResponseBody
    public String emailInvoicePdf() throws Exception {
        //Add parameters
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "World");

        //Create email object
        EmailObject emailObject = new EmailObject();
        emailObject.setFrom("Shai");    //change
        emailObject.getTo().add("shaigivati464@gmail.com");//change
        emailObject.setSubject("PDF Report");
        emailObject.setText("Test sending PDF file");

        pdfUtils.sendPdf(map, emailObject);
        return "Pdf has been successfully sent";
    }


    @RequestMapping("/invoice.pdf")
    public void invoicePdf(HttpServletResponse response) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("total", "500");
        params.add("name", "Ben");
        params.add("month","January 2017");
        pdfUtils.buildRequest("invoice", "/invoice-pdf.vm", params, response);
    }



    @RequestMapping("/invoice-pdf.vm")
    public String invoicePdfVm(HttpServletRequest request, Model model) throws Exception {
        for (String param : request.getParameterMap().keySet()) {
            model.addAttribute(param, request.getParameter(param));
        }
        return "tmpl_invoice_pdf";
    }

}

