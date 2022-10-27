package com.urise.webapp.model;

public enum Contacts {
    PHONE_NUMBER("Номер телефона"),
    MAIL("Электронная почта"),
    SKYPE("Скайп");

    private final String title;

    Contacts(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
