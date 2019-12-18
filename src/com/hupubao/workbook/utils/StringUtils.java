package com.hupubao.workbook.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author feihong
 * @date 2017-08-10 23:21
 */
public class StringUtils {

    public static boolean isBlank(Object obj) {
        return obj == null || org.apache.commons.lang3.StringUtils.isBlank(obj.toString());
    }

    public static boolean isNotBlank(Object obj){
         return !isBlank(obj);
    }

    public static Integer parseInteger(String str) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.valueOf(m.replaceAll("").trim());
    }

    /**
     * 首字母转大写
     * @param name
     * @return
     */
    public static String firstToUpperCase(String name) {
        return StringUtils.isBlank(name) ? "" : name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 首字母转小写
     * @param name
     * @return
     */
    public static String firstToLowerCase(String name) {
        return StringUtils.isBlank(name) ? "" : name.substring(0, 1).toLowerCase() + name.substring(1);
    }
    public static void main(String[] args) {
        String str = "int(12)";
        System.out.println(parseInteger(str));
    }

    public static String join(Object[] strAry, String join){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.length;i++){
            if(i==(strAry.length-1)){
                sb.append(strAry[i]);
            }else{
                sb.append(strAry[i]).append(join);
            }
        }

        return new String(sb);
    }


    /**
     * 删除空格，换行符，制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder preStr = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (value == null || "".equals(value.trim())) {
                continue;
            }
            if (!"sign".equals(key)) {
                preStr.append(key).append("=").append(value).append("&");
            }
        }
        return preStr.substring(0, preStr.length() - 1);
    }

}
