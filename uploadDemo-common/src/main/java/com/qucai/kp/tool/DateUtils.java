package com.qucai.kp.tool;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {

    /** Default locale is CHINA */
    public static final Locale DEFAULT_LOCALE = Locale.CHINA;

    public final static String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";

    public final static String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";

    public final static String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";

    public final static String FORMAT_DATE_PATTERN_1 = "yyyy/MM/dd";

    public final static String FORMAT_DATE_PATTERN_2 = "yyyy/M/dd";

    public final static String FORMAT_DATE_PATTERN_3 = "yyyy/MM/d";

    public final static String FORMAT_DATE_PATTERN_4 = "yyyy/M/d";

    public final static String FORMAT_DATE_YYYY_MM_DD_HHMMSS = "yyyyMMddHHmmss";

    public final static String FORMAT_DATE_YYYY_MM_DD_HHMMSSSSS = "yyyyMMddHHmmssSSS";

    public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public final static String FORMAT_DATE_MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";

    public final static String FORMAT_DATE_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";

    public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public final static String FORMAT_DATE_HH_MM = "HH:mm";

    public final static String FORMAT_DATE_HH_MM_SS = "HH:mm:ss";

    public final static String FORMAT_DATE_HHMM = "HHmm";

    public final static String FORMAT_DATE_HHMMSS = "HHmmss";

    public static final String FORMAT_DATE_YYMMDD = "yyMMdd";

    public static final String FORMAT_WORK_TIME = "yyyy-MM-dd HHmmss";

    public static final String FORMAT_DATE_YYYY_MM_DDHHMMSS = "yyyy-MM-ddHHmmss";

    public static final String FORMAT_DATE_YYYYMMDD_BLANK_HHMMSS = "yyyyMMdd HH:mm:ss";

    /**
     * 获取月份
     */
    public static final String FORMAT_DATE_MM = "MM";

    /**
     * 获取年份
     */
    public static final String FORMAT_DATE_YYYY = "yyyy";

    public static final String FORMAT_DATE_YYYY_MM = "yyyy-MM";

    public final static int compareDate(String stringValue1, String stringValue2) throws ParseException {
        Date date1 = tryParse(stringValue1);
        if (date1 == null)
            throw new ParseException("Can not parse " + stringValue1 + " to Date.", 0);
        Date date2 = tryParse(stringValue2);
        if (date2 == null)
            throw new ParseException("Can not parse " + stringValue1 + " to Date.", 0);
        return date1.compareTo(date2);
    }

    /*
     * 给定时间于当期时间的比较，差值为分钟
     */
    public final static long compareDateToMin(String stringValue1) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now = Calendar.getInstance().getTime();
        java.util.Date date = df.parse(stringValue1);
        long l = now.getTime() - date.getTime();

        return l / (60 * 1000);

    }

    /**
     * Returns current system date as formatted string value with default format
     * pattern.
     * 
     * @return current system date.
     * 
     * @see #FORMAT_DATE_DEFAULT
     */
    public final static String getCurrentDateAsString() {
        return getCurrentDateAsString(FORMAT_DATE_DEFAULT);
    }

    /**
     * Returns current system date as formatted string value with given format
     * pattern.
     * 
     * @param formatPattern
     *            format pattern.
     * @return current system date.
     * 
     */
    public final static String getCurrentDateAsString(String formatPattern) {
        Date date = getCurrentDate();
        return format(date, formatPattern);
    }

    /**
     * Returns current system date.
     * 
     * @return current system date.
     */
    public final static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Format Date value as string value with default format pattern.
     * 
     * @param date
     *            Date value.
     * @return formatted date as string value.
     * 
     * @see #FORMAT_DATE_DEFAULT
     */
    public final static String format(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, FORMAT_DATE_DEFAULT);
    }

    /**
     * Format Date value as string value with default format pattern.
     * 
     * @param date
     *            Date value.
     * @return formatted date as string value.
     * 
     * @see #FORMAT_DATE_DEFAULT
     */
    public final static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * Format Date value as string value with default format pattern.
     * 
     * @param date
     *            Date value.
     * @return formatted date as string value.
     * 
     * @see #FORMAT_DATE_DEFAULT
     */
    public final static String formatTimestamp(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * Format Date value as string value with default format pattern.
     * 
     * @param date
     *            Date value.
     * @return formatted date as string value.
     * 
     * @see #FORMAT_DATE_DEFAULT
     */
    public final static Date parseTimestamp(String date) {
        if (date == null) {
            return null;
        }
        return parse(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * Format Date value as string value with given format pattern.
     * 
     * @param date
     *            Date value.
     * @param formatPattern
     *            format pattern.
     * @return formatted date as string value.
     * 
     * @see #FORMAT_DATE_DEFAULT
     * @see #FORMAT_DATE_YYYY_MM_DD
     * @see #FORMAT_DATE_YYYY_MM_DD_HH_MM
     * @see #FORMAT_DATE_YYYY_MM_DD_HH_MM_SS
     * @see #FORMAT_DATE_YYYY_MM_DD_HHMMSS
     */
    public final static String format(Date date, String formatPattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(formatPattern).format(date);
    }

    /**
     * Parse string value to Date with default format pattern.
     * 
     * @param stringValue
     *            date value as string.
     * @return Date represents stringValue.
     * @see #FORMAT_DATE_DEFAULT
     */
    public final static Date parse(String stringValue) {
        return parse(stringValue, FORMAT_DATE_DEFAULT);
    }

    /**
     * Parse string value to Date with given format pattern.
     * 
     * @param stringValue
     *            date value as string.
     * @param formatPattern
     *            format pattern.
     * @return Date represents stringValue, null while parse exception occurred.
     * @see #FORMAT_DATE_DEFAULT
     */
    public final static Date parse(String stringValue, String formatPattern) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        try {
            return format.parse(stringValue);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * Try to parse string value to date.
     * 
     * @param stringValue
     *            string value.
     * @return Date represents stringValue, null while parse exception occurred.
     */
    public final static Date tryParse(String stringValue) {
        Date date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_YYYYMMDD);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD_HHMMSS);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD_HHMM);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_PATTERN_1);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_PATTERN_2);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_PATTERN_3);
        if (date != null) {
            return date;
        }
        date = parse(stringValue, FORMAT_DATE_PATTERN_4);
        if (date != null) {
            return date;
        }
        return date;
    }

    /**
     * get day of week
     * 
     * @param SUN_FST_DAY_OF_WEEK
     * @return
     */
    public static int getDayOfWeek(int SUN_FST_DAY_OF_WEEK) {
        if (SUN_FST_DAY_OF_WEEK > 7 || SUN_FST_DAY_OF_WEEK < 1)
            return 0;
        if (SUN_FST_DAY_OF_WEEK == 1)
            return 7;
        return SUN_FST_DAY_OF_WEEK - 1;
    }

    public static Timestamp parseTimestamp(String stringValue, String formatPattern) {
        return new Timestamp(parse(stringValue, formatPattern).getTime());
    }

    public static Timestamp parseTimestamp(Date d) {
        return new Timestamp(d.getTime());
    }

    // -----------------------------------------------------------------------
    /**
     * Adds a number of milliseconds to a date returning a new object. The
     * original date object is unchanged.
     * 
     * @param date
     *            the date, not null
     * @param amount
     *            the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException
     *             if the date is null
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    /**
     * Adds a number of milliseconds to a date returning a new object. The
     * original date object is unchanged.
     * 
     * @param date
     *            the date, not null
     * @param amount
     *            the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException
     *             if the date is null
     */
    public static Date addMilliseconds(Date date, long amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime() + amount);
        return c.getTime();
    }

    // -----------------------------------------------------------------------
    /**
     * Adds a number of minutes to a date returning a new object. The original
     * date object is unchanged.
     * 
     * @param date
     *            the date, not null
     * @param amount
     *            the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException
     *             if the date is null
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    // -----------------------------------------------------------------------
    /**
     * Adds to a date returning a new object. The original date object is
     * unchanged.
     * 
     * @param date
     *            the date, not null
     * @param calendarField
     *            the calendar field to add to
     * @param amount
     *            the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException
     *             if the date is null
     * 
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * <p>
     * Checks if two date objects are on the same day ignoring time.
     * </p>
     * 
     * <p>
     * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
     * 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     * 
     * @param date1
     *            the first date, not altered, not null
     * @param date2
     *            the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException
     *             if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if two calendar objects are on the same day ignoring time.
     * </p>
     * 
     * <p>
     * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
     * 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     * 
     * @param cal1
     *            the first calendar, not altered, not null
     * @param cal2
     *            the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException
     *             if either calendar is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
                .get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * @Title: getFirstDayOfWeek
     * @Description: 取得指定日期所在周的第一天
     * @param date
     * @return Date
     * @author yangzhi
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * @Title: addWeekDayOfWeek
     * @Description: 在指定的日期上+指定的周
     * @param currentDate
     * @param week
     * @return Date
     * @author yangzhi
     */
    public static Date addWeekDayOfWeek(Date currentDate, int week) {
        Calendar c = new GregorianCalendar();
        c.setTime(currentDate);
        c.add(Calendar.WEEK_OF_YEAR, week);
        return c.getTime();
    }

    /**
     * 比较两个日期的差值
     * @param d1
     * @param d2
     * @return
     */
    public static int getDaysBetweenDate(Date date1, Date date2) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(date1);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(date2);
        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = d2.get(java.util.Calendar.YEAR);
        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    }

    public final static int getCurrentAge(Date dateOfBirth) {
        Calendar now = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(dateOfBirth);
        if (now.get(Calendar.YEAR) > birthday.get(Calendar.YEAR)) {
            if (now.get(Calendar.MONTH) > birthday.get(Calendar.MONTH)) {
                return now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
            } else if (now.get(Calendar.MONTH) == birthday.get(Calendar.MONTH)) {
                if (now.get(Calendar.DAY_OF_MONTH) >= birthday.get(Calendar.DAY_OF_MONTH)) {
                    return now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
                } else {
                    return now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR) - 1;
                }
            } else {
                return now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR) - 1;
            }
        } else {
            return 1;
        }
    }

    /**
     * 获取多少天前，或后
     * @author yhy 2017年6月29日
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * Main method for test.
     * 
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        String stringValue = "2015-05-22 11:04:55";
        System.out.println("Parse \"" + stringValue + "\" using format pattern \"" + DateUtils.FORMAT_DATE_YYYYMMDD_BLANK_HHMMSS
                + "\" with method \"DateUtils.parse()\", result: " + DateUtils.parse(stringValue));
        stringValue = "20080506";
        System.out.println("Parse \"" + stringValue + "\" using method \"DateUtils.tryParse()\", result: "
                + DateUtils.tryParse(stringValue));
        Date d = DateUtils.tryParse(stringValue);
        String s = DateUtils.format(d, DateUtils.FORMAT_DATE_YYYYMMDD_BLANK_HHMMSS);
        System.out.print("--->>>" + s);
    }

}
