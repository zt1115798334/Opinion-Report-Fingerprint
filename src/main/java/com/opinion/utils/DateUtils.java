package com.opinion.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>Title: DateUtils</p>
 * <p>Description: 日期工具类</p>
 *
 * @author zhangtong
 * @date 2017年4月6日
 */
public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {
    }


    public static LocalDate currentDate() {
        LocalDate now = LocalDate.now();
        return now;
    }

    public static LocalDateTime currentDatetime() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }
    /**
     * <p>
     * Description: 解析yyyy-MM-dd HH:mm:ss格式的日期字符串，返回日期对象
     * </p>
     *
     * @param date
     * @return
     */
    public static LocalDate parseDate(String date) {
        return parseDate(date, DATE_FORMAT);
    }

    /**
     * <p>
     * Description: 使用指定的日期格式解析日期字符串，返回日期对象
     * </p>
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static LocalDate parseDate(String date, String dateFormat) {
        LocalDate result = null;
        if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(dateFormat)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            result = LocalDate.parse(date, formatter);
        }
        return result;
    }

    /**
     * <p>
     * Description: 使用指定的日期格式解析日期字符串，返回日期对象
     * </p>
     *
     * @param dateTime
     * @param dateFormat
     * @return
     */
    public static LocalDateTime parseDatetime(String dateTime, String dateFormat) {
        LocalDateTime result = null;
        if (StringUtils.isNotEmpty(dateTime) && StringUtils.isNotEmpty(dateFormat)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            result = LocalDateTime.parse(dateTime, formatter);
        }
        return result;
    }

    /**
     * <p>Description: 将日期对象格式化为yyyy-MM-dd格式的字符串</p>
     *
     * @param date
     * @return
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * <p>Description: 将日期对象格式化为yyyy-MM-dd格式的字符串</p>
     *
     * @param dateTime
     * @return
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return formatDate(dateTime, DATE_FORMAT);
    }
    /**
     * <p>
     * Description: 使用指定的格式格式化日期对象，返回格式化后的日期字符串
     * </p>
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDate(LocalDate date, String dateFormat) {
        String result = null;
        if (date != null && StringUtils.isNotEmpty(dateFormat)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            result = date.format(formatter);
        }
        return result;
    }

    /**
     * <p>
     * Description: 使用指定的格式格式化日期对象，返回格式化后的日期字符串
     * </p>
     *
     * @param dateTime
     * @param dateFormat
     * @return
     */
    public static String formatDate(LocalDateTime dateTime, String dateFormat) {
        String result = null;
        if (dateTime != null && StringUtils.isNotEmpty(dateFormat)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            result = dateTime.format(formatter);
        }
        return result;
    }

    /**
     * 时分秒归零
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime dateTimeToZero(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = LocalTime.of(0, 0, 0);
        return date.atTime(time);
    }

    /**
     * 当前日期前七天日期
     *
     * @return
     */
    public static LocalDateTime currentDateBeforeSevenDays() {
        LocalDateTime dateTimeOfSevenDays = currentDateAddDay(-6);
        return dateTimeToZero(dateTimeOfSevenDays);
    }

    /**
     * 当前日期前14天日期
     *
     * @return
     */
    public static LocalDateTime currentDateBeforeFourteenDays() {
        LocalDateTime dateTimeOfSevenDays = currentDateAddDay(-13);
        return dateTimeToZero(dateTimeOfSevenDays);
    }

    /**
     * 当前日期加上天数
     *
     * @param day
     * @return
     */
    public static LocalDateTime currentDateAddDay(long day) {
        return dateTimeAddDay(day, currentDatetime());
    }

    /**
     * 日期加上天数
     *
     * @param day
     * @return
     */
    public static LocalDateTime dateTimeAddDay(long day, LocalDateTime dateTime) {
        return dateTime.plusDays(day);
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = currentDateBeforeFourteenDays();
        System.out.println("localDateTime = " + localDateTime);
    }
}
