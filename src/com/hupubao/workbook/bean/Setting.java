package com.hupubao.workbook.bean;

/**
 * <h1>设置信息</h1>
 * @author ysdxz207
 * @date 2019-12-09
 */
public class Setting {


    /**
     * 部门
     */
    private String department;
    /**
     * 排版
     */
    private String style;
    /**
     * 邮件配置
     */
    private EmailSetting emailSetting;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public EmailSetting getEmailSetting() {
        return emailSetting;
    }

    public void setEmailSetting(EmailSetting emailSetting) {
        this.emailSetting = emailSetting;
    }
}
