package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.utils.ComponentUtils;
import com.hupubao.workbook.utils.DataUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Moses
 * @date 2017-10-30 15:12
 * 
 */
public class DelSelectRowActionListener implements ActionListener{

    private JTable table;
    private Project currentProject;


    public DelSelectRowActionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentProject = ComponentUtils.getCurrentProject(table);

        int [] rowIndexes = table.getSelectedRows();

        if (rowIndexes.length == 0) {
            Messages.showMessageDialog(currentProject, "啥都不选删个🔨啊！", "提示", Messages.getWarningIcon());
            return;
        }

        int code = Messages.showOkCancelDialog(currentProject, "真删啊☹？","提示", "是的！", "不不不", Messages.getQuestionIcon());

        if (code != Messages.OK) {
            return;
        }

        DataUtils.deleteTableRows(table,
                rowIndexes);
    }
}
