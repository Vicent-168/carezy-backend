package com.shdata.health.carezy.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final ZoneId zoneId = ZoneId.systemDefault();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 判断给定的日期是否为周一
     * @param date 日期对象
     * @return 如果是周一返回 true，否则返回 false
     */
    public static boolean isMonday(Date date) {
        LocalDate localDate = date.toInstant().atZone(zoneId).toLocalDate();
        return localDate.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    /**
     * 将 Date 对象转换为只包含日期部分的 Date 对象
     * @param originalDate 原始 Date 对象
     * @return 只包含日期部分的 Date 对象
     */
    public static Date getTimeOfDay(Date originalDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    //获取昨天日期
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 将业务日期字符串转换为 Date 类型，并往前推一天时间
     * @param ywrq 业务日期字符串
     * @return 转换后的 Date 对象，并往前推一天
     * @throws ParseException 如果解析失败
     */
    public static Date getBusinessDateMinusOneDay(String ywrq) throws ParseException {
        // 解析字符串日期
        Date businessDate = dateFormat.parse(ywrq);

        // 创建 Calendar 实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(businessDate);  // 设置 Calendar 时间为解析得到的日期

        // 往前推一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // 返回往前推一天的日期
        return calendar.getTime();
    }

    /**
     * 获取距今 N 天前的开始时刻（零点时刻），并返回字符串格式
     * @param currentDate 当前日期（java.util.Date类型）
     * @param daysBeforeStr 距今的天数（字符串形式）
     * @return 距今 N 天前的开始时刻的字符串表示
     * @throws NumberFormatException 如果 daysBeforeStr 不能解析为 long
     */
    public static String getStartOfNDaysAgo(Date currentDate, String daysBeforeStr) throws NumberFormatException {
        long daysBefore = Long.parseLong(daysBeforeStr);

        // 将 java.util.Date 转换为 LocalDate
        LocalDate localDate = currentDate.toInstant().atZone(zoneId).toLocalDate();

        // 计算 N 天前的日期
        LocalDate nDaysAgo = localDate.minusDays(daysBefore);

        // 获取 N 天前的零点时刻
        java.time.LocalDateTime startOfDay = nDaysAgo.atStartOfDay();

        // 将 LocalDateTime 转换回 java.util.Date
        Date date = Date.from(startOfDay.atZone(zoneId).toInstant());

        // 将日期格式化为字符串
        return dateFormat.format(date);
    }



    // 获取当前日期
    public static Date getCurrentDate() {
        return Date.from(LocalDate.now().atStartOfDay(zoneId).toInstant());
    }

    // 获取本周一的零点时间
    public static Date getStartOfWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        return Date.from(startOfWeek.atZone(zoneId).toInstant());
    }

    // 获取当前日期前一日零点时刻
    public static Date getStartOfYesterday() {
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime yesterdayStart = todayStart.minusDays(1);
        return Date.from(yesterdayStart.atZone(zoneId).toInstant());
    }

    // 获取昨日结束时刻 (即昨日的 23:59:59.999)
    public static Date getEndOfYesterday() {
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime yesterdayEnd = todayStart.minusSeconds(1);
        return Date.from(yesterdayEnd.atZone(zoneId).toInstant());
    }

    // 获取今日零点的时间
    public static Date getStartOfDay() {
        return Date.from(LocalDate.now().atStartOfDay(zoneId).toInstant());
    }

    // 获取当前时间
    public static Date getCurrentTime() {
        return Date.from(LocalDateTime.now().atZone(zoneId).toInstant());
    }

    // 获取当前日期的上周日的日期
    public static Date getLastSunday() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastSunday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        return Date.from(lastSunday.atStartOfDay(zoneId).toInstant());
    }

    // 将 Date 转换为字符串
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    // 将 Date 转换为时间戳字符串
    public static String dateTimeToString(Date date) {
        if (date == null) {
            return null;
        }
        return dateTimeFormat.format(date);
    }

    // 将字符串转换为 Date
    public static Date stringToDate(String dateString) {
        try {
            return dateString == null ? null : dateFormat.parse(dateString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }

    // 将时间戳字符串转换为 Date
    public static Date stringToDateTime(String dateTimeString) {
        try {
            return dateTimeString == null ? null : dateTimeFormat.parse(dateTimeString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date time format: " + dateTimeString, e);
        }
    }

}
