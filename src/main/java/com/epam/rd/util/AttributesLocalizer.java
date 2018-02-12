package com.epam.rd.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AttributesLocalizer {

    public static void getLang(String pageLangRequest, HttpServletRequest request, HttpSession session) {
        if (pageLangRequest == null || pageLangRequest.equals("")) {
            String pageLanguage = (String) session.getAttribute("lang");
            session.setAttribute("lang", pageLanguage);
            LanguageDefiner.definePageLang(pageLanguage);
            localizePageAttributes(request);
        } else {
            session.setAttribute("lang", pageLangRequest);
            LanguageDefiner.definePageLang(pageLangRequest);
            localizePageAttributes(request);
        }
    }

    public static void localizePageAttributes(HttpServletRequest request) {
        String welcomeMessage = LocaleMessageProvider.getInstance().encode("login_welcome");
        request.setAttribute("login_welcome", welcomeMessage);

        String buy = LocaleMessageProvider.getInstance().encode("buy");
        request.setAttribute("buy", buy);

        String main = LocaleMessageProvider.getInstance().encode("main");
        request.setAttribute("main", main);

        String login = LocaleMessageProvider.getInstance().encode("login");
        request.setAttribute("login", login);

        String register = LocaleMessageProvider.getInstance().encode("register");
        request.setAttribute("register", register);

        String settings = LocaleMessageProvider.getInstance().encode("settings");
        request.setAttribute("settings", settings);

        String currentDate = LocaleMessageProvider.getInstance().encode("currentDate");
        request.setAttribute("currentdate", currentDate);

        String password = LocaleMessageProvider.getInstance().encode("labelPassword");
        request.setAttribute("labelpassword", password);

        String email = LocaleMessageProvider.getInstance().encode("labelEmail");
        request.setAttribute("labelemail", email);

        String title = LocaleMessageProvider.getInstance().encode("workspaceTitle");
        request.setAttribute("workspacetitle", title);

        String workspacePrice = LocaleMessageProvider.getInstance().encode("workspacePrice");
        request.setAttribute("price", workspacePrice);

        String workspaceDescription = LocaleMessageProvider.getInstance().encode("workspaceDescription");
        request.setAttribute("description", workspaceDescription);

        String startDate = LocaleMessageProvider.getInstance().encode("startDate");
        request.setAttribute("startdate", startDate);

        String endDate = LocaleMessageProvider.getInstance().encode("endDate");
        request.setAttribute("enddate", endDate);

        String status = LocaleMessageProvider.getInstance().encode("status");
        request.setAttribute("status", status);

        String message = LocaleMessageProvider.getInstance().encode("workspaceMessage");
        request.setAttribute("workspacemessage", message);

        String amount = LocaleMessageProvider.getInstance().encode("amount");
        request.setAttribute("amount", amount);

        String replenish = LocaleMessageProvider.getInstance().encode("replenish");
        request.setAttribute("replenish", replenish);

        String balance = LocaleMessageProvider.getInstance().encode("balance");
        request.setAttribute("labelbalance", balance);

        String wrongPassword = LocaleMessageProvider.getInstance().encode("wrong_password");
        request.setAttribute("wrongpassword", wrongPassword);

        String noSuchEmail = LocaleMessageProvider.getInstance().encode("no_such_email");
        request.setAttribute("nosuchemail", noSuchEmail);

        String name = LocaleMessageProvider.getInstance().encode("name");
        request.setAttribute("name", name);

        String logout = LocaleMessageProvider.getInstance().encode("logout");
        request.setAttribute("logout", logout);

        String surname = LocaleMessageProvider.getInstance().encode("surname");
        request.setAttribute("surname", surname);

        String join = LocaleMessageProvider.getInstance().encode("join");
        request.setAttribute("join", join);

        String wrongEmailFormat = LocaleMessageProvider.getInstance().encode("wrong_email_format");
        request.setAttribute("wrong_email_format", wrongEmailFormat);

        String passwordTooShort = LocaleMessageProvider.getInstance().encode("password_too_short");
        request.setAttribute("password_too_short", passwordTooShort);

        String emailAlreadyExists = LocaleMessageProvider.getInstance().encode("email_already_exists");
        request.setAttribute("existsemail", emailAlreadyExists);

        String emptyFields = LocaleMessageProvider.getInstance().encode("empty_fields");
        request.setAttribute("empty_fields", emptyFields);

        String myProfile = LocaleMessageProvider.getInstance().encode("myprofile");
        request.setAttribute("myprofile", myProfile);

        String mainTitle = LocaleMessageProvider.getInstance().encode("title");
        request.setAttribute("title", mainTitle);

        String picture = LocaleMessageProvider.getInstance().encode("picture");
        request.setAttribute("picture", picture);

        String nameIssue = LocaleMessageProvider.getInstance().encode("name_issue");
        request.setAttribute("name_issue", nameIssue);
    }
}
