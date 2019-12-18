package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.utils.ComponentUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

/**
 * 
 * @author Moses
 * @date 2017-11-08 14:44
 * 
 */
public class WBCellMouseListener extends MouseAdapter {
    
    private Integer [] columns;
    private Project currentProject;


    public WBCellMouseListener(Integer [] columns) {
        this.columns = columns;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        JTable table = (JTable) e.getSource();
        currentProject = ComponentUtils.getCurrentProject(table);

        if (e.getClickCount() ==2 &&
                Arrays.asList(columns).contains(table.getSelectedColumn())) {

            int selectedRow = table.getSelectedRow();

            int selectedColumn = table.getSelectedColumn();

            for (int column :
                    columns) {
                if (selectedColumn != column) {
                    continue;
                }
                String cellValue = "";
                Object cellValueObj = table.getValueAt(selectedRow, column);
                cellValue = cellValueObj == null ? "" : cellValueObj.toString();
                String str = Messages.showMultilineInputDialog
                        (currentProject,
                                "内容：",
                                "编辑",
                                cellValue,
                                Messages.getInformationIcon(),
                                null);
                if (str != null) {
                    //str==null为用户取消
                    table.setValueAt(str, selectedRow, column);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
