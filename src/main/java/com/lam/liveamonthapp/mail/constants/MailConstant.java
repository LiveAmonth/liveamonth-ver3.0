package com.lam.liveamonthapp.mail.constants;

public enum MailConstant {
    PW_SUBJECT("pw.subject"),
    HELLO("hello"),
    INTRO("intro"),
    TEMP("temp"),
    LOGIN("login"),
    EDIT("edit"),
    ASK("ask"),
    END("end");
    private String code;

    MailConstant(String code) {
        this.code = code;
    }

    public String getCode() {
        return "mail.comment." + code;
    }
    public String getPwMailCode(){
        return "mail." + code;
    }
}
