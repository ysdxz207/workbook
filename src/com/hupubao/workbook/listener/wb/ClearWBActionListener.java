package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.bean.Wanchengdu;
import com.hupubao.workbook.utils.ComponentUtils;
import com.hupubao.workbook.utils.DataUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Moses
 * @date 2018-04-20 14:39:22
 */

public class ClearWBActionListener implements ActionListener {

    private JTable tableWB;
    private Project currentProject;


    public ClearWBActionListener(JTable tableWB) {
        this.tableWB = tableWB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int chooseCode = Messages.showOkCancelDialog("确定清理已完成任务❔",
                "提示", "很确定！", "还是不了！", Messages.getErrorIcon());

        if (chooseCode != Messages.OK) {
            return;
        }

        currentProject = ComponentUtils.getCurrentProject(tableWB);
        List<Integer> wanchengRows = new ArrayList<>();

        Vector<Vector> vectorWB = DataUtils.readWBTableConfig();

        for (Vector vector : vectorWB) {
            Wanchengdu wanchengdu = (Wanchengdu) vector.get(3);

            if (wanchengdu.getPercent() != null
                    && wanchengdu.getPercent() == 100) {
                int rowNum = (Integer) vector.get(0);
                wanchengRows.add(rowNum);
            }
        }


        if (wanchengRows.size() == 0) {
            Messages.showMessageDialog(currentProject, "没有任务可清理！<(￣︶￣)>", "提示", Messages.getWarningIcon());
            return;
        }

        int [] rowIndexes = new int[wanchengRows.size()];

        for (int i = 0;i < wanchengRows.size(); i++) {
            //因此处获取到的是行号，需要转成行索引
            rowIndexes[i] = wanchengRows.get(i) - 1;
        }

        DataUtils.deleteTableRows(tableWB,
                rowIndexes);
    }

}


