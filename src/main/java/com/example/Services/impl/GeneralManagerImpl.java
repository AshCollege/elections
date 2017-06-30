package com.example.Services.impl;

import com.example.Objects.Entities.*;
import com.example.Persist;
import com.example.Services.GeneralManager;
import com.example.Utils.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.Utils.Definitions.PARAM_LANG_DEFAULT;
import static com.example.Utils.Definitions.PARAM_LANG_EN;
import static org.springframework.util.StringUtils.hasText;


/**
 * Created by Sigal on 5/21/2016.
 */

@Service
public class GeneralManagerImpl implements GeneralManager {

    @Autowired
    private Persist persist;

    @Autowired
    private TemplateUtils templateUtils;

    public List<ConfigObject> getConfig() {
        return persist.getConfig();
    }

    public Map<String, TranslationObject> getAllTranslations() {
        List<TranslationObject> translationObjectList = persist.getAllTranslations();
        Map<String, TranslationObject> translations = new HashMap<>();
        for (TranslationObject translationObject : translationObjectList) {
            translations.put(translationObject.getTranslationKey().toLowerCase(), translationObject);
        }
        return translations;
    }

    public void updateConfigObject(ConfigObject configObject) {
        persist.save(configObject);
    }

    public ConfigObject getLatestDebtSavesDateConfig() {
        return persist.getLatestDebtSavesDateConfig();
    }

    public List<MemadClientObject> getAllClients() {
        return persist.getAllClients();
    }

    public List<InvoiceObject> getAllConsumersnvoices(Integer consumerUid) {
        return persist.getAllConsumersnvoices(consumerUid);
    }

    public List<InvoiceObject> getAllUnpaidConsumerInvoices(Integer consumerUid) {
        return persist.getAllUnpaidConsumerInvoices(consumerUid);
    }

    public InvoiceObject getUserByLoginDetails(Integer clientId, Integer consumerId, Integer invoiceNum) {
        return persist.getUserByLoginDetails(clientId, consumerId, invoiceNum);
    }

    public ConsumerObject loadConsumer(Integer consumerUid) {
        return (ConsumerObject) persist.load(ConsumerObject.class, consumerUid);
    }

    public InvoiceObject loadInvoice(Integer invoiceId) {
        return (InvoiceObject) persist.load(InvoiceObject.class, invoiceId);
    }

    public List<InvoiceObject> getInvoiceByNum(Integer invoiceNum) {
        return persist.getInvoiceByNum(invoiceNum);
    }

    public void updateMemdadClientObject(MemadClientObject memadClientObject) {
        persist.save(memadClientObject);
    }

    public MemadClientObject loadMemadClientObject(int oid) {
        return (MemadClientObject) persist.load(MemadClientObject.class, oid);
    }

    public List<FaqObject> getAllFaq() {
        return persist.getAllFaq();
    }

    public List<ConsumerObject> getAllConsumersByClientId(Integer clientId) {
        return persist.getAllConsumersByClientId(clientId);
    }

    public List<InvoiceObject> getAllClientinvoicesByClientId(Integer clientId) {
        return persist.getAllClientinvoicesByClientId(clientId);

    }

    public List<InvoiceObject> getAllUnpaidClientInvoices(Integer clientId) {
        return persist.getAllUnpaidClientInvoices(clientId);
    }

    public List<InvoiceObject> getAllPaidClientInvoices(Integer clientId) {
        return persist.getAllPaidClientInvoices(clientId);
    }

    public List<InvoiceObject> getAllClientinvoicesByYear(Integer clientId, int year) {
        return persist.getAllClientinvoicesByYear(clientId, year);
    }

    public MemadClientObject loadClient(int clientId) {
        return (MemadClientObject) persist.load(MemadClientObject.class, clientId);
    }

    public List<Integer>  searchInvoicesByParameters(Integer lowInvoiceNum ,Integer highInvoiceNum, Integer lowConsumerUid,  Integer highConsumerUid,Integer lowTotal,Integer highTotal, Date lowDate, Date highDate, int lowRun,int highRun, int month, int year, Boolean paid, Integer paymentConfirmation) {
        return  persist.searchInvoicesByParameters(lowInvoiceNum, highInvoiceNum,lowConsumerUid, highConsumerUid,lowTotal,highTotal, lowDate,highDate, lowRun, highRun, month, year, paid, paymentConfirmation);
    }
    public void updateInvoiceAfterPayment(InvoiceObject invoiceObject) {
        persist.save(invoiceObject);
    }

    public void updateConsumerObject(ConsumerObject consumerObject) {
        persist.save(consumerObject);
    }

    public List<LangObject> getActiveLanguages() {
        return persist.getActiveLanguages();
    }

    public Map<Integer, List<InvoiceObject>> getAllInvoiceUnsentByEmail(int clientOid) {
        Map<Integer, List<InvoiceObject>> unsentInvoicesMap = new HashMap<>();
        List<InvoiceObject> unsentInvoices = persist.getAllInvoiceUnsentByEmail(clientOid);
        for (InvoiceObject invoiceObject : unsentInvoices) {
            if (invoiceObject.getConsumerObject().isAllowEmail() && invoiceObject.getConsumerObject().getEmail() != null) {
                List<InvoiceObject> unsentListByClient = unsentInvoicesMap.get(invoiceObject.getConsumerObject().getUid());
                if (unsentListByClient == null) {
                    unsentListByClient = new ArrayList<>();
                }
                unsentListByClient.add(invoiceObject);
                unsentInvoicesMap.put(invoiceObject.getConsumerObject().getUid(), unsentListByClient);
            }
        }
        return unsentInvoicesMap;
    }


}
