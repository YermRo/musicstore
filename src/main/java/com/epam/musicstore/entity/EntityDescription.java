package com.epam.musicstore.entity;

import java.util.Locale;

public class EntityDescription extends BaseEntity {
    private String ruDescription;
    private String enDescription;

    public String getRuDescription() {
        return ruDescription;
    }

    public void setRuDescription(String ruDescription) {
        this.ruDescription = ruDescription;
    }

    public String getEnDescription() {
        return enDescription;
    }

    public void setEnDescription(String enDescription) {
        this.enDescription = enDescription;
    }

    public String getDescription(Locale locale) {
        if (locale != null && locale.getLanguage().equals("ru")) {
            return ruDescription;
        } else {
            return enDescription;
        }
    }

    @Override
    public String toString() {
        return "EntityDescription{" +
                "ruDescription='" + ruDescription + '\'' +
                ", enDescription='" + enDescription + '\'' +
                '}';
    }
}
