package com.bn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil 
{
	//学期开学时间(年，月，日)
	static int year,month,day;
	//时间差
	static long dateDiff=0;
	//当前日期为第几周
	//static int currtWeeksNum=0;
	//当前日期为周几
	static int ff=0;
	//当前的年月日
	public static int now_year,now_month,now_day,now_week;
	//指定输出格式
	static SimpleDateFormat format=new SimpleDateFormat("yyyy<#>MM<#>dd");
	
	//计算当前日期与学期开始日期的时间差
	private static void dateDiff(String startTime, String endTime, String format) 
	{   
        // 按照传入的格式生成一个simpledateformate对象  
        SimpleDateFormat sf = new SimpleDateFormat(format);  
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
        long diff;  
        try 
        {  
            diff = sf.parse(endTime).getTime() - sf.parse(startTime).getTime();  
            long day = diff / nd;// 计算差多少天
            //差值的绝对值
            dateDiff=Math.abs(day);
            System.out.println("dateDiff"+dateDiff);
        } 
        catch (ParseException e) 
        {  
            e.printStackTrace();  
        }  
    }  
	//获得某日期后n天的日期
	public static Date getDateAfter(Date d,int day)
	{
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
	   return now.getTime();
    }


	//获得整周日期
	public static int[] wholeWeekDate(int weeksNum)
	{
		currtWeeks();
		
		int[] everyMonthDate={31,28,31,30,31,30,31,31,30,31,30,31};
		String[] info=getWeeksNumDate(weeksNum).split("<#>");
		int temp_year=Integer.parseInt(info[0]);
		int temp_month=Integer.parseInt(info[1]);
		int temp_day=Integer.parseInt(info[2]);
		int[] finalInfo={temp_month,temp_day,0,0,0,0,0,0};
		int temp;
		//闰年
		if(temp_year%400==0||(temp_year%4==0&&temp_year%100!=0))
		{
			everyMonthDate[1]=29;
		}
		if(temp_month==2)
		{
			for(int j=2;j<8;j++)
			{
				temp=temp_day+j-1;
				if(temp>everyMonthDate[1])
				{
					finalInfo[j]=temp-everyMonthDate[1];
				}
				else
				{
					finalInfo[j]=temp;
				}
			}
		}
		//4,6,9,11月
		else if(temp_month==4||temp_month==6||temp_month==9||temp_month==11)
		{
			for(int j=2;j<8;j++)
			{
				temp=temp_day+j-1;
				if(temp>30)
				{
					finalInfo[j]=temp-30;
				}
				else
				{
					finalInfo[j]=temp;
				}
			}
		}
		else
		{
			for(int j=2;j<8;j++)
			{
				temp=temp_day+j-1;
				if(temp>31)
				{
					finalInfo[j]=temp-31;
				}
				else
				{
					finalInfo[j]=temp;
				}
			}
		}
		return finalInfo;
	}
	
	//获得第n周的日期
	public static String getWeeksNumDate(int weeksNum)
	{
		int diffWeek=weeksNum-Constant.currtWeeksNum;
		System.out.println("差的周数"+diffWeek);
		System.out.println("fff"+ff);
		int tt=-ff+7*diffWeek;
		System.out.println("减的天数"+tt);
		Date date=new Date();
		date=getDateAfter(date,tt);
		return format.format(date);
	}
	//计算当前是第几周
	public static int currtWeeks()
	{
		getNowTime();
		startDate();
		System.out.println("年"+year+"月"+month+"日"+day);
		//计算当前日期以学期开始时间的时间差
		dateDiff(new SimpleDateFormat("yyyy<#>MM<#>dd").format(new Date()), year+"<#>"+month+"<#>"+day, "yyyy<#>MM<#>dd");
        //临时变量
	    int trueDiff=0;
        switch(now_week)
        {
        case 1:trueDiff=(int)(dateDiff-6);ff=6;break;//7
        case 2:trueDiff=(int)(dateDiff);ff=0;break;//1
        case 3:trueDiff=(int)(dateDiff-1);ff=1;break;//2
        case 4:trueDiff=(int)(dateDiff-2);ff=2;break;//3
        case 5:trueDiff=(int)(dateDiff-3);ff=3;break;//4
        case 6:trueDiff=(int)(dateDiff-4);ff=4;break;//5
        case 7:trueDiff=(int)(dateDiff-5);ff=5;break;//6
        }
        //计算当前日期为第几周
        Constant.currtWeeksNum=trueDiff/7+1;
        System.out.println("currtWeeksNum"+Constant.currtWeeksNum);
        return Constant.currtWeeksNum;
	}
	//获得学期的真正开始时间
	@SuppressWarnings("deprecation")
	public static void startDate()  
	{  
		//从数据库获取学期开始日期
		String[] startDate=SQLiteUtil.QueryFTime().split("<#>");
//		String[] startDate=new String[3];
//		startDate[0]="2014";
//		startDate[1]="2";
//		startDate[2]="12";
		year=Integer.parseInt(startDate[0]);
		month=Integer.parseInt(startDate[1]);
		day=Integer.parseInt(startDate[2]);
		int temp_year=year;
		int temp_month=month;
		int temp_day=day;
	    if (temp_month == 1 || temp_month == 2)  
	    {  
	        temp_month += 12;  
	        temp_year -= 1;  
	    }  
	    int week = (temp_day + 2 * temp_month + 3 * (temp_month + 1) / 5 + temp_year + temp_year / 4 - temp_year / 100 + temp_year / 400) % 7;  
	    //学期开始星期不为周一，则获得周一的日期
	    if(week!=0)
	    {
	    	//获得第一周的周一日期
			Date date=new Date(year,month,day);
			date=getDateAfter(date,-week);
			String[] dd=format.format(date).split("<#>");
			//真正的学期开始日期
			year=Integer.parseInt(dd[0])-1900;
			month=Integer.parseInt(dd[1])-1;
			day=Integer.parseInt(dd[2]);
	    }  
	    System.out.println("true_year"+year);
	    System.out.println("true_month"+month);
	    System.out.println("true_day"+day);
	} 
	//获得当前的年月日星期
	public static void getNowTime()
	{
		final Calendar c = Calendar.getInstance();  
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
        now_year = Integer.parseInt(String.valueOf(c.get(Calendar.YEAR))); // 获取当前年份   
        now_month = Integer.parseInt(String.valueOf(c.get(Calendar.MONTH) + 1));// 获取当前月份   
        now_day = Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));// 获取当前月份的日期号码   
        now_week= Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_WEEK)));  
//        if("1".equals(mWay)){  
//            mWay ="天";  
//        }else if("2".equals(mWay)){  
//            mWay ="一";  
//        }else if("3".equals(mWay)){  
//            mWay ="二";  
//        }else if("4".equals(mWay)){  
//            mWay ="三";  
//        }else if("5".equals(mWay)){  
//            mWay ="四";  
//        }else if("6".equals(mWay)){  
//            mWay ="五";  
//        }else if("7".equals(mWay)){  
//            mWay ="六";  
//        }  	
//		Date dateNow=new Date();  
//		String[] dateNowStr=format.format(dateNow).split("<#>");
//        now_year =Integer.parseInt(dateNowStr[0]); 
//        now_month =Integer.parseInt(dateNowStr[1]);
//        now_day =Integer.parseInt(dateNowStr[2]);
        System.out.println("getNowTime   "+now_year);
        System.out.println("getNowTime   "+now_month);
        System.out.println("getNowTime   "+now_day);
        System.out.println("getNowTime   "+now_week);
	}
}