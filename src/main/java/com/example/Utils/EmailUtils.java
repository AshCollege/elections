package com.example.Utils;

import com.example.Aspects.LoggingAspect;
import com.example.Objects.EmailObject;
import com.example.Services.GeneralManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.springframework.util.StringUtils.hasText;

@Component
public class EmailUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtils.class);

    @Autowired
    Utils utils;

    private JavaMailSenderImpl getMailSender() {
        JavaMailSenderImpl mailSender = null;
        try {
            mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername(utils.getConfigValueByKey("email_username"));
            mailSender.setPassword(utils.getConfigValueByKey("email_password"));
            if (!(hasText(mailSender.getPassword()) && hasText(mailSender.getUsername()))) {
                LOGGER.error(LOGGER.getName() + ", getMailSender, missing username or password");
                throw new Exception();
            }
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.auth","true");
            properties.setProperty("mail.smtp.starttls.enable","true");
            properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            mailSender.setJavaMailProperties(properties);
        } catch (Exception e) {
            LOGGER.error(LOGGER.getName() + ", getMailSender", e);
            return null;
        }
        return mailSender;
    }


    public boolean sendEmail(EmailObject email) {
        boolean success = false;
        JavaMailSender mailSender = getMailSender();
        if (mailSender != null) {
            MimeMessage message = mailSender.createMimeMessage();
            try{
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(email.getFrom());
                String [] recipients = new String[email.getTo().size()];
                helper.setTo(email.getTo().toArray(recipients));
                helper.setSubject(email.getSubject());
                helper.setText(email.getText());
                for(String filePath : email.getFilesPaths()) {
                    FileSystemResource file = new FileSystemResource(filePath);
                    helper.addAttachment(file.getFilename(), file);
                }
            } catch (MessagingException e) {
                throw new MailParseException(e);
            }
            mailSender.send(message);
            success = true;
        } else {
            LOGGER.error(LOGGER.getName() + ", sendEmail , error initializing email error initializing email properties, email won't be sent to: " + email.getTo());
        }
        return success;
    }
}
