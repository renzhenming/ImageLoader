package com.rzm.imageloader.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static String toMD5(String sbs) {
        if (TextUtils.isEmpty(sbs)){
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(sbs.getBytes());
            return toHexString(digest.digest(), "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toHexString(byte[] bytes, String separtor) {
        if (bytes == null || bytes.length <= 0){
            return null;
        }
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex).append(separtor);
        }
        return hexString.toString();
    }
}
