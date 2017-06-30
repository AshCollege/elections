package com.example.Controllers;

import com.example.Objects.EmailObject;
import com.example.Utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    @Autowired
    private EmailUtils emailUtils;

    @RequestMapping("/mail")
    @ResponseBody
    public String sendMail() {
        EmailObject email = new EmailObject();
        email.setFrom("Shai");

        email.getTo().add("shaigivati464@gmail.com");

        email.setSubject("Test subject");
        email.setText("This is test mail");

        email.getFilesPaths().add("C://pais.log");
        email.getFilesPaths().add("C://stati.log");
        return emailUtils.sendEmail(email) ? "ok" : "error";
    }
}
