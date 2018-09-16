package com.sy.huangniao.common.Util;

import java.io.File;

/**
 * MD5校验工具类
 */
public class MD5Utils {

    /**
     * MD5加密字符串
     *
     * @param str 目标字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5String(String str) {
        return EncryptUtils.md5(str);
    }

    /**
     * 校验密码与其MD5是否一致
     *
     * @param pwd 密码字符串
     * @param md5 基准MD5值
     * @return 检验结果
     */

    public static boolean checkPassword(String pwd, String md5) {
        return EncryptUtils.md5(pwd).equalsIgnoreCase(md5);
    }

    /**
     * 校验密码与其MD5是否一致
     *
     * @param pwd 以字符数组表示的密码
     * @param md5 基准MD5值
     * @return 检验结果
     */

    public static boolean checkPassword(char[] pwd, String md5) {
        return checkPassword(new String(pwd), md5);

    }

    /**
     * 检验文件的MD5值
     *
     * @param file 目标文件
     * @param md5  基准MD5值
     * @return 检验结果
     */

    public static boolean checkFileMD5(File file, String md5) {
        return EncryptUtils.md5(file).equalsIgnoreCase(md5);

    }

    /**
     * 检验文件的MD5值
     *
     * @param fileName 目标文件的完整名称
     * @param md5      基准MD5值
     * @return 检验结果
     */

    public static boolean checkFileMD5(String fileName, String md5) {
        return checkFileMD5(new File(fileName), md5);
    }

}
