package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.bean.DayfWeek;
import com.hupubao.workbook.bean.Wanchengdu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Vector;

/**
 * 
 * @author Moses
 * @date 2017-10-30 15:12
 * 
 */
public class AddNewWBActionListener implements ActionListener{

    private JTable tableNewWB;


    public AddNewWBActionListener(JTable tableNewWB) {
        this.tableNewWB = tableNewWB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tableNewWB.getModel();
        LocalDateTime now = LocalDateTime.now();

        int index = defaultTableModel.getRowCount() + 1;
        Vector<Object> data = new Vector<>();
        data.add(index);
        data.add("");
        data.add("");
        data.add(new Wanchengdu());
        data.add(new DayfWeek(now));

        defaultTableModel.addRow(data);
    }
}
