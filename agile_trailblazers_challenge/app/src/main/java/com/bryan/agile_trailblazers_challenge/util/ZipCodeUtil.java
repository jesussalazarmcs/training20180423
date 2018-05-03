package com.bryan.agile_trailblazers_challenge.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipCodeUtil {

    public static boolean isZipCode(String zipcode) {
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(zipcode);
        return matcher.matches();
    }
}
