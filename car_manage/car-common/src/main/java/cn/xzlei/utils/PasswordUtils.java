package cn.xzlei.utils;


import cn.hutool.crypto.digest.DigestUtil;

public class PasswordUtils {

    // 密码盐
    static final String SALT = "haoche";

    public static String encode(String password) {
        return DigestUtil.md5Hex(password+SALT);
    }
}
