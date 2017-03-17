package com.cxh.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {
    private static final String YAN = "iuqewhurbasd9f6q345896t132y9a6sd96f34h59gsd9fq3";

    /**
     * 文件加密
     */
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            password = password + YAN;
            byte[] encrypts = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : encrypts) {
                int num = b & 0xff;
                String str = Integer.toHexString(num);
                if (str.length() == 1) {
                    str = "0" + str;
                }
                sb.append(str);
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算指定文件的MD5值
     */
    public static String getFileMd5(String path) {
        StringBuilder sb = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            FileInputStream fis = new FileInputStream(new File(path));
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            byte[] encrypts = md.digest();
            sb = new StringBuilder();
            for (byte b : encrypts) {
                int num = b & 0xff;
                String str = Integer.toHexString(num);
                if (str.length() == 1) {
                    str = "0" + str;
                }
                sb.append(str);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
