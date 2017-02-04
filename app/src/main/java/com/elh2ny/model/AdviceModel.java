package com.elh2ny.model;

/**
 * Created by mostafa_anter on 2/4/17.
 */

public class AdviceModel {
    private String subject;

    public AdviceModel(String subject) {
        this.subject = subject;
    }

    public AdviceModel() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
