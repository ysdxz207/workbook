package com.hupubao.workbook.utils;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;

import java.awt.*;

/**
 * @author Moses
 * @date 2017-05-24 15:58
 */
public class ComponentUtils {

	public static Project getCurrentProject(Component component) {
		DataContext dataContext = DataManager.getInstance().getDataContext(component);
		return DataKeys.PROJECT.getData(dataContext);
	}

}
