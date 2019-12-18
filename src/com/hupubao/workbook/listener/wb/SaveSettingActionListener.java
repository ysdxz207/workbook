package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.bean.EmailSetting;
import com.hupubao.workbook.bean.Setting;
import com.hupubao.workbook.enums.Style;
import com.hupubao.workbook.utils.SettingUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>保存设置</h1>
 * @author ysdxz207
 * @date 2019-12-09
 */
public class SaveSettingActionListener implements ActionListener {

    private JTextField textfieldDepartment;
    private JTextField textFieldHost;
    private JTextField textFieldPort;
    private JTextField textFieldEmail;
    private JTextField textFieldTitle;
    private JTextField textFieldSender;
    private JTextField textFieldPassword;
    private JTextArea textAreaEmailTemplate;
    private JRadioButton jRadioButton1;
    private Project currentProject;


    public SaveSettingActionListener(JTextField textfieldDepartment,
                                     JTextField textFieldHost,
                                     JTextField textFieldPort,
                                     JTextField textFieldEmail,
                                     JTextField textFieldTitle,
                                     JTextField textFieldSender,
                                     JTextField textFieldPassword,
                                     JTextArea textAreaEmailTemplate,
                                     JRadioButton jRadioButton1) {
        this.textfieldDepartment = textfieldDepartment;
        this.textFieldHost = textFieldHost;
        this.textFieldPort = textFieldPort;
        this.textFieldEmail = textFieldEmail;
        this.textFieldTitle = textFieldTitle;
        this.textFieldSender = textFieldSender;
        this.textFieldPassword = textFieldPassword;
        this.textAreaEmailTemplate = textAreaEmailTemplate;
        this.jRadioButton1 = jRadioButton1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Setting setting = new Setting();
        EmailSetting emailSetting = new EmailSetting();


        setting.setDepartment(textfieldDepartment.getText());
        setting.setStyle(jRadioButton1.isSelected() ? Style.LIST.name() : Style.WEEK.name());
        emailSetting.setHost(textFieldHost.getText());
        emailSetting.setPort(textFieldPort.getText());
        emailSetting.setEmail(textFieldEmail.getText());
        emailSetting.setTitle(textFieldTitle.getText());
        emailSetting.setSender(textFieldSender.getText());
        emailSetting.setPassword(textFieldPassword.getText());
        emailSetting.setContentTemplate(textAreaEmailTemplate.getText());

        setting.setEmailSetting(emailSetting);

        SettingUtils.saveSetting(setting);

        Messages.showMessageDialog(currentProject, "保存成功了！", "提示", Messages.getInformationIcon());
    }

}


