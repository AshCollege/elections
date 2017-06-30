package com.example.Services;

import com.example.Objects.Entities.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sigal on 5/21/2016.
 */

@Transactional
public interface GeneralManager {

    public List<ConfigObject> getConfig();

    public Map<String, TranslationObject> getAllTranslations();

    public void updateConfigObject(ConfigObject configObject);

    public ConfigObject getLatestDebtSavesDateConfig();

    public  List<MemadClientObject> getAllClients();

    public List<InvoiceObject> getAllConsumersnvoices(Integer consumerUid);

    public List<InvoiceObject> getAllUnpaidConsumerInvoices(Integer consumerUid);

    public InvoiceObject getUserByLoginDetails(Integer clientId, Integer consumerId, Integer invoiceNum);

    public ConsumerObject loadConsumer(Integer consumerUid);

    public InvoiceObject loadInvoice (Integer invoiceId);

    public List<InvoiceObject> getInvoiceByNum (Integer invoiceNum);

    public void updateMemdadClientObject (MemadClientObject memadClientObject);

    public MemadClientObject loadMemadClientObject(int oid);

    public List<FaqObject> getAllFaq();

    public List<ConsumerObject> getAllConsumersByClientId(Integer clientId);

    public List<InvoiceObject> getAllClientinvoicesByClientId(Integer clientId);

    public List<InvoiceObject> getAllUnpaidClientInvoices(Integer clientId);

    public List<InvoiceObject> getAllPaidClientInvoices(Integer clientId);

    public List<InvoiceObject> getAllClientinvoicesByYear(Integer clientId,int year);

    public MemadClientObject loadClient(int clientId);

    public List<Integer> searchInvoicesByParameters(Integer lowInvoiceNum,Integer highInvoiceNum,Integer lowConsumerUid,Integer highConsumerUid,Integer lowTotal, Integer highTotal,Date lowDate, Date highDate, int lowRun, int highRun,int month, int year, Boolean paid, Integer paymentConfirmation);

    public void updateInvoiceAfterPayment (InvoiceObject invoiceObject);

    public void updateConsumerObject(ConsumerObject consumerObject);

    public List<LangObject> getActiveLanguages();

    public Map<Integer, List<InvoiceObject>> getAllInvoiceUnsentByEmail(int clientOid);

}