package com.example;

import com.example.Objects.Entities.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Sigal on 5/20/2016.
 */
@Service
@Component
public class Persist {

    private SessionFactory sessionFactory;

    @Autowired
    public Persist(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Session getQuerySession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Object o) {
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    public Object load(Class clazz, int id) {
        return getQuerySession().get(clazz, id);
    }

    public Object load(Class clazz, long id) {
        return getQuerySession().get(clazz, id);
    }


    public List<TranslationObject> getAllTranslations() {
        Query query = getQuerySession().createQuery("FROM TranslationObject");
        return (List<TranslationObject>) query.list();
    }

    public ConfigObject getLatestDebtSavesDateConfig() {
        Query query = getQuerySession().createQuery("FROM ConfigObject WHERE configKey = 'latestDebtSaved'");
        return (ConfigObject) query.uniqueResult();
    }

    public List<ConfigObject> getConfig() {
        return (List<ConfigObject>) getQuerySession().createQuery("FROM ConfigObject").list();
    }

    public List<MemadClientObject> getAllClients() {
        return (List<MemadClientObject>) getQuerySession().createQuery("FROM MemadClientObject").list();
    }

    public List<InvoiceObject> getAllConsumersnvoices(Integer consumerUid) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM InvoiceObject WHERE consumerObject.uid =" + consumerUid + " ORDER BY date Desc").list();
    }

    public List<InvoiceObject> getAllUnpaidConsumerInvoices(Integer consumerUid) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM InvoiceObject WHERE clientObject.oid =" + consumerUid + " AND paid= FALSE").list();
    }

    public InvoiceObject getUserByLoginDetails(Integer clientId, Integer consumerId, Integer invoiceNum) {
        return (InvoiceObject) getQuerySession().createQuery("FROM InvoiceObject WHERE clientObject.oid =" + clientId + " AND consumerObject.id= " + consumerId + " AND invoiceNum =" + invoiceNum).uniqueResult();
        //        "SELECT consumerObject FROM InvoiceObject WHERE clientObject.oid =" + clientId + "" +
        //      " AND consumerObject.id= " + consumerId + " AND num =" + invoiceNum + " AND total =" + total  ).uniqueResult();
    }

    public List<InvoiceObject> getInvoiceByNum(Integer invoiceNum) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM InvoiceObject WHERE invoiceNum=" + invoiceNum).list();
    }

    public List<FaqObject> getAllFaq() {
        return (List<FaqObject>) getQuerySession().createQuery("FROM FaqObject").list();
    }

    public List<ConsumerObject> getAllConsumersByClientId(Integer clientId) {
        return (List<ConsumerObject>) getQuerySession().createQuery("FROM ConsumerObject WHERE memadClientObject.oid= " + clientId).list();
    }

    public List<InvoiceObject> getAllClientinvoicesByClientId(Integer clientId) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM  InvoiceObject WHERE clientObject.oid= " + clientId + " AND consumerObject.manager = " + false + "  ORDER BY date DESC").list();
    }

    public List<InvoiceObject> getAllUnpaidClientInvoices(Integer clientId) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM InvoiceObject WHERE clientObject.oid= " + clientId + "AND paid= " + false + " AND consumerObject.manager = " + false + "  ORDER BY date DESC").list();
    }

    public List<InvoiceObject> getAllPaidClientInvoices(Integer clientId) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM InvoiceObject WHERE clientObject.oid= " + clientId + "AND paid= " + true + " AND consumerObject.manager = " + false + "  ORDER BY date DESC").list();
    }

    public List<InvoiceObject> getAllClientinvoicesByYear(Integer clientId, int year) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM InvoiceObject WHERE clientObject.oid= " + clientId + " AND year= " + year + " AND consumerObject.manager = " + false + "  ORDER BY date DESC").list();
    }

    public List<Integer> searchInvoicesByParameters(Integer lowInvoiceNum, Integer highInvoiceNum, Integer lowConsumerUid, Integer highConsumerUid, Integer lowTotal, Integer highTotal, Date lowDate, Date highDate, int lowRun, int highRun, int month, int year, Boolean paid, Integer paymentConfirmation) {
        boolean whereExist = false;

        StringBuilder query = new StringBuilder("SELECT oid FROM InvoiceObject");
        if (lowInvoiceNum != null) {
            query.append(" WHERE invoiceNum BETWEEN ");
            query.append(lowInvoiceNum);
            query.append(" AND ");
            query.append(highInvoiceNum);
            whereExist = true;
        }
        if (lowConsumerUid != null) {
            query.append(whereExist ? " AND" : " WHERE consumerObject.consumerNum BETWEEN ");
            query.append(lowConsumerUid);
            query.append(" AND ");
            query.append(highConsumerUid);
            whereExist = true;

        }

        if (lowTotal != null) {
            if (whereExist) {
                query.append(" AND total BETWEEN ");

            } else {
                query.append(" WHERE total BETWEEN ");
                whereExist = true;
            }

            query.append(lowTotal);
            query.append(" AND ");
            query.append(highTotal);
        }
        if (lowDate != null) {
            query.append(whereExist ? " AND" : " WHERE date BETWEEN ");
            query.append(lowDate);
            query.append(" AND ");
            query.append(highDate);
            whereExist = true;
            query.append("' ");
        }
        if (lowRun != 0) {
            query.append(whereExist ? " AND" : " WHERE run BETWEEN ");
            query.append(lowRun);
            query.append(" AND ");
            query.append(highRun);
            whereExist = true;

        }
        if (month != 0) {
            if (whereExist) {
                query.append(" AND month=");
            } else {
                query.append(" WHERE month=");
                whereExist = true;

            }
            query.append(month);
        }

        if (year != 0) {
            if (whereExist) {
                query.append(" AND year=");
            } else {
                query.append(" WHERE year=");
                whereExist = true;

            }

            query.append(year);
        }

        if (paid != null) {
            if (whereExist) {
                query.append(" AND paid=");
            } else {
                query.append(" WHERE paid=");
                whereExist = true;
            }

            query.append(paid);
        }

        if (paymentConfirmation != null) {
            if (whereExist) {
                query.append(" AND paymentConfirmation=");
            } else {
                query.append(" WHERE paymentConfirmation=");
                whereExist = true;
            }
            query.append(paymentConfirmation);
        }

        return (List<Integer>) getQuerySession().createQuery(query.toString()).list();


    }

    public List<LangObject> getActiveLanguages() {
        return (List<LangObject>) getQuerySession().createQuery("FROM LangObject WHERE active=TRUE").list();
    }

    public List<InvoiceObject> getAllInvoiceUnsentByEmail(int clientOid) {
        return (List<InvoiceObject>) getQuerySession().createQuery("FROM InvoiceObject WHERE emailSent=FALSE").list();
    }

}