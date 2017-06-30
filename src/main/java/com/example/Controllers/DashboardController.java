package com.example.Controllers;

import com.example.Objects.Entities.ConsumerObject;
import com.example.Objects.Entities.InvoiceObject;
import com.example.Services.GeneralManager;
import com.example.Utils.TemplateUtils;
import com.example.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.util.StringUtils.hasText;

/**
 * Created by Sigal on 5/16/2016.
 */

@Controller
public class DashboardController {


    @Autowired
    private Utils utils;

    @Autowired
    private GeneralManager generalManager;

    @Autowired
    private TemplateUtils templateUtils;

    @RequestMapping("/dashboard")
    public String dashboard(@CookieValue("consumerUid") Integer consumerUid, HttpServletRequest request, HttpServletResponse response, Model model, String consumerId) throws Exception {
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "dashboard");

            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);

            List<InvoiceObject> invoices = generalManager.getAllConsumersnvoices(Integer.valueOf(consumerUid));
            model.addAttribute("invoices", invoices);
            model.addAttribute("consumer", consumer);
            int sumAllInvoices = 0;
            int sumUnpaidInvoices = 0;
            for (InvoiceObject invoiceObject : invoices) {
                sumAllInvoices += invoiceObject.getTotal();
                if (!invoiceObject.getPaid()) {
                    sumUnpaidInvoices += invoiceObject.getTotal();
                }
            }
            model.addAttribute("unpaidSum",sumUnpaidInvoices);
            model.addAttribute("averageInvoice",(sumAllInvoices/invoices.size()));
//            } else {
//                error = true;
//            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_dashboard";
    }

    @RequestMapping({"/dashboard/current-invoice"})
    public String currentInvoice(@CookieValue("consumerUid") Integer consumerUid,HttpServletRequest request, HttpServletResponse response, Model model, String consumerId,String invoiceOid) throws Exception {
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);

            model.addAttribute("pageName", templateUtils.getTranslation("Dashboard"));

            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
            InvoiceObject  currenrtInvoice = generalManager.loadInvoice(Integer.valueOf(invoiceOid));
            List<InvoiceObject> invoices = generalManager.getAllConsumersnvoices(Integer.valueOf(consumerUid));

            model.addAttribute("invoice", currenrtInvoice);
            model.addAttribute("invoices", invoices);
            model.addAttribute("consumer", consumer);


        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_invoice";
    }

    @RequestMapping("/client-details")
    public String clientDetails(@CookieValue("consumerUid") Integer consumerUid,HttpServletRequest request, HttpServletResponse response, Model model, String consumerId) throws Exception {
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "client.details");

            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);


            model.addAttribute("consumer", consumer);
            model.addAttribute("client",consumer.getMemadClientObject());
//            } else {
//                error = true;
//            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_client_details";
    }


    @RequestMapping("/invoices-history")
    public String invoicesHistory(@CookieValue("consumerUid") Integer consumerUid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", templateUtils.getTranslation("Dashboard"));
            model.addAttribute("pageName", "invoices.history");

          //  ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
            List<InvoiceObject> consumerInvoices = generalManager.getAllConsumersnvoices(consumerUid);

            model.addAttribute("invoices", consumerInvoices);
//            } else {
//                error = true;
//            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }

        return "tmpl_invoices_history";
    }


    @RequestMapping("/private-details")
    public String consumerDetails(@CookieValue("consumerUid") Integer consumerUid,HttpServletRequest request, HttpServletResponse response, Model model, String consumerId) throws Exception {
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "private.details");

            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);


            model.addAttribute("consumer", consumer);
            model.addAttribute("client",consumer.getMemadClientObject());
//            } else {
//                error = true;
//            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_consumer_details";
    }

    @RequestMapping("/temporary")
    public String temporary(@CookieValue("consumerUid") Integer consumerUid,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "temporary");

           // ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
           // Integer clientId = consumer.getMemadClientObject().getOid();

          //  List<ConsumerObject> consumers = generalManager.getAllConsumersByClientId(clientId);


         //   List<InvoiceObject> invoices = generalManager.getAllClientinvoicesByClientId(clientId);

        //    model.addAttribute("invoices", invoices);
        //    model.addAttribute("consumer", consumer);
        //    model.addAttribute("consumers", consumers);

//            } else {
//                error = true;
//            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_temporary";
    }

}