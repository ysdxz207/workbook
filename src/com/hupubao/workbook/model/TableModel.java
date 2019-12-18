package com.hupubao.workbook.model;

import com.hupubao.workbook.utils.DataUtils;
import com.hupubao.workbook.bean.Wanchengdu;
import com.hupubao.workbook.constants.Constants;
import com.hupubao.workbook.constants.UserConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
    private JTable table;


    public TableModel(JTable table) {
        this.table = table;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (Constants.NAME_TABLE_EMAIL.equalsIgnoreCase(table.getName())) {
            if (column != UserConstants.COLUMN_EMAIL_DEFAULT_SELECT) {
                return true;
            }
            int defaultRow = DataUtils.getDefaultEmailRowNum();
            if (defaultRow == -1) {
                return true;
            }

            return row == DataUtils.getDefaultEmailRowNum() ? true : false;
        }

        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //渲染默认邮件列为勾选框
        if (columnIndex == 2 && Constants.NAME_TABLE_EMAIL.equalsIgnoreCase(table.getName())) {
            return Boolean.class;
        }

        if (columnIndex == UserConstants.COLUMN_WORK_PROGRESS
                && Constants.NAME_TABLE_WB.equalsIgnoreCase(table.getName())) {
            return Wanchengdu.class;
        }

        return super.getColumnClass(columnIndex);
    }


}
