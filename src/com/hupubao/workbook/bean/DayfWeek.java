package com.hupubao.workbook.bean;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * <h1>星期</h1>
 * @author Seichii.wei
 * @date 2019-12-10 09:41:39
 */
public class DayfWeek {

    private String day;
    private int value;

    public DayfWeek() {
    }

    public DayfWeek(LocalDateTime localDateTime) {
        this.day = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
        this.value = localDateTime.getDayOfWeek().getValue();
    }


    public DayfWeek(String day, int value) {
        this.day = day;
        this.value = value;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.day;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }


        return value == ((DayfWeek) obj).value;
    }
}
