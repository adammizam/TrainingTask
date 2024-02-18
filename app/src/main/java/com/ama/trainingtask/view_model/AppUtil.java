package com.ama.trainingtask.view_model;

import java.util.Random;

public class AppUtil {

    /**
     * Generate a random string and the length
     * @param sizeOfRandomString is int provided
     * @return String values
     */
    public static final String ALLOWED_CHARACTERS ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
