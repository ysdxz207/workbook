package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.constants.Constants;
import com.hupubao.workbook.utils.ComponentUtils;
import com.hupubao.workbook.utils.DataUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * 
 * @author Moses
 * @date 2017-10-30 17:35
 * 
 */
public class TableDataChangedListener implements TableModelListener {

    private JTable table;

    public TableDataChangedListener(JTable table) {
        this.table = table;
    }


    @Override
    public void tableChanged(TableModelEvent e) {
        //project之间同步数据
        Project projectCurrent = ComponentUtils.getCurrentProject(table);
        if (projectCurrent != null) {
            //项目加载好后再保存配置
            DataUtils.saveConfigs(table, false);
        }
        Project [] projects = ProjectManager.getInstance().getOpenProjects();
        for (Project project : projects) {
            if (projectCurrent != null &&
                    !project.equals(projectCurrent)) {
                ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(Constants.NAME_TOOL_WINDOW);
                Content [] contents = toolWindow.getContentManager().getContents();
                for (Content content : contents) {

                    JTabbedPane jTabbedPane = (JTabbedPane) content.getComponent();
                    JPanel jPane = (JPanel)jTabbedPane.getComponent(0);
                    JScrollPane jScrollPane = (JScrollPane) jPane.getComponent(0);
                    JTable tableOther = (JTable) ((JViewport) jScrollPane.getComponent(0)).getView();

                    if (table.getName().equals(tableOther.getName())) {
                        tableOther.setModel(table.getModel());
                        DataUtils.setTableColumn(tableOther);
                        //重新设置内容不可编辑列
                        DataUtils.setCulomnUneditable(tableOther);
                    }
                }
            }
        }

    }
}
