package com.example.licenseebe.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomValidators {
    public boolean isValidName(String name) {
        return name.matches("^([ \\u00c0-\\u01ffa-zA-Z'\\-])+$");
    }

    public boolean isEmailValid(String email) {
        String ePattern = "[A-Za-z\\d.]*@[A-Za-z\\d.]*.[A-Za-z\\d._]";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email.trim());
        return m.matches();
    }
}
