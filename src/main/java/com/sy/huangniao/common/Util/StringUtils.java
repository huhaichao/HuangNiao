package com.sy.huangniao.common.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String助手类
 *
 **/
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Pattern IP_PATTERN = Pattern.compile(
        "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|"
            + "(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|"
            + "([1-9]\\d)|(\\d))");

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))"
            + "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

    private static final Pattern MOBILE_PATTERN = Pattern.compile(
        "^((176)|(13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

    /**
     * ip校验
     *
     * @param s
     * @return 格式是否正确
     */
    public static boolean isIpAddress(final String s) {
        final Matcher m = IP_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * email校验
     *
     * @param email
     * @return 格式是否正确
     */
    public static boolean isEmail(final String email) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(email)) {
            return false;
        }
        final Matcher m = EMAIL_PATTERN.matcher(email);
        return m.matches();
    }

    /**
     * 手机号校验
     *
     * @param mobiles
     * @return 格式是否正确
     */
    public static boolean isMobile(final String mobiles) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(mobiles)) {
            return false;
        }
        final Matcher m = MOBILE_PATTERN.matcher(mobiles);
        return m.matches();
    }

    /**
     * 脱敏手机号
     * @param phoneNo
     * @return
     */
    public  static  String handlePhoneNo(String  phoneNo){
        if (org.apache.commons.lang3.StringUtils.isEmpty(phoneNo)) {
            return null;
        }
        StringBuilder sb  = new StringBuilder(phoneNo);
        sb.replace(3,8,"*****");
        return  sb.toString();
    }

    /**
     * 脱敏身份证号码
     * @param identity
     * @return
     */
    public static  String handleIdentity(String identity){
        if (org.apache.commons.lang3.StringUtils.isEmpty(identity)) {
            return null;
        }
        StringBuilder sb  = new StringBuilder(identity);
        sb.replace(3,14,"******");
        return  sb.toString();
    }

    /**
     * 脱敏姓名
     * @param realName
     * @return
     */
    public static  String handleRealName(String realName){
        if (org.apache.commons.lang3.StringUtils.isEmpty(realName)) {
            return null;
        }
        StringBuilder sb  = new StringBuilder(realName);
        sb.replace(0,2,"*");
        return  sb.toString();
    }


    /**
     * 转换成整数
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    public static int toInt(final String str, final int defaultValue) {
        try {
            if (str == null) { return defaultValue; }
            return Integer.parseInt(str.trim());
        } catch (final Exception ex) {
            return defaultValue;
        }
    }

    /**
     * 转换成整数
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    public static long toLong(final String str, final long defaultValue) {
        try {
            if (str == null) { return defaultValue; }
            return Long.parseLong(str.trim());
        } catch (final Exception ex) {
            return defaultValue;
        }
    }

    public static Float toFloat(final String s, final Float defaultValue) {
        if (s == null || "".equals(s.trim()) || "Infinity".equals(s.trim()) || "NaN".equals(s.trim())) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(s.trim());
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    public static Double toDouble(final String s, final Double defaultValue) {

        if (s == null || "".equals(s.trim()) || "Infinity".equals(s.trim()) || "NaN".equals(s.trim()) || "null".equals(
            s)) { return defaultValue; }
        try {
            return Double.parseDouble(s.trim());
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    /**
     * 迁移原来的代码
     * 将字符串格式化成 HTML 代码输出
     * 只转换特殊字符，适合于 HTML 中的表单区域
     *
     * @param str 要格式化的字符串
     * @return 过滤后的字符串
     */
    public static String inputToHtml(String str) {
        if (isEmpty(str)) {
            return str;
        }
        str = replaceStr(str, "<", "&lt;");
        str = replaceStr(str, ">", "&gt;");
        str = replaceStr(str, "/", "&#47;");
        str = replaceStr(str, "\\", "&#92;");
        str = replaceStr(str, "\r\n", "<br/>");
        str = replaceStr(str, " ", "&nbsp;");
        str = replaceStr(str, "'", "&acute;");
        str = replaceStr(str, "\"", "&quot;");
        str = replaceStr(str, "(", "&#40;");
        str = replaceStr(str, ")", "&#41;");
        //str = replaceStr(str,".","&#46;");
        return str;
    }

    /**
     * 迁移原来的代码
     * 字符串替换，将 source 中的 oldString 全部换成 newString
     *
     * @param source    源字符串
     * @param oldString 老的字符串
     * @param newString 新的字符串
     * @return 替换后的字符串
     */
    private static String replaceStr(final String source, final String oldString, final String newString) {
        final StringBuffer output = new StringBuffer();

        final int lengthOfSource = source.length();   // 源字符串长度
        final int lengthOfOld = oldString.length();   // 老字符串长度

        int posStart = 0;   // 开始搜索位置
        int pos;            // 搜索到老字符串的位置

        final String lower_s = source.toLowerCase();        //不区分大小写
        final String lower_o = oldString.toLowerCase();

        while ((pos = lower_s.indexOf(lower_o, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));

            output.append(newString);
            posStart = pos + lengthOfOld;
        }

        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }

        return output.toString();
    }

    public static String getDomainFromUrl(final String url) {
        final String regEx = "(https?://[^/]+)/.*";
        final Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 将字符串格式化后 HTML 代码转成原始字符输出
     * 只有html编辑器中修改或输入表单中的修改需要调此函数
     *
     * @param str 格式化后的字符串
     * @return 原始的字符串
     */

    public static String outputToOrgStr(String str) {
        if (StringUtils.isEmpty(str)) { return null; }
        str = replaceStr(str, "&lt;", "<");
        str = replaceStr(str, "&gt;", ">");
        str = replaceStr(str, "&#47;", "/");
        str = replaceStr(str, "&#92;", "\\");
        str = replaceStr(str, "<br/>", "\r\n");
        str = replaceStr(str, "&nbsp;", " ");
        str = replaceStr(str, "&acute;", "'");
        str = replaceStr(str, "&quot;", "\"");
        //str = replaceStr(str, "&#46;",".");
        str = replaceStr(str, "&#40;", "(");
        str = replaceStr(str, "&#41;", ")");

        //	    str = replaceStr(str, "<br>","\r\n");
        //	    str = replaceStr(str, "&cedil;",".");
        //	    str = replaceStr(str, "    ","\t" );
        return str;
    }

    public static void main(final String[] args) {
        System.out.println(toInt("a", 1));
        System.out.println(toInt("2", 1));
    }
}
