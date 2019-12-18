package com.hupubao.workbook.celleditor;

import com.hupubao.workbook.bean.DayfWeek;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.EventObject;
import java.util.Locale;
import java.util.Vector;

public class DayfWeekCellEditor extends JComboBox<DayfWeek> implements TableCellEditor {

    protected transient Vector listeners;

    protected transient boolean editing;

    public DayfWeekCellEditor() {
        super();
        listeners = new Vector();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            return this;
        }


        editing = true;

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

    @Override
    public Object getCellEditorValue() {

        return getSelectedItem();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        editing = false;
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
        editing = false;
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        listeners.addElement(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        listeners.removeElement(l);
    }


    protected void fireEditingCanceled() {
        ChangeEvent ce = new ChangeEvent(this);
        for (int i = listeners.size() - 1; i >= 0; i--) {
            ((CellEditorListener) listeners.elementAt(i)).editingCanceled(ce);
        }
    }

    protected void fireEditingStopped() {
        ChangeEvent ce = new ChangeEvent(this);
        for (int i = listeners.size() - 1; i >= 0; i--) {
            ((CellEditorListener) listeners.elementAt(i)).editingStopped(ce);
        }
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        // 星期下拉框改变事件将会触发编辑结束事件，编辑结束事件会触发TableDataChangedListener
        addItemListener(e1 -> fireEditingStopped());

    }
}
