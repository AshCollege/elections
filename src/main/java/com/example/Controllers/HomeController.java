package com.example.Controllers;

import com.example.Objects.Entities.ConsumerObject;
import com.example.Objects.Entities.FaqObject;
import com.example.Objects.Entities.InvoiceObject;
import com.example.Objects.Entities.MemadClientObject;
import com.example.Services.GeneralManager;
import com.example.Utils.Utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.Utils.Definitions.LOGIN_ERROR_TYPE_WRONG_CUNSUMER_NUMBER;
import static com.example.Utils.Definitions.LOGIN_ERROR_TYPE_WRONG_INVOICE_NUMBER;
import static com.example.Utils.Definitions.LOGIN_ERROR_TYPE_WRONG_MISMATCH;
import static org.springframework.util.StringUtils.hasText;

/**
 * Created by Sigal on 5/16/2016.
 */
@Controller
public class HomeController {

    @Autowired
    private Utils utils;

    @Autowired
    private GeneralManager generalManager;

    @RequestMapping({"/", "/home"})
    public String showHomePage(HttpServletRequest request, HttpServletResponse response, Model model, String userId, String errorType) {
        boolean error = false;
        try {
            String errorMessage = null;
            if (errorType != null) {
                switch (Integer.valueOf(errorType)) {
                    case LOGIN_ERROR_TYPE_WRONG_CUNSUMER_NUMBER:
                        errorMessage = "wrong.consumer.number.try.again";
                        break;
                    case LOGIN_ERROR_TYPE_WRONG_INVOICE_NUMBER:
                        errorMessage = "wrong.invoice.number.try.again";
                        break;

                    case LOGIN_ERROR_TYPE_WRONG_MISMATCH:
                        errorMessage = "Wrong.Credentials.try.again";
                        break;
                }
            }
            if (errorMessage != null) {
                model.addAttribute("errorMessage", errorMessage);
            }
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName", "login");
            List<MemadClientObject> clients = generalManager.getAllClients();

            model.addAttribute("clients",clients);
        } catch (Exception e) {
            error = true;
        }
        return "tmpl_home";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model, String clientOid, String consumer, String invoice) throws Exception{
       boolean error = false;
        int errorType = 0;

        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("templateUtils", utils.templateUtils);
           Integer clientId=  Integer.valueOf(clientOid);
           Integer consumerId=  Integer.valueOf(consumer);
           Integer invoiceNum = Integer.valueOf(invoice) ;
           InvoiceObject currentInvoice = generalManager.getUserByLoginDetails(clientId,consumerId,invoiceNum);
            if (currentInvoice != null) {
                response.addCookie(new Cookie("consumerUid", consumer));
                request.getSession().setAttribute("uid", clientId);
                ConsumerObject consumerObject= generalManager.loadConsumer(consumerId);

                if (consumerObject.isManager()) {
                    response.sendRedirect("/manager-dashboard?consumerUid=" + consumer);
                }
            else {
                    response.sendRedirect("/dashboard?consumerUid=" + consumer);
                }
            } else {
               error = true;
                ConsumerObject consumerObject= generalManager.loadConsumer(consumerId);
                if(consumerObject==null) {
                    errorType = LOGIN_ERROR_TYPE_WRONG_CUNSUMER_NUMBER;
                  }else {
                  List<InvoiceObject> invoiceObjects = generalManager.getInvoiceByNum(invoiceNum);
                    if(invoiceObjects.size()==0){
                        errorType = LOGIN_ERROR_TYPE_WRONG_INVOICE_NUMBER;
                    }else{
                        errorType = LOGIN_ERROR_TYPE_WRONG_MISMATCH;
                    }
                }

            }
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("home?errorType=" + errorType);
        }
        return "tmpl_home";
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            response.addCookie(new Cookie("consumerUid", null));
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            return "tmpl_error";
        } else {
            response.sendRedirect("/home");
        }
        return "tmpl_home";
    }


}
