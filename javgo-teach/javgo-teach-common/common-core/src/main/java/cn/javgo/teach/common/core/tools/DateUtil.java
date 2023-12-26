package cn.javgo.teach.common.core.tools;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 日期处理工具类
 *
 * @author javgo.cn
 * @date 2023/12/26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {

    /**
     * 时间转换：时间戳转为指定格式时间字符串
     *
     * @param format 格式
     * @param time   时间戳
     * @return 格式化后的时间字符串
     */
    public static String formatToString(String format, long time) {
        if (time == 0) {
            return "";
        }
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * 时间转换：指定格式时间字符串转为当前时间戳
     *
     * @param format 格式
     * @return 时间戳
     */
    public static long formatToLong(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return Timestamp.valueOf(sdf.format(new Date())).getTime();
    }

    /**
     * 时间转换：字符串转日期
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return 日期
     */
    public static Date parseDate(String dateStr, String format) {
        if (Objects.isNull(dateStr) || Objects.isNull(format) || format.isEmpty()) {
            return null;
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 时间转换：字符串转日期(默认格式：yyyy-MM-dd)
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 时间转换：日期转字符串
     *
     * @param date   日期
     * @param format 格式
     * @return 日期字符串
     */
    public static String format(Date date, String format) {
        if (Objects.isNull(date)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 时间转换：日期转字符串(默认格式：yyyy-MM-dd)
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 时间获取：获取当前年份
     *
     * @return 年份
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 时间获取：获取指定日期的年份
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 时间获取：获取指定日期的月份
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 月份从0开始
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 时间获取：获取当前月份
     *
     * @return 月份
     */
    public static String getMonth() {
        Calendar cal = Calendar.getInstance();
        return new DecimalFormat("00").format(cal.get(Calendar.MONTH));
    }

    /**
     * 时间获取：获取指定日期的天数（每个月的天数）
     *
     * @param date 日期
     * @return 天数
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 时间获取：获取指定日期的小时
     *
     * @return 小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 时间获取：获取指定日期的分钟
     *
     * @return 分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 时间获取：获取指定日期的秒数
     *
     * @return 秒数
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 时间获取：获取指定日期的毫秒数
     *
     * @return 毫秒数
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 时间获取：获取指定日期的字符串格式（yyyy-MM-dd）
     *
     * @return 日期字符串
     */
    public static String getDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 时间获取：获取指定日期的字符串格式（HH:mm:ss）
     *
     * @return 日期字符串
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 时间获取：获取指定日期的字符串格式（yyyy-MM-dd HH:mm:ss）
     *
     * @return 日期字符串
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间获取：获取指定日期的第一天
     *
     * @param dateStr 日期字符串
     * @return 日期字符串
     */
    public static String getMonthBegin(String dateStr) {
        Date date = parseDate(dateStr);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 时间获取：获取指定日期的最后一天
     *
     * @param dateStr 日期字符串
     * @return 日期字符串
     */
    public static String getMonthEnd(String dateStr) {
        Date date = parseDate(getMonthBegin(dateStr));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 时间转换：以指定格式转换日期（常用格式：yyyy-MM-dd）
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 时间转换：以指定格式转换日期
     *
     * @param date   日期
     * @param format 格式
     * @return 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (Objects.nonNull(date)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 时间计算：日期加天数
     *
     * @param date 日期
     * @param day  天数
     * @return 日期
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 时间计算：日期加天数
     *
     * @param dateStr 日期字符串
     * @param day     天数
     * @return 日期
     */
    public static String addDate(String dateStr, int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            long date = simpleDateFormat.parse(dateStr).getTime();
            long millis = date + ((long) day) * 24 * 3600 * 1000;
            return simpleDateFormat.format(new Date(millis));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 时间计算：日期相差的毫秒数
     *
     * @param date1 日期
     * @param date2 日期
     * @return 毫秒数
     */
    public static int diffDate(Date date1, Date date2) {
        return (int) ((getMillis(date1) - getMillis(date2)) / (24 * 3600 * 1000));
    }

    /**
     * 时间计算：日期加年数
     *
     * @param date 日期
     * @param year 年数
     * @return 日期
     */
    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 时间计算：日期相差的天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 天数
     */
    public static int dayDateDiff(String beginDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(beginDate);
            Date end = sdf.parse(endDate);
            long between = end.getTime() - start.getTime();
            long day = between / (24 * 60 * 60 * 1000);
            return Integer.parseInt(String.valueOf(day));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 时间计算：日期相差的年数
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     * @return 年数
     */
    public static int yearDateDiff(String startYear, String endYear) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startYear);
            Date end = sdf.parse(endYear);
            long between = end.getTime() - start.getTime();
            long day = between / (24 * 60 * 60 * 1000);
            return Integer.parseInt(String.valueOf(day / 365));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 时间计算：获取上个月
     *
     * @return 日期
     */
    public static DateTime lastMonth() {
        return offsetMonth(new DateTime(), -1);
    }

    /**
     * 时间计算：日期偏移
     *
     * @param date   日期
     * @param offset 偏移量
     * @return 日期
     */
    public static DateTime offsetMonth(Date date, int offset) {
        return offset(date, DateField.MONTH, offset);
    }

    /**
     * 时间计算：日期偏移
     *
     * @param date      日期
     * @param dateField 偏移类型
     * @param offset    偏移量
     * @return 日期
     */
    public static DateTime offset(Date date, DateField dateField, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(dateField.getValue(), offset);
        return new DateTime(cal.getTime());
    }

    /**
     * 获取指定日期的开始时间（即当天的 0 时 0 分 0 秒）
     *
     * @param date 日期
     * @return 该日期的开始时间
     */
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的结束时间（即当天的 23 时 59 分 59 秒）
     *
     * @param date 日期
     * @return 该日期的结束时间
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

}
