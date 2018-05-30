package com.bn.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bn.gridandweather.R;

public class JSonInfoUtil{
	//从网络上获取指定城市名称天气预报jSon字符串的方法
	public static String getJSonStr(String cityName) throws Exception{
		String jSonStr=null;
		StringBuilder sb=new StringBuilder("http://api.map.baidu.com/telematics/v3/weather?location=");
		String str=URLEncoder.encode(cityName, "UTF-8");
		sb.append(str);
		sb.append("&output=json&ak=RBP3TTKEPsxxQ0iGxLzLowoA");
		
		String urlStr=null;
		urlStr=new String(sb.toString().getBytes());
		URL url=new URL(urlStr);
		URLConnection uc=url.openConnection();
		uc.setRequestProperty("accept-language", "zh_CN");
		
		InputStream in=uc.getInputStream();
		int ch=0;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    while((ch=in.read())!=-1){
	      	baos.write(ch);
	    }
	    byte[] bb=baos.toByteArray();
	    baos.close();
	    in.close(); 
	    jSonStr=new String(bb);
    	return UnicodeToString(jSonStr);	
	}
	
	//解析JSon字符串，得到应用程序需要的城市名称、天气描述、
	//最高温度、最低温度、风向、风力、图片
	public static TQYBInfo parseJSon(String jSonStr) throws Exception{
		TQYBInfo result=new TQYBInfo();
		JSONObject json = new JSONObject(jSonStr);  
		JSONArray obj = json.getJSONArray("results"); 
		JSONObject temp = new JSONObject(obj.getString(0));  
		result.city=temp.getString("currentCity");//城市名称
		
		JSONArray obj3 = temp.getJSONArray("weather_data");//天气数据
		JSONObject temp2 = new JSONObject(obj3.getString(0));
		
		result.date=temp2.getString("date");//日期
		result.tqms=temp2.getString("weather");//天气
		result.wd=temp2.getString("temperature");//温度
		result.flfx=temp2.getString("wind");//风力风向
		result.pic=getChangePicID(temp2.getString("dayPictureUrl"));//图片
		
		return result;
	}
	
	public static int getChangePicID(String img){//将从网络上获取的图片替换成本地的图片
		String[] group=img.split("/");
		int id = 0;
		if(group[group.length-1].equals("qing.png")){//晴
			id=R.drawable.qing;
		}else if(group[group.length-1].equals("yin.png")){//阴
			id=R.drawable.yin;
		}else if(group[group.length-1].equals("duoyun.png")){//多云
			id=R.drawable.duoyun;
		}else if(group[group.length-1].equals("xiaoyu.png")){//小雨
			id=R.drawable.xiaoyu;
		}else if(group[group.length-1].equals("zhongyu.png")){//中雨
			id=R.drawable.zhongyu;
		}else if(group[group.length-1].equals("dayu.png")){//大雨
			id=R.drawable.dayu;
		}else if(group[group.length-1].equals("zhenyu.png")){//阵雨
			id=R.drawable.zhenyu;
		}else if(group[group.length-1].equals("baoyu.png")){//暴雨
			id=R.drawable.baoyu;
		}else if(group[group.length-1].equals("dabaoyu.png")){//大暴雨
			id=R.drawable.dabaoyu;
		}else if(group[group.length-1].equals("leizhenyu.png")){//雷阵雨
			id=R.drawable.leizhenyu;
		}else if(group[group.length-1].equals("wu.png")){//雾
			id=R.drawable.wu;
		}else if(group[group.length-1].equals("mai.png")){//霾
			id=R.drawable.mai;
		}
		return id;
	}
    public static String UnicodeToString(String str){//将Unicode转换成字符串
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))"); 
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()){
        	ch = (char) Integer.parseInt(matcher.group(2), 16);
        	str = str.replace(matcher.group(1), ch + ""); 
        }
        return str;
    }  
}
