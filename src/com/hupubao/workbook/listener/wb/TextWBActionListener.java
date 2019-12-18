package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.bean.Setting;
import com.hupubao.workbook.enums.Style;
import com.hupubao.workbook.utils.ComponentUtils;
import com.hupubao.workbook.utils.DataUtils;
import com.hupubao.workbook.utils.SettingUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Moses
 * @date 2017-10-30 15:12
 */
public class TextWBActionListener implements ActionListener {

    private JTable tableNewWB;
    private Project currentProject;


    public TextWBActionListener(JTable tableNewWB) {
        this.tableNewWB = tableNewWB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentProject = ComponentUtils.getCurrentProject(tableNewWB);

        // 查询设置
        Setting setting = SettingUtils.readSetting();

        String wb = Style.WEEK.name().equals(setting.getStyle()) ? DataUtils.buildWBTextListStringByWeek() : DataUtils.buildWBTextListString();

        DialogBuilder builder = new DialogBuilder(tableNewWB);

        JPanel panelContent = new JPanel();
        panelContent.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setColumns(90);
        textArea.setRows(20);
        textArea.setText(wb);
        panelContent.add(textArea, BorderLayout.CENTER);

        builder.getWindow().setLocationRelativeTo(null);
        builder.setCenterPanel(panelContent);
        //持久化的key
        builder.setDimensionServiceKey("workbook_text_wb_dialog");
        builder.setTitle("WB文本内容");
        builder.removeAllActions();
        builder.addOkAction();

        boolean isOk = builder.show() == DialogWrapper.OK_EXIT_CODE;
        if (isOk) {
        }
    }
}


