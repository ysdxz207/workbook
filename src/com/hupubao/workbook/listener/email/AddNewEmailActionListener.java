package com.hupubao.workbook.listener.email;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * 
 * @author Moses
 * @date 2017-10-30 15:12
 * 
 */
public class AddNewEmailActionListener implements ActionListener{

    private JTable table;


    public AddNewEmailActionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();

        int index = defaultTableModel.getRowCount() + 1;
        Vector<Object> data = new Vector<>();
        data.add(index);
        data.add("");
        data.add(new Boolean(false));

        defaultTableModel.addRow(data);
    }
}
