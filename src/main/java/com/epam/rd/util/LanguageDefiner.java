package com.epam.rd.util;

public class LanguageDefiner {

    public static void definePageLang(String pageLanguage) {
        if (pageLanguage != null) {
            if (pageLanguage.equals("ru")) {
                LocaleMessageProvider.getInstance().changeLang("Русский");
            } else if (pageLanguage.equals("en")) {
                LocaleMessageProvider.getInstance().changeLang("English");
            }
        } else {
            LocaleMessageProvider.getInstance().changeLang("English");
        }
    }
}
