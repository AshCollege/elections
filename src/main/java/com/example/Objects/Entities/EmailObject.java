package com.example.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sigal on 1/17/2017.
 */
public class EmailObject {
    private String from;
    private List<String> to = new ArrayList<>();
    private String subject;
    private String text;
    private List<String> filesPaths = new ArrayList<>();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getFilesPaths() {
        return filesPaths;
    }

    public void setFilesPaths(List<String> filesPaths) {
        this.filesPaths = filesPaths;
    }

}
