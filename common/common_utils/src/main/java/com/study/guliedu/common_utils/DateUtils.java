package com.study.guliedu.common_utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author liao.peng
 * @since 2021/7/5 14:40
 */
public class DateUtils {

    /**
     * 解析成日期字符串
     * @param localDate
     * @param pattern
     * @return
     */
    public static String parseDate(LocalDateTime localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(dateTimeFormatter);
    }

}
