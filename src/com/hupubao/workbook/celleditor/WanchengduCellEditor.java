package com.hupubao.workbook.celleditor;

import com.hupubao.workbook.bean.Wanchengdu;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

public class WanchengduCellEditor extends JSlider implements TableCellEditor {

    protected transient Vector listeners;

    protected transient int originalValue;

    protected transient boolean editing;

    public WanchengduCellEditor() {
        super(SwingConstants.HORIZONTAL);
        listeners = new Vector();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            return this;
        }

        originalValue = getValue();
        editing = true;

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

    @Override
    public Object getCellEditorValue() {

        return new Wanchengdu(new Integer(getValue()), Wanchengdu.status.getDescription(getValue()));
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
        setValue(originalValue);
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

        if (e.getButton() == MouseEvent.BUTTON1
                && e.getID() == MouseEvent.MOUSE_RELEASED) {
            fireEditingStopped();
        }
    }
}
