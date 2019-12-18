package com.hupubao.workbook.windows;

import com.alibaba.fastjson.parser.ParserConfig;
import com.hupubao.workbook.bean.EmailSetting;
import com.hupubao.workbook.bean.Setting;
import com.hupubao.workbook.constants.Constants;
import com.hupubao.workbook.constants.UserConstants;
import com.hupubao.workbook.enums.Style;
import com.hupubao.workbook.listener.email.AddNewEmailActionListener;
import com.hupubao.workbook.listener.wb.*;
import com.hupubao.workbook.utils.DataUtils;
import com.hupubao.workbook.utils.SettingUtils;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;

/**
 *
 */
public class Main implements ProjectComponent {

    private Project project;

    private JPanel mainPane;
    private JTable tableNewWb;
    private JButton btnNewRow;
    private JButton btnDelRow;
    private JButton btnSendEmail;
    private JButton btnEmailAdd;
    private JButton btnEmailDel;
    private JTable tableEmail;
    private JScrollPane jScrollPaneWb;
    private JScrollPane jScrollPaneEmail;
    private JButton btnSendEmail2;
    private JButton btnTextWb;
    private JButton btnClear;
    private JTabbedPane tabbedPane;
    private ButtonGroup buttonGroupChooseType;
    private JRadioButton radioButton2;
    private JRadioButton radioButton1;
    private JTextField textFieldHost;
    private JTextField textFieldPort;
    private JTextField textFieldEmail;
    private JTextArea textAreaEmailTemplate;
    private JPanel panelSettingContent;
    private JPanel panelSettingTools;
    private JButton buttonSaveSetting;
    private JPanel panelWBTools;
    private JPanel panelEmailTools;
    private JTextField textFieldSender;
    private JTextField textFieldTitle;
    private JTextField textFieldPassword;
    private JTextField textFieldDepartment;

    public static ToolWindow toolWindow;

    public Main() {

    }

    public Main(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
        // 开启fastjson的auto_type功能，并添加白名单
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        ParserConfig.getGlobalInstance().addAccept("com.hupubao.workbook.bean.");

        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        Runnable runnable = () -> {
            toolWindow = toolWindowManager.registerToolWindow(Constants.NAME_TOOL_WINDOW, true, ToolWindowAnchor.RIGHT);
            ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

            Content content = contentFactory.createContent(tabbedPane, "", false);

            content.setCloseable(false);

            ContentManager contentManager = toolWindow.getContentManager();
            contentManager.addContent(content);

            contentManager.setSelectedContent(content);

            toolWindow.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Constants.TOOL_WINDOW_ICON)));

            initCompents();
        };
        toolWindowManager.invokeLater(runnable);

    }

    private void initCompents() {
        btnNewRow.addActionListener(new AddNewWBActionListener(tableNewWb));
        btnDelRow.addActionListener(new DelSelectRowActionListener(tableNewWb));
        btnSendEmail.addActionListener(new SendEmailActionListener(tableNewWb));
        btnSendEmail2.addActionListener(new SendEmailActionListener(tableNewWb));

        btnEmailAdd.addActionListener(new AddNewEmailActionListener(tableEmail));
        btnEmailDel.addActionListener(new DelSelectRowActionListener(tableEmail));
        btnTextWb.addActionListener(new TextWBActionListener(tableNewWb));
        btnClear.addActionListener(new ClearWBActionListener(tableNewWb));


        buttonSaveSetting.addActionListener(new SaveSettingActionListener(textFieldDepartment, textFieldHost,
                textFieldPort, textFieldEmail, textFieldTitle,
                textFieldSender, textFieldPassword, textAreaEmailTemplate,
                radioButton1));

        // 初始化设置回显
        Setting setting = SettingUtils.readSetting();
        if (setting != null) {
            EmailSetting emailSetting = setting.getEmailSetting();
            textFieldDepartment.setText(setting.getDepartment());
            textFieldHost.setText(emailSetting.getHost());
            textFieldPort.setText(emailSetting.getPort());
            textFieldEmail.setText(emailSetting.getEmail());
            textFieldTitle.setText(emailSetting.getTitle());
            textFieldSender.setText(emailSetting.getSender());
            textFieldPassword.setText(emailSetting.getPassword());
            textAreaEmailTemplate.setText(emailSetting.getContentTemplate());
            if (Style.LIST.name().equals(setting.getStyle())) {
                radioButton1.setSelected(true);
            } else {
                radioButton2.setSelected(true);
            }
        }

        tableNewWb.setName(Constants.NAME_TABLE_WB);
        tableEmail.setName(Constants.NAME_TABLE_EMAIL);

        //表格数据初始化
        DataUtils.initTableData(tableNewWb, UserConstants.HEADS_WB);
        DataUtils.initTableData(tableEmail, UserConstants.HEADS_EMAIL);

    }


}