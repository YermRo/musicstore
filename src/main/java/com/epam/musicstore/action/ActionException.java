package com.epam.musicstore.action;

public class ActionException extends Exception {
    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, Exception e) {
        super(message, e);
    }

}
