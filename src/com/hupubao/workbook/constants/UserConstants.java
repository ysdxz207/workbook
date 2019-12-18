package com.hupubao.workbook.constants;
/**
 * 
 * @author Moses
 * @date 2017-11-02 17:08
 * 
 */
public class UserConstants {

    public static final String [] HEADS_WB = {"序号", "工作内容", "存在的问题", "完成情况", "星期"};
    public static final String [] HEADS_EMAIL = {"序号", "收件人(多个用英文分号或换行隔开)", "是否默认"};

    /**
     * 需要编辑框的列
     */
    public static Integer [] COLUMN_WORK_CONTENT = {1, 2};
    public static Integer [] COLUMN_EMAIL_CONTENT = {1};

    public static Integer COLUMN_WORK_PROGRESS = 3;

    /**
     * 邮件默认选择列
     */
    public static final int COLUMN_EMAIL_DEFAULT_SELECT = 2;
}
