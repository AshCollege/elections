package com.example.Utils;

import com.example.Objects.Entities.TranslationObject;
import com.example.Services.GeneralManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.Utils.Definitions.PARAM_LANG_DEFAULT;
import static com.example.Utils.Definitions.PARAM_LANG_EN;
import static org.springframework.util.StringUtils.hasText;

/**
 * Created by Sigal on 5/18/2016.
 */
public class TemplateUtils {

    public Map<String, String> translations = null;

    @Autowired
    private GeneralManager generalManager;

    @Autowired
    private Utils utils;

    public String getTranslation(String key, Integer lang) {
        if (lang == null) {
            lang = PARAM_LANG_DEFAULT;
        }
        String value = "";
        try {
            TranslationObject translationObject = Utils.translations.get(key.toLowerCase());
            if (translationObject != null) {
                switch (lang) {
                    case PARAM_LANG_EN:
                        value = translationObject.getEn();
                        break;
                }
                if (!hasText(value)) {
                    value = hasText(translationObject.getTranslationValue()) ? translationObject.getTranslationValue() : key;
                }
            } else {
                value = key;
            }
        } catch (Exception e) {
            value = "";
        }
        return value;
    }


    public String getTranslation(String key, String param) {
        try {
            String value = getTranslation(key);
            return value.replace("%@", param);

        } catch (Exception e) {
            int i = 0;
        }
        return "";
    }



       public String formatDateExcludeTime(Date date) {
        String dateAsString = "";
           if (date != null) {
               try {
                   DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                   dateAsString = formatter.format(date);
               } catch (Exception e) {
                   dateAsString = date.toString();
               }
           }
        return dateAsString;
    }

    public Date convertStringToDate(String stringDate) {
       DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        Date date;
        try {
            date = dateFormat.parse(stringDate);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }


    public String getHebrewMonth(String month) {
        return month.replace("Jan", "ינואר")
                .replace("Feb", "פברואר")
                .replace("Mar", "מרץ")
                .replace("Apr", "אפריל")
                .replace("May", "מאי")
                .replace("Jun", "יוני")
                .replace("Jul", "יולי")
                .replace("Aug", "אוגוסט")
                .replace("Sep", "ספטמבר")
                .replace("Oct", "אוקטובר")
                .replace("Nov", "נובמבר")
                .replace("Dec", "דצמבר");
    }

    public String getTranslation(String key) {
        return getTranslation(key, 0);
    }


    }
