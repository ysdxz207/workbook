package com.hupubao.workbook.utils;

import com.alibaba.fastjson.JSONArray;

import java.util.Vector;

/**
 * 
 * @author Moses
 * @date 2017-10-31 16:07
 * 
 */
public class JSONUtils {

    public static Vector jsonArrayToVector(JSONArray jsonArray) {
        Vector vector = new Vector();

        if (jsonArray == null || jsonArray.size() == 0) {
            return vector;
        }
        for (int i = 0; i < jsonArray.size(); i ++) {
            Object obj = jsonArray.get(i);
            if (obj instanceof JSONArray) {
                vector.add(jsonArrayToVector((JSONArray) obj));
            } else {

                vector.add(obj);
            }
        }
        return vector;
    }
}
