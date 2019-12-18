package com.hupubao.workbook.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hupubao.workbook.bean.DataRow;
import com.hupubao.workbook.bean.DayfWeek;
import com.hupubao.workbook.bean.Wanchengdu;
import com.hupubao.workbook.celleditor.DayfWeekCellEditor;
import com.hupubao.workbook.celleditor.WBTableNoEditCellEditor;
import com.hupubao.workbook.celleditor.WanchengduCellEditor;
import com.hupubao.workbook.cellrender.DayfWeekCellRenderer;
import com.hupubao.workbook.cellrender.WanchengduCellRenderer;
import com.hupubao.workbook.constants.Constants;
import com.hupubao.workbook.constants.UserConstants;
import com.hupubao.workbook.listener.wb.TableDataChangedListener;
import com.hupubao.workbook.listener.wb.WBCellMouseListener;
import com.hupubao.workbook.model.TableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Moses
 * @date 2017-10-30 16:55
 */
public class DataUtils {

    private static final String WB_CONFIG_FILE_PATH = System.getProperty("user.home") + "/workbook/wb.json";
    private static final String EMAIL_CONFIG_FILE_PATH = System.getProperty("user.home") + "/workbook/email.json";
    private static DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    private static final int COLUMN_NUM_DEFAULT = 0;


    static {
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    public static void saveConfigs(JTable table,
                                   boolean isEmailVersion) {

        String configPath = getConfigPathByTable(table);

        //表格数据
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        Vector<Vector> vector = defaultTableModel.getDataVector();

        if (vector == null || vector.size() == 0) {
            //清空
            vector = new Vector<>();
        }

        JSONArray jsonArray = new JSONArray();
        for (Vector row : vector) {
            jsonArray.add(row);
        }
        if (Constants.NAME_TABLE_WB.equals(table.getName())) {
            Constants.TABLE_DATA_BOOK = vector;
        } else {
            Constants.TABLE_DATA_EMAIL = vector;
        }


        FileUtils.writeFile(configPath, JSON.toJSONString(jsonArray, SerializerFeature.WriteClassName, SerializerFeature.PrettyFormat));

    }

    public static Vector<Vector> readTableConfig(String configPath) {
        Vector<Vector> vector = new Vector();
        String jsonStr = FileUtils.readFile(configPath);

        if (StringUtils.isBlank(jsonStr)) {
            return vector;
        }
        JSONArray jsonArray = JSON.parseArray(jsonStr);

        return JSONUtils.jsonArrayToVector(jsonArray);
    }

    public static Vector<Vector> readTableConfig(JTable table) {


        if (Constants.NAME_TABLE_WB.equals(table.getName())) {

            if (Constants.TABLE_DATA_BOOK.size() == 0) {
                String configPath = getConfigPathByTable(table);
                Constants.TABLE_DATA_BOOK = readTableConfig(configPath);
            }

            return Constants.TABLE_DATA_BOOK;
        } else {
            if (Constants.TABLE_DATA_EMAIL.size() == 0) {
                String configPath = getConfigPathByTable(table);
                Constants.TABLE_DATA_EMAIL = readTableConfig(configPath);
            }
            return Constants.TABLE_DATA_EMAIL;
        }

    }

    private static String getConfigPathByTable(JTable table
    ) {
        String tableName = table.getName();
        String configPath = WB_CONFIG_FILE_PATH;

        if (Constants.NAME_TABLE_EMAIL.equalsIgnoreCase(tableName)) {
            configPath = EMAIL_CONFIG_FILE_PATH;
        }
        return configPath;
    }

    public static String[] getDefaultRecievers() {
        Vector<Vector> vectorEmail = DataUtils.readTableConfig(EMAIL_CONFIG_FILE_PATH);

        for (Vector row : vectorEmail) {
            boolean isDefault = (boolean) row.get(UserConstants.COLUMN_EMAIL_DEFAULT_SELECT);
            String emails = row.get(1).toString();
            if (isDefault && StringUtils.isNotBlank(emails)) {
                if (emails.indexOf(";") != -1) {
                    return emails.split(";");
                }
                return emails.split("\n");
            }
        }
        return new String[0];
    }

    public static void initTableData(JTable table, String[] heads) {
        //读取数据
        Vector<Vector> vector = DataUtils.readTableConfig(table);

        TableModel dm = new TableModel(table);
        dm.setColumnIdentifiers(heads);


        table.setModel(dm);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setCellEditor(new WBTableNoEditCellEditor());
        table.getColumnModel().getColumn(0).setMinWidth(1);
        table.getColumnModel().getColumn(0).setPreferredWidth(1);

        //内容不可编辑，弹出输入框
        setCulomnUneditable(table);
        if (Constants.NAME_TABLE_WB.equalsIgnoreCase(table.getName())) {
            table.setRowHeight(40);
            table.setCellSelectionEnabled(true);
            table.addMouseListener(new WBCellMouseListener(UserConstants.COLUMN_WORK_CONTENT));

            //设置表格样式
            setTableColumn(table);
        }

        if (Constants.NAME_TABLE_EMAIL.equalsIgnoreCase(table.getName())) {

            table.addMouseListener(new WBCellMouseListener(UserConstants.COLUMN_EMAIL_CONTENT));
        }


        //初始化数据
        for (Vector row : vector) {
            dm.addRow(row);
        }
        dm.addTableModelListener(new TableDataChangedListener(table));
    }

    /**
     * 设置表格样式
     *
     * @param table
     */
    public static void setTableColumn(JTable table) {

        if (!Constants.NAME_TABLE_WB.equals(table.getName())) {
            return;
        }
        table.getColumnModel().getColumn(0).setMinWidth(24);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(66);
        table.getColumnModel().getColumn(3).setMinWidth(172);
        table.getColumnModel().getColumn(4).setMinWidth(66);
        //显示完成度控件
        table.getColumnModel().getColumn(UserConstants.COLUMN_WORK_PROGRESS)
                .setCellRenderer(new WanchengduCellRenderer());
        table.getColumnModel().getColumn(UserConstants.COLUMN_WORK_PROGRESS)
                .setCellEditor(new WanchengduCellEditor());

        table.getColumnModel().getColumn(4)
                .setCellRenderer(new DayfWeekCellRenderer());
        table.getColumnModel().getColumn(4)
                .setCellEditor(new DayfWeekCellEditor());
    }

    public static Vector<Vector> readWBTableConfig() {

        return readTableConfig(WB_CONFIG_FILE_PATH);
    }

    public static int getDefaultEmailRowNum() {
        Vector<Vector> vectorEmail = DataUtils.readTableConfig(EMAIL_CONFIG_FILE_PATH);

        for (int i = 0; i < vectorEmail.size(); i++) {
            Vector row = vectorEmail.get(i);
            boolean isDefault = (boolean) row.get(UserConstants.COLUMN_EMAIL_DEFAULT_SELECT);
            if (isDefault) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 设置列不可编辑
     *
     * @param table
     */
    public static void setCulomnUneditable(JTable table) {
        Integer[] columns = UserConstants.COLUMN_WORK_CONTENT;
        if (Constants.NAME_TABLE_EMAIL.equalsIgnoreCase(table.getName())) {
            columns = UserConstants.COLUMN_EMAIL_CONTENT;
        }

        //内容不可编辑，弹出输入框
        for (int column :
                columns) {

            table.getColumnModel().getColumn(column)
                    .setCellEditor(new WBTableNoEditCellEditor());
        }


    }

    /**
     * 重排序表格序号
     *
     * @param table
     */
    public static void resetTableNum(JTable table) {

        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            table.setValueAt(i + 1, i, COLUMN_NUM_DEFAULT);
        }
    }

    public static void deleteTableRows(JTable table,
                                       int[] rows) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();

        //倒序删除
        for (int i = rows.length - 1; i >= 0; i--) {
            defaultTableModel.removeRow(rows[i]);
        }

        //重排序表格序号
        DataUtils.resetTableNum(table);
    }


    /**
     * <h1>将WB内容组装成列表文本</h1>
     *
     * @return
     */
    public static String buildWBTextListString() {

        return buildContentStringByDataRowList(getDataRowList(), false);
    }

    /**
     * <h1>将WB内容组装成列表文本</h1>
     *
     * @return
     */
    public static String buildWBTextListStringByWeek() {
        StringBuilder sb = new StringBuilder();

        List<DataRow> list = getDataRowList();

        Map<DayfWeek, List<DataRow>> map = list.stream().collect(Collectors.groupingBy(DataRow::getDayfWeek));

        List<DayfWeek> keys = new ArrayList<>(map.keySet());
        keys.sort(Comparator.comparingInt(DayfWeek::getValue));

        for (DayfWeek key : keys) {
            sb.append(key.getDay())
                    .append("：")
                    .append("\n");

            List<DataRow> dataRowList = map.get(key);
            sb.append(buildContentStringByDataRowList(dataRowList, true));
        }


        return sb.toString();
    }

    /**
     * <h1>组装字符串内容</h1>
     *
     * @param dataRowList
     * @param localNum    是否星期独立数字标号
     * @return
     */
    private static String buildContentStringByDataRowList(List<DataRow> dataRowList, boolean localNum) {
        StringBuilder sb = new StringBuilder();
        dataRowList.sort(Comparator.comparingInt(DataRow::getId));

        int i = 1;
        for (DataRow dataRow : dataRowList) {
            if (dataRow.getWanchengdu().getPercent() == Wanchengdu.status.STOPING.percent) {
                continue;
            }
            sb.append(localNum ? i : dataRow.getId())
                    .append("、")
                    .append(dataRow.getContent());
            if (StringUtils.isNotBlank(dataRow.getProblem())) {

                sb.append("(")
                        .append(dataRow.getProblem())
                        .append(")");
            }
            sb.append("[")
                    .append(dataRow.getWanchengdu().getDescription())
                    .append("]")
                    .append("\n");

            i++;
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * <h1>获取表格数据列表</h1>
     *
     * @return
     */
    private static List<DataRow> getDataRowList() {
        Vector<Vector> vectors = DataUtils.readWBTableConfig();

        List<DataRow> list = new ArrayList<>();

        for (Vector vector : vectors) {
            String content = vector.get(1).toString();
            String problem = vector.get(2).toString();
            Wanchengdu wanchengdu = (Wanchengdu) vector.get(3);
            DayfWeek dayfWeek = (DayfWeek) vector.get(4);

            DataRow dataRow = new DataRow();
            dataRow.setId((Integer) vector.get(0));
            dataRow.setContent(content);
            dataRow.setProblem(problem);
            dataRow.setWanchengdu(wanchengdu);
            dataRow.setDayfWeek(dayfWeek);
            list.add(dataRow);

        }

        return list;
    }

    /**
     * <h1>根据星期获取当天内容</h1>
     *
     * @param day
     * @return
     */
    public static String buildContentByDayOfWeek(int day) {
        return buildContentStringByDataRowList(getContentListByDayOfWeek(day), true);
    }

    /**
     * <h1>根据星期获取当天内容列表</h1>
     *
     * @param day
     * @return
     */
    public static List<DataRow> getContentListByDayOfWeek(int day) {

        List<DataRow> list = getDataRowList();

        list = list.stream().filter(dataRow -> dataRow.getDayfWeek().getValue() == day).collect(Collectors.toList());
        list.sort(Comparator.comparingInt(DataRow::getId));

        return list;
    }

    public static String buildPercent(int day) {
        List<DataRow> list = getContentListByDayOfWeek(day);
        double sumPercent = 0.0;
        for (DataRow dataRow : list) {
            sumPercent += dataRow.getWanchengdu().getPercent().doubleValue() / 100;
        }
        int percent = (int) (1.0d / list.size() * sumPercent * 100);
        return percent + "%";
    }

    public static void main(String[] args) {
        int a = 20;
        int b = 40;
        int c = 30;

        System.out.println(1.0d/3*(20.0d/100+40.0d/100+30.0d/100)*100);
    }
}
