package com.anthony.frameimageeffect.util;

import android.util.Patterns;

import java.util.regex.Matcher;

/**
 * Created by jerry on 24/02/16.
 */
public class ValidationUtils {
    public static boolean validateEmail(final String email) {
        Matcher mMatcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return mMatcher.matches();
    }
    public static boolean isEmpty(String str) {
        boolean isEmpty = false;
        if (str == null || str.trim().length() == 0 || ("null").equalsIgnoreCase(str)) {
            isEmpty = true;
        }
        return isEmpty;
    }
}
