package com.sy.huangniao.common.Util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.File;

/**
 * 加密工具类
 *
 */
public class EncryptUtils {

    /**
     * 计算字符串的md5值
     *
     * @param content
     * @return
     */
    public static String md5(CharSequence content) {
        return Hashing.md5().hashString(content, Charsets.UTF_8).toString().toUpperCase();
    }

    /**
     * 计算字符串的sha值
     *
     * @param content
     * @return
     */
    public static String sha1(CharSequence content) {
        return Hashing.sha1().hashString(content, Charsets.UTF_8).toString().toUpperCase();
    }

    /**
     * 计算文件的md5值
     *
     * @param file
     * @return
     */
    public static String md5(File file) {
        try {
            Preconditions.checkNotNull(file, "file is null");
            Preconditions.checkArgument(file.exists(), "file is not exist,absolute path = " + file.getAbsolutePath());
            return Files.hash(file, Hashing.md5()).toString().toUpperCase();
        } catch (Exception ex) {
            throw new RuntimeException("md5 file error", ex);
        }
    }

    /**
     * 计算文件的crc32值
     *
     * @param file
     * @return
     */
    public static String crc32(File file) {
        try {
            Preconditions.checkNotNull(file, "file is null");
            Preconditions.checkArgument(file.exists(), "file is not exist,absolute path = " + file.getAbsolutePath());
            return Files.hash(file, Hashing.crc32()).toString().toUpperCase();
        } catch (Exception ex) {
            throw new RuntimeException("crc32 file error", ex);
        }
    }

    public static void main(String[] args) {
        System.out.println(md5("abc"));
        System.out.println(sha1("abc"));
    }
}
