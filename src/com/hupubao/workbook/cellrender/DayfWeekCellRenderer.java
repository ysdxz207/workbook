package com.hupubao.workbook.cellrender;


import com.hupubao.workbook.bean.DayfWeek;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayfWeekCellRenderer extends JComboBox<DayfWeek> implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value == null) {
            return this;
        }
        setEnabled(true);
        DayfWeek dayfWeek = (DayfWeek) value;
        removeAllItems();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            DayfWeek dayfWeek1 = new DayfWeek(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.CHINA), dayOfWeek.getValue());
            addItem(dayfWeek1);
            if (dayfWeek.getValue() == dayOfWeek.getValue()) {
                setSelectedItem(dayfWeek1);
            }
        }
        return this;
    }


}
