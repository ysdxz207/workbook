package com.hupubao.workbook.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 
 * @author Moses
 * @date 2017-11-09 16:12
 * 
 */
public class DateUtils {

    private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String getNowDateStr() {
        return DateFormatUtils.format(new Date(), PATTERN_DATE);
    }

    public static String getNowDateTimeStr() {
        return DateFormatUtils.format(new Date(), PATTERN_DATE_TIME);
    }
}
