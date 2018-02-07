package com.epam.rd.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.Control.FORMAT_PROPERTIES;

public class LocaleMessageProvider {
    private static LocaleMessageProvider provider = new LocaleMessageProvider();

    public static LocaleMessageProvider getInstance() {
        return provider;
    }

    ResourceBundle bundle;

    private LocaleMessageProvider() {
        bundle = get("ru");
    }

    public void changeLang(String language) {
        if (language.equals("Русский")) {
            bundle = get("ru");
        } else {
            bundle = get("en");
        }
    }

    private ResourceBundle get(String s) {
        return ResourceBundle.getBundle("Messages", new Locale.Builder()
                        .setLanguage(s).setScript("Cyrl").build(),
                ResourceBundle.Control.getControl(FORMAT_PROPERTIES));
    }

    public String encode(String in) {
        try {
            return new String(bundle.getString(in).getBytes("ISO-8859-1"), "windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "null";
    }

}
