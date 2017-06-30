package com.example.Objects.General;

import org.springframework.stereotype.Component;

/**
 * Params that can be passed in payment request
 */
@Component
public class RequestParams {

    //Terminal auth params
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String TERMINAL = "termNo";

    //Required params
    public static final String GOOD_URL = "goodUrl";
    public static final String ERROR_URL = "errorUrl";
    public static final String TOTAL = "total";
    public static final String CURRENCY = "currency";
    public static final String MAX_PAYMENTS = "maxPayments";
    public static final String MIN_PAYMENTS = "minPaymentsNo";
    public static final String CREDIT_TYPE_FROM = "creditTypeFrom";

    //OptionalFields
    public static final String VALIDATE_LINK = "ValidateLink";
    public static final String ERROR_LINK = "ErrorLink";
    public static final String STYLE_SHEET_ADDRESS = "styleSheetAddress";
    public static final String HEAD_TEXT = "headText";
    public static final String BOTTOM_TEXT = "bottomText";
    public static final String HIDE_PELECARD_LOGO = "hidePelecardLogo";
    public static final String BACKGROUND = "Background";
    public static final String BACKGROUND_IMAGE = "backgroundImage";
    public static final String TOP_MARGIN = "topMargin";
    public static final String SUPPORTED_CARD_TYPES = "topMargin";
    public static final String PARMX = "parmx";
    public static final String NAME_TO_PARMX = "nameToParmX";
    public static final String HIDE_PARMX = "hideParmx";
    public static final String CANCEL_URL = "cancelUrl";
    public static final String SUPPORT_PHONE = "supportPhone";
    public static final String ERROR_TEXT = "errorText";
    public static final String CREDIT_CARD_HOLDER = "CreditCardHolder";
    public static final String ID = "Id";
    public static final String CVV2 = "cvv2";
    public static final String AUTH_NUM = "authNum";
   // public static final String AUTH_NUM = "123456";
    public static final String SHOP_NO = "shopNo";
    public static final String FRM_ACTION = "frmAction";
    public static final String TOKEN_FOR_TERMINAL = "TokenForTerminal";
    public static final String J5 = "J5";
    public static final String KEEP_SSL = "keepSSL";
    public static final String DESIGHN_INPUT = "DesighnInput";
    public static final String CC_DASH = "CCDash";
    public static final String FOCUS = "Focus";
}
