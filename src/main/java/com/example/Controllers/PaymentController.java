package com.example.Controllers;

import com.example.Objects.Entities.InvoiceObject;
import com.example.Objects.General.PaymentResponse;
import com.example.Objects.General.RequestParams;
import com.example.Services.GeneralManager;
import com.example.Utils.TemplateUtils;
import com.example.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.annotation.ModelFactory;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for payments.
 * Send request to the payment system, and after client payment
 * process response.
 */
@Controller
public class PaymentController {
    /**
     * This controller method builds POST request to Pelecard payment gateway
     * with terminal authorization params and other payment params from {@link RequestParams},
     * Tincluding callback for success and error response(goodUrl and errorUrl params).
     *
     * @return Response from Pelecard for further payment
     */

    @Autowired
    private Utils utils;


    @Autowired
    private TemplateUtils templateUtils;

    @Autowired
    private GeneralManager generalManager;

    private  InvoiceObject invoiceObject;

    @RequestMapping("/payment-request")
    public String paymentRequest(Model model,String total,String invoiceId) {

         invoiceObject = generalManager.loadInvoice(Integer.valueOf(invoiceId));

        Map<String, String> params = new HashMap<>();
        //Terminal auth params
        params.put(RequestParams.USER_NAME, Utils.getConfigValueByKey("pelecard_username"));
        params.put(RequestParams.PASSWORD, Utils.getConfigValueByKey("pelecard_password"));
//        params.put(RequestParams.TERMINAL, Utils.getConfigValueByKey(invoiceObject.getClientObject().getTerminal()));
        params.put(RequestParams.TERMINAL, Utils.getConfigValueByKey("terminal"));
        //Required params
        params.put(RequestParams.ID, invoiceId);
        params.put(RequestParams.TOTAL, total);
        params.put(RequestParams.CURRENCY, "1");
        params.put(RequestParams.MAX_PAYMENTS, "1");
        params.put(RequestParams.MIN_PAYMENTS, "1");
        params.put(RequestParams.CREDIT_TYPE_FROM, "7");
        params.put(RequestParams.GOOD_URL, Utils.getConfigValueByKey("site_url") + "/payment-success");
        params.put(RequestParams.ERROR_URL, Utils.getConfigValueByKey("site_url") + "/payment-failure");

        //Optional params(for example head text)
        params.put(RequestParams.HEAD_TEXT, "Invoices");
        params.put(RequestParams.PARMX, invoiceId);
        model.addAllAttributes(params);
        return "payment";
    }


    /**
     * This method contain business logic for process payment response.
     *
     * @param response Response object.
     * @return Response string representation.
     */
    @RequestMapping(value = "/payment-success", produces = "text/plain")
    public String paymentSuccess(@ModelAttribute PaymentResponse response, HttpServletResponse resp, HttpServletRequest request, Model model) {
        String result =  "result: " + response.getResult() + "\r\n"
                + "authNum: " + response.getAuthNum() + "\r\n"
                + "parmx:" + response.getParmx() + "\r\n"
                + "token:" + response.getToken() + "\r\n"
                + " id:" + response.getId() + "\r\n"
                + "creditCardHolder:" + response.getCreditCardHolder() + "\r\n"
                + "invoiceLink:" + response.getInvoiceLink();

        Date date = new Date();

        invoiceObject.setPaid(true);
        invoiceObject.setPaymentDate(date);
       // invoiceObject.setPaidAmount();
        invoiceObject.setTransactionApproval(response.getAuthNum());
        generalManager.updateInvoiceAfterPayment(invoiceObject);

        utils.setDefaultParameters(request, resp, model);
        model.addAttribute("pageName","payment_termination");

        return "tmpl_success_result";
    }


    @RequestMapping(value = "/payment-failure", produces = "text/plain")

    public String paymentFailure(@ModelAttribute PaymentResponse response, HttpServletResponse resp, HttpServletRequest request, Model model) {
        String result =  "result: " + response.getResult() + "\r\n"
                + "authNum: " + response.getAuthNum() + "\r\n"
                + "parmx:" + response.getParmx() + "\r\n"
                + "token:" + response.getToken() + "\r\n"
                + "id:" + response.getId() + "\r\n"
                + "creditCardHolder:" + response.getCreditCardHolder() + "\r\n"
                + "invoiceLink:" + response.getInvoiceLink();
        String errorNum = response.getResult().substring(0, 3);
      //  String errorNum = response.getResult().substring(0, response.getResult().indexOf('*'));

        // String errorNum = response.getResult();
        utils.setDefaultParameters(request, resp, model);
        model.addAttribute("pageName", "payment_termination");
        model.addAttribute("errorNum",errorNum);
        return "tmpl_failure_result";
    }


}
