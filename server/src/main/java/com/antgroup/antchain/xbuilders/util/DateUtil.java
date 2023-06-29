package com.antgroup.antchain.xbuilders.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param time
     * @return
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期的秒
     *
     * @param time
     * @return
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式
     *
     * @param pattern
     * @return
     */
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     *
     * @param startTime
     * @param endTime
     * @param field     单位(年月日时分秒)
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取一天的开始时间，2020,7,22 00:00:00
     *
     * @param time
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * 获取一天的结束时间，2020,7,22 23:59:59.999999999
     *
     * @param time
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return getDayStart(time.plusDays(1)).minusNanos(1);
    }

}
