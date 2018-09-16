package com.sy.huangniao.common.Util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huchao on 2018/9/14.
 */
public class DateUtils {

    public static Date getDateInMinuteAgo(final Date date, final int minute) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    public static Date getDateInDayAgo(final Date date, final int dateNum) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, dateNum);
        return cal.getTime();
    }

    public static Date string2dateUtil(final String dateStr, final String format) {
        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(dateStr, new ParsePosition(0));
    }

    public static String date2StringUtil(final Date date, final String format) {
        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
