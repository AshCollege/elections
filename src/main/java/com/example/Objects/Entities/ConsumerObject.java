package com.example.Objects.Entities;

/**
 * Created by Sigal on 1/4/2017.
 */
public class ConsumerObject {

    private Integer uid;
    private Integer consumerNum;
    private String name;
    private MemadClientObject memadClientObject;
    private boolean manager;
    private int lang;
    private boolean allowEmail;
    private String email;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getConsumerNum() {return consumerNum;}

    public void setConsumerNum(Integer consumerNum) {
        this.consumerNum = consumerNum;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public MemadClientObject getMemadClientObject() {
        return memadClientObject;
    }

    public void setMemadClientObject(MemadClientObject memadClientObject) {
        this.memadClientObject = memadClientObject;
    }
    public boolean isManager() {return manager; }

    public void setManager(boolean manager) {this.manager = manager;}

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public boolean isAllowEmail() {
        return allowEmail;
    }

    public void setAllowEmail(boolean allowEmail) {
        this.allowEmail = allowEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
