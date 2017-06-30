package com.example.Objects.Entities;

import java.util.Date;

/**
 * Created by Sigal on 1/4/2017.
 */
public class InvoiceObject {

    private Integer oid;
    private Integer invoiceNum;
    private String details;
    private Integer consumption;
    private ConsumerObject consumerObject;
    private Integer total;
    private MemadClientObject clientObject;
    private Date date;
    private int run;
    private int month;
    private int year;
    private Boolean paid;
    private Integer paymentConfirmation;
    private String mail;
    private Integer packet;
    private Date paymentDate;
    private Integer paidAmount;
    private String cardType;
    private String transactionApproval;
    private boolean emailSent;

    public Integer getOid() { return oid; }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getDetails() {
        return (details != null) ? details : "";
    }

    public void setDetails(String details) {this.details = details; }

    public Integer getConsumption() {return consumption;}

    public void setConsumption(Integer consumption) {this.consumption = consumption;}

    public ConsumerObject getConsumerObject() {
        return consumerObject;
    }

    public void setConsumerObject(ConsumerObject consumerObject) {
        this.consumerObject = consumerObject;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public MemadClientObject getClientObject() {
        return clientObject;
    }

    public void setClientObject(MemadClientObject clientObject) {
        this.clientObject = clientObject;
    }

    public Date getDate() {return date; }

    public void setDate(Date date) {this.date = date; }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {return year;}

    public void setYear(int year) {this.year = year;}

    public Boolean getPaid() {return paid; }

    public void setPaid(Boolean paid) {this.paid = paid; }

    public Integer getPaymentConfirmation() {
        return paymentConfirmation;
    }

    public void setPaymentConfirmation(Integer paymentConfirmation) {
        this.paymentConfirmation = paymentConfirmation;
    }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public Integer getPacket() { return packet; }

    public void setPacket(Integer packet) { this.packet = packet; }

    public Date getPaymentDate() {return paymentDate; }

    public void setPaymentDate(Date paymentDate) {this.paymentDate = paymentDate; }

    public Integer getPaidAmount() {return paidAmount; }

    public void setPaidAmount(Integer paidAmount) { this.paidAmount = paidAmount; }

    public String getCardType() {return cardType; }

    public void setCardType(String cardType) {this.cardType = cardType; }

    public String getTransactionApproval() {return transactionApproval; }

    public void setTransactionApproval(String transactionApproval) {this.transactionApproval = transactionApproval; }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }
}
