package com.hupubao.workbook.cellrender;


import com.hupubao.workbook.bean.Wanchengdu;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class WanchengduCellRenderer extends JSlider implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value == null) {
            return this;
        }
        Wanchengdu wanchengdu = (Wanchengdu) value;
        setEnabled(true);
        setValue(wanchengdu.getPercent());
        setMajorTickSpacing(20);
        setMinorTickSpacing(20);
        setPaintLabels(true);
        setSnapToTicks(true);
        setLabelTable(Wanchengdu.getLabels());
        return this;
    }


}
