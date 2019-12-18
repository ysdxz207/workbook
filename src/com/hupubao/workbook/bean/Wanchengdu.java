package com.hupubao.workbook.bean;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class Wanchengdu {

    private Integer percent = 20;
    private String description;
    private String tag;

    public Wanchengdu() {
    }


    public enum status {
        STOPING(0, "暂缓", "暂缓(仅查看)"),
        WAITING_START(0, "未开始", "未开始"),
        DEVELOPING1(20, "开发中", "开发中"),
        DEVELOPING2(40, "开发中", "开发中"),
        TESTING(60, "开发完成，测试中", "测试中"),
        WAITING_RELEASE(80, "测试完成，待上线", "待上线"),
        OVER(100, "完成，已上线", "完成");

        status(int percent, String description, String tag) {
            this.percent = percent;
            this.description = description;
            this.tag = tag;
        }
        public int percent;
        private String description;
        private String tag;

        public static String getDescription(int percent) {
            for (status s : status.values()) {
                if (s.percent == percent) {
                    return s.description;
                }
            }
            return "";
        }
    }

    public Wanchengdu(Integer percent,
                      String description) {
        this.percent = percent;
        this.description = description;
    }

    //
    public static Dictionary<Integer, Component> getLabels() {
        Dictionary<Integer, Component> labelTable = new Hashtable<>();

        for (status s: status.values()) {
            JLabel jLable = new JLabel(s.tag);

            Font font = jLable.getFont();
            jLable.setFont(new Font(font.getName(), font.getStyle(), 9));
            labelTable.put(s.percent, jLable);
        }

        return labelTable;
    }

    //
    public String getDescription() {
        return status.getDescription(this.percent);
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
