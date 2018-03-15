package com.ssgl.util;

import java.text.SimpleDateFormat;
import java.util.*;

public class Util {
	/**
	 *
	 * @功能:生成主键id
	 * @return
	 */
	public static String makeId(){
		long t1 = System.currentTimeMillis();
		return t1+makeRandom();
	}

	/**
	 *
	 * @功能:生成随机数
	 * @return
	 */
	public static String makeRandom(){
		Random r = new Random();
		String string = "";
		for(int i =0;i<6;i++){
			string += r.nextInt(10);
		}
		return string;
	}

	/**
	 *
	 * @功能:获取当前时间并以字符串方式返回
	 * @return
	 */
	public static String getStringDate(){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(d);
    }

    public static String getDateTime(String time){
		if(null==time){
			return new Date().toString();
		}
		return time;
	}

}
