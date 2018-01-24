package com.ssgl.util;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/5 0005
 * Time: 20:14
 */

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    /**
     * 字符串转换为list
     * @param string
     * @return
     */
    public static List<String> stringConvertList(String string){
        String[] ids = string.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0;i<ids.length;i++){
            list.add(ids[i]);
        }
        return list;
    }
}
