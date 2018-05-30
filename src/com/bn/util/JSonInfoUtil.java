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
	//�������ϻ�ȡָ��������������Ԥ��jSon�ַ����ķ���
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
	
	//����JSon�ַ������õ�Ӧ�ó�����Ҫ�ĳ������ơ�����������
	//����¶ȡ�����¶ȡ����򡢷�����ͼƬ
	public static TQYBInfo parseJSon(String jSonStr) throws Exception{
		TQYBInfo result=new TQYBInfo();
		JSONObject json = new JSONObject(jSonStr);  
		JSONArray obj = json.getJSONArray("results"); 
		JSONObject temp = new JSONObject(obj.getString(0));  
		result.city=temp.getString("currentCity");//��������
		
		JSONArray obj3 = temp.getJSONArray("weather_data");//��������
		JSONObject temp2 = new JSONObject(obj3.getString(0));
		
		result.date=temp2.getString("date");//����
		result.tqms=temp2.getString("weather");//����
		result.wd=temp2.getString("temperature");//�¶�
		result.flfx=temp2.getString("wind");//��������
		result.pic=getChangePicID(temp2.getString("dayPictureUrl"));//ͼƬ
		
		return result;
	}
	
	public static int getChangePicID(String img){//���������ϻ�ȡ��ͼƬ�滻�ɱ��ص�ͼƬ
		String[] group=img.split("/");
		int id = 0;
		if(group[group.length-1].equals("qing.png")){//��
			id=R.drawable.qing;
		}else if(group[group.length-1].equals("yin.png")){//��
			id=R.drawable.yin;
		}else if(group[group.length-1].equals("duoyun.png")){//����
			id=R.drawable.duoyun;
		}else if(group[group.length-1].equals("xiaoyu.png")){//С��
			id=R.drawable.xiaoyu;
		}else if(group[group.length-1].equals("zhongyu.png")){//����
			id=R.drawable.zhongyu;
		}else if(group[group.length-1].equals("dayu.png")){//����
			id=R.drawable.dayu;
		}else if(group[group.length-1].equals("zhenyu.png")){//����
			id=R.drawable.zhenyu;
		}else if(group[group.length-1].equals("baoyu.png")){//����
			id=R.drawable.baoyu;
		}else if(group[group.length-1].equals("dabaoyu.png")){//����
			id=R.drawable.dabaoyu;
		}else if(group[group.length-1].equals("leizhenyu.png")){//������
			id=R.drawable.leizhenyu;
		}else if(group[group.length-1].equals("wu.png")){//��
			id=R.drawable.wu;
		}else if(group[group.length-1].equals("mai.png")){//��
			id=R.drawable.mai;
		}
		return id;
	}
    public static String UnicodeToString(String str){//��Unicodeת�����ַ���
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
