package com.epam.musicstore.action;

public class ActionResult {
    private String view;
    private final boolean redirect;

    public ActionResult(String view, boolean redirect) {
        this.view = view;
        this.redirect = redirect;
    }

    public ActionResult(String view) {
        this.view = view;
        this.redirect = false;
    }

    public String getView() {
        return view;
    }

    public boolean isRedirect(){
        return redirect;
    }
}
