package com.system.technologyinformation.utils;


import android.text.TextUtils;

import java.security.MessageDigest;

/**
     * MD5通用类
     *
     *
     */
    public class MD5Util {

        public static String md5(String text)  {
            if (TextUtils.isEmpty(text)) {
                return "";
            }
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
                byte[] bytes = md5.digest(text.getBytes());
                String result = "";
                for (byte b : bytes) {
                    String temp = Integer.toHexString(b & 0xff);
                    if (temp.length() == 1) {
                        temp = "0" + temp;
                    }
                    result += temp;
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

