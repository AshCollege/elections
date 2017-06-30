package com.example.Controllers;

import com.example.Objects.EmailObject;
import com.example.Objects.Entities.ConsumerObject;
import com.example.Objects.Entities.InvoiceObject;
import com.example.Objects.Entities.MemadClientObject;
import com.example.Services.GeneralManager;
import com.example.Utils.EmailUtils;
import com.example.Utils.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sigal on 4/11/2017.
 */

@Controller
public class DatabaseToolController {

    @Autowired
    private GeneralManager generalManager;

    @Autowired
    private TemplateUtils templateUtils;

    @Autowired
    private EmailUtils emailUtils;

    @RequestMapping("/send-emails.json")
    public void sendEmails(String clientOid, HttpServletResponse response) throws Exception{
        Map<Integer, List<InvoiceObject>> invoiceObjectsMap = generalManager.getAllInvoiceUnsentByEmail(Integer.valueOf(clientOid));
        int emailsSent = 0;
        for (Integer consumerOid : invoiceObjectsMap.keySet()) {
            ConsumerObject consumerObject = generalManager.loadConsumer(consumerOid);
            if (consumerObject != null) {
                EmailObject emailObject = new EmailObject();
                emailObject.setFrom(consumerObject.getMemadClientObject().getName());
                List<String> recipients = new ArrayList<>();
                recipients.add(consumerObject.getEmail());
                emailObject.setTo(recipients);
                emailObject.setSubject(templateUtils.getTranslation("invoice"));
                emailObject.setText(templateUtils.getTranslation("invoice.details"));
                //TODO: fill paths
//                List<InvoiceObject> unsentInvoices = invoiceObjectsMap.get(consumerOid);
//                emailObject.setFilesPaths();
                emailUtils.sendEmail(emailObject);
                emailsSent++;
            }
        }
        response.getWriter().write(emailsSent);
    }

    @RequestMapping("is-client-exist.json")
    public void isClientExist (String clientOid, HttpServletResponse response) throws Exception {
        MemadClientObject memadClientObject = generalManager.loadMemadClientObject(Integer.valueOf(clientOid));
        String clientName = "";
        if (memadClientObject != null) {
            clientName = memadClientObject.getName();
        }
        response.getWriter().write(clientName);
    }


}
