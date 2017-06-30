package com.example.Controllers;

import com.example.Objects.Entities.MemadClientObject;
import com.example.Services.GeneralManager;
import com.example.Utils.TemplateUtils;
import com.example.Utils.Utils;
import com.sun.prism.shader.DrawCircle_Color_AlphaTest_Loader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.Objects.Entities.ConsumerObject;
import com.example.Objects.Entities.InvoiceObject;
import com.example.Services.GeneralManager;
import com.example.Utils.TemplateUtils;
import com.example.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.util.StringUtils.hasText;

/**
 * Created by Sigal on 2/16/2017.
 */


@Controller
public class ManagerDashboardController {


    @Autowired
    private Utils utils;

    @Autowired
    private GeneralManager generalManager;

    @Autowired
    private TemplateUtils templateUtils;


    @RequestMapping("/manager-dashboard")
    public String managerDashboard(@CookieValue("consumerUid") Integer consumerUid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        boolean error = false;
        int currentYear = 2017;
        Date today = new Date();
        templateUtils.formatDateExcludeTime(today);

        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "manager-dashboard");

            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
            Integer clientId = consumer.getMemadClientObject().getOid();

            List<ConsumerObject> consumers = generalManager.getAllConsumersByClientId(clientId);


            List<InvoiceObject> allInvoices = generalManager.getAllClientinvoicesByClientId(clientId);

            List<InvoiceObject> paidInvoices = generalManager.getAllPaidClientInvoices(clientId);

            List<InvoiceObject> unPaidInvoices = generalManager.getAllUnpaidClientInvoices(clientId);

            Integer allDebts = 0, notPaidDebts = 0;

            for (InvoiceObject invoice : allInvoices) {

                allDebts = invoice.getTotal() + allDebts;

                if (!invoice.getPaid()) {
                    notPaidDebts = invoice.getTotal() + notPaidDebts;
                }

            }

            model.addAttribute("invoices", allInvoices);
            model.addAttribute("paidInvoices", paidInvoices);
            model.addAttribute("unPaidInvoices", unPaidInvoices);
            model.addAttribute("invoicesNum", allInvoices.size());
            model.addAttribute("paidInvoicesNum", paidInvoices.size());
            model.addAttribute("unPaidInvoicesNum", unPaidInvoices.size());
            model.addAttribute("allDebts", allDebts);
            model.addAttribute("notPaidDebts", notPaidDebts);
            model.addAttribute("consumer", consumer);
            model.addAttribute("consumers", consumers);
            model.addAttribute("currentYear", currentYear);
            model.addAttribute("today", today);

//            } else {
//                error = true;
//            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_manager_dashboard";
    }


    @RequestMapping("/consumpsion-report")
    public String consumptionReport(@CookieValue("consumerUid") Integer consumerUid, HttpServletRequest request, HttpServletResponse response, Model model, String year) throws Exception {
        boolean error = false;
        int currentYear = 2017;
        Integer chosenYear = Integer.valueOf(year);

        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "manager-dashboard");

            if (chosenYear == null) {
                chosenYear = currentYear;
            }

            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
            Integer clientId = consumer.getMemadClientObject().getOid();

            MemadClientObject client = generalManager.loadClient(clientId);

            List<ConsumerObject> consumers = generalManager.getAllConsumersByClientId(clientId);

            List<InvoiceObject> allInvoices = generalManager.getAllClientinvoicesByClientId(clientId);

            List<InvoiceObject> invoicesByYear = generalManager.getAllClientinvoicesByYear(clientId, chosenYear);

            Integer[] monthlyConsumption = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            for (int i = 0; i < 12; i++) {
                for (InvoiceObject invoice : invoicesByYear) {

                    if (invoice.getMonth() == i + 1) {
                        monthlyConsumption[i] = invoice.getConsumption() + monthlyConsumption[i];
                    }

                }
            }

            //         List<InvoiceObject> paidInvoices = generalManager.getAllPaidClientInvoices(clientId);

            //        List<InvoiceObject> unPaidInvoices = generalManager.getAllUnpaidClientInvoices(clientId);

            model.addAttribute("allInvoices", allInvoices);
            model.addAttribute("invoices", invoicesByYear);
            model.addAttribute("monthlyConsumption", monthlyConsumption);


            //         model.addAttribute("paidInvoices", paidInvoices);
            //         model.addAttribute("unPaidInvoices", unPaidInvoices);
            //        model.addAttribute("invoicesNum", invoices.size());
            //        model.addAttribute("paidInvoicesNum", paidInvoices.size());
            //        model.addAttribute("unPaidInvoicesNum", unPaidInvoices.size());
            model.addAttribute("currentYear", currentYear);
            model.addAttribute("consumer", consumer);
            model.addAttribute("consumers", consumers);
            model.addAttribute("client", client);
            model.addAttribute("chosenYear", year);
//            } else {
//                error = true;
//            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_consumption";
    }

    @RequestMapping("/search-invoices.json")
    public void searchInvoices(@CookieValue("consumerUid") Integer consumerUid, HttpServletRequest request, HttpServletResponse response, Model model, String lowInvoiceNum, String highInvoiceNum, String lowConsumerUid, String highConsumerUid,String lowTotal, String highTotal, String lowDate, String highDate,  String lowRun, String highRun, String month, String year, String payStatus, String paymentConfirmation) throws Exception {
        boolean error = false;
     //   List<InvoiceObject> invoices = null;
        List<Integer> invoicesOidList = null;
     //   List<InvoiceObject> allInvoices = null;
        Boolean payStatusSearch = null;


        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "manager-dashboard");

            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
            //Integer clientId = consumer.getMemadClientObject().getOid();


            Integer searchInvoice = (lowInvoiceNum.length() == 0 ? null : Integer.valueOf(lowInvoiceNum));
            Integer searchHighInvoice = (highInvoiceNum.length() == 0 ? null : Integer.valueOf(highInvoiceNum));
            Integer searchlowConsumer = (lowConsumerUid.length() == 0 ? null : Integer.valueOf(lowConsumerUid));
            Integer searchHighConsumer = (highConsumerUid.length() == 0 ? null : Integer.valueOf(highConsumerUid));

            Integer searchLowTotal = (lowTotal.length() == 0 ? null : Integer.valueOf(lowTotal));
            Integer searchHighTotal = (highTotal.length() == 0 ? null : Integer.valueOf(highTotal));

            Date searchlowDate = (lowDate.length() == 0 ? null : templateUtils.convertStringToDate(lowDate));
            Date searchHighDate = (highDate.length() == 0 ? null : templateUtils.convertStringToDate(highDate));
            int searchlowRun = (lowRun.length() == 0 ? 0 : Integer.valueOf(lowRun));
            int searchHighRun = (highRun.length() == 0 ? 0 : Integer.valueOf(highRun));

            int searchMonth = (month.length() == 0 ? 0 : Integer.valueOf(month));
            int searchYear = (year.length() == 0 ? 0 : Integer.valueOf(year));

            switch (payStatus) {
                case "all":
                    payStatusSearch = null;
                    break;
                case "paid":
                    payStatusSearch = true;
                    break;
                case "unpaid":
                    payStatusSearch = false;
                    break;


            }
            // Integer searchPayStatus = Integer.valueOf(payStatus);

            Integer searchPaymentConfirmation = (paymentConfirmation.length() == 0 ? null : Integer.valueOf(paymentConfirmation));
            //           Date date = new Date();
            //    date = null;
            invoicesOidList = generalManager.searchInvoicesByParameters(searchInvoice,searchHighInvoice, searchlowConsumer,searchHighConsumer, searchLowTotal, searchHighTotal, searchlowDate,searchHighDate,searchlowRun,searchHighRun, searchMonth, searchYear, payStatusSearch, searchPaymentConfirmation);

          //  allInvoices = generalManager.getAllClientinvoicesByClientId(1);


        } catch (Exception e) {
            error = true;
        }

        response.setContentType("application/json; charset=UTF-8");
        JSONObject JObject = new JSONObject();
        JObject.put("error", error);
       // JObject.put("allInvoices", allInvoices);
        JObject.put("invoicesOidList", invoicesOidList);
        response.getWriter().print(JObject);
    }


//    @RequestMapping("/choose-year.json")
//    public void changeSupervisor(@CookieValue("consumerUid") Integer consumerUid, HttpServletRequest request, HttpServletResponse response, Model model, String year) throws Exception {
//        boolean error = false;
//        Integer chosenYear = Integer.valueOf(year);
//        try {
//            utils.setDefaultParameters(request, response, model);
//            model.addAttribute("pageName", "manager-dashboard");
//
//            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
//            Integer clientId = consumer.getMemadClientObject().getOid();
//
//        } catch (Exception e) {
//            error = true;
//        }
//        response.setContentType("application/json; charset=UTF-8");
//        JSONObject JObject = new JSONObject();
//        JObject.put("error", error);
//        JObject.put("chosenYear", chosenYear);
//        response.getWriter().print(JObject);
//
//    }


}