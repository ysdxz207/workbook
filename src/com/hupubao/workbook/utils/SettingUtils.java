package com.hupubao.workbook.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hupubao.workbook.bean.Setting;

import java.io.File;
import java.io.IOException;

/**
 * <h1>Workbook设置工具类</h1>
 * @author ysdxz207
 * @date 2019-12-09
 */
public class SettingUtils {

    /**
     * 配置文件路径
     */
    private static final String SETTING_FILE_PATH = System.getProperty("user.home") + "/workbook/setting";


    /**
     * <h1>保存配置信息</h1>
     * @param setting
     */
    public static void saveSetting(Setting setting) {
        File settingFile = new File(SETTING_FILE_PATH);
        if (!settingFile.exists()) {
            try {
                settingFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileUtils.writeFile(SETTING_FILE_PATH, JSON.toJSONString(setting, SerializerFeature.PrettyFormat));
    }


    /**
     * 读取配置信息
     * @return
     */
    public static Setting readSetting() {

        String settingStr = FileUtils.readFile(SETTING_FILE_PATH);
        Setting setting = null;
        try {
            setting = JSON.parseObject(settingStr).toJavaObject(Setting.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return setting;
    }

}
