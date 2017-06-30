package com.example.Utils;

import com.example.Objects.Entities.ConfigObject;
import com.example.Objects.Entities.ConsumerObject;
import com.example.Objects.Entities.TranslationObject;
import com.example.Services.GeneralManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.util.StringUtils.hasText;

/**
 * Created by Sigal on 5/16/2016.
 */
public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    @Autowired
    public TemplateUtils templateUtils;

    @Autowired
    private GeneralManager generalManager;

    private static List<ConfigObject> config = null;

    public static Map<String, TranslationObject> translations = null;

    @PostConstruct
    private void initialize(){
        config = generalManager.getConfig();
        translations = generalManager.getAllTranslations();
    }


    public void setDefaultParameters(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("siteName", getConfigValueByKey("site_name"));
        model.addAttribute("templateUtils", templateUtils);
        model.addAttribute("lang",getLang(request));

    }


    public int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public Date getCurrentMonthFirstDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LocalDateTime now = LocalDateTime.now();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), getMonth(date), 1);
        return cal.getTime();
    }

    public Date getNextMonthFirstDate(Date date){
        int month = getMonth(date);
        int nextMonth = 0;
        if (month == 12) {
            nextMonth = 1;
        } else {
            nextMonth = ++month;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), nextMonth, 1);
        return cal.getTime();
    }



    public static String getConfigValueByKey(String key){
        for (ConfigObject con: config) {
            if(con.getConfigKey().equals(key)){
                return con.getConfigValue();
            }
        }
        return  "0";
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        String value = "0";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return hasText(value) ? value : "0";
    }

    private int getLang(HttpServletRequest request) {
        int lang = 0;
        try {
            Integer uid = Integer.valueOf(getCookieValueByName(request,"consumerUid"));
            if (uid != null && uid != 0) {
                ConsumerObject consumerObject = generalManager.loadConsumer(uid);
                if (consumerObject != null) {
                    lang = consumerObject.getLang();
                }
            }
        } catch (Exception e) {
            LOGGER.error(LOGGER.getName() + ", getLang, could not get lang - using default");
        }
        return lang;
    }


}
