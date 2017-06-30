package com.example.Controllers;

import com.example.Objects.EmailObject;
import com.example.Objects.Entities.ConsumerObject;
import com.example.Objects.Entities.FaqObject;
import com.example.Objects.Entities.InvoiceObject;
import com.example.Objects.Entities.LangObject;
import com.example.Services.GeneralManager;
import com.example.Utils.EmailUtils;
import com.example.Utils.TemplateUtils;
import com.example.Utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sigal on 2/6/2017.
 */

@Controller
public class GeneralController {

    @Autowired
    private Utils utils;

    @Autowired
    private GeneralManager generalManager;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private TemplateUtils templateUtils;


    @RequestMapping("/faq")
    public String faq(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            List<FaqObject> faq = generalManager.getAllFaq();
            model.addAttribute("faq",faq);
            model.addAttribute("pageName","faq");
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_faq";
    }

    @RequestMapping("/contact")
    public String contact(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        boolean error = false;
        try {
            utils.setDefaultParameters(request, response, model);
            model.addAttribute("pageName","contact");
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_contact";
    }

    @RequestMapping("/settings")
    public String settings(@CookieValue("consumerUid") Integer consumerUid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        boolean error = false;
        try {
            model.addAttribute("pageName","settings");
            utils.setDefaultParameters(request, response, model);
            ConsumerObject consumer = generalManager.loadConsumer(consumerUid);
            List<LangObject> languages = generalManager.getActiveLanguages();
            model.addAttribute("languages",languages);
            model.addAttribute("lang",consumer.getLang());
        } catch (Exception e) {
            error = true;
        }
        if (error) {
            response.sendRedirect("/home?error=true");
        }
        return "tmpl_settings";
    }

    @RequestMapping("change-lang.json")
    public void changeLang(String langCode, @CookieValue("consumerUid") Integer consumerUid, HttpServletResponse response) throws Exception{
        boolean error = false;
        ConsumerObject consumerObject = generalManager.loadConsumer(consumerUid);
        if (consumerObject != null) {
            consumerObject.setLang(Integer.valueOf(langCode));
            generalManager.updateConsumerObject(consumerObject);
        } else {
            error = true;
        }
        response.setContentType("application/json; charset=UTF-8");
        JSONObject JObject = new JSONObject();
        JObject.put("error", error);
        response.getWriter().print(JObject);

    }

}
