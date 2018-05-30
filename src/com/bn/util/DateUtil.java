package com.bn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil 
{
	//ѧ�ڿ�ѧʱ��(�꣬�£���)
	static int year,month,day;
	//ʱ���
	static long dateDiff=0;
	//��ǰ����Ϊ�ڼ���
	//static int currtWeeksNum=0;
	//��ǰ����Ϊ�ܼ�
	static int ff=0;
	//��ǰ��������
	public static int now_year,now_month,now_day,now_week;
	//ָ�������ʽ
	static SimpleDateFormat format=new SimpleDateFormat("yyyy<#>MM<#>dd");
	
	//���㵱ǰ������ѧ�ڿ�ʼ���ڵ�ʱ���
	private static void dateDiff(String startTime, String endTime, String format) 
	{   
        // ���մ���ĸ�ʽ����һ��simpledateformate����  
        SimpleDateFormat sf = new SimpleDateFormat(format);  
        long nd = 1000 * 24 * 60 * 60;// һ��ĺ�����  
        long diff;  
        try 
        {  
            diff = sf.parse(endTime).getTime() - sf.parse(startTime).getTime();  
            long day = diff / nd;// ����������
            //��ֵ�ľ���ֵ
            dateDiff=Math.abs(day);
            System.out.println("dateDiff"+dateDiff);
        } 
        catch (ParseException e) 
        {  
            e.printStackTrace();  
        }  
    }  
	//���ĳ���ں�n�������
	public static Date getDateAfter(Date d,int day)
	{
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
	   return now.getTime();
    }


	//�����������
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
		//����
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
		//4,6,9,11��
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
	
	//��õ�n�ܵ�����
	public static String getWeeksNumDate(int weeksNum)
	{
		int diffWeek=weeksNum-Constant.currtWeeksNum;
		System.out.println("�������"+diffWeek);
		System.out.println("fff"+ff);
		int tt=-ff+7*diffWeek;
		System.out.println("��������"+tt);
		Date date=new Date();
		date=getDateAfter(date,tt);
		return format.format(date);
	}
	//���㵱ǰ�ǵڼ���
	public static int currtWeeks()
	{
		getNowTime();
		startDate();
		System.out.println("��"+year+"��"+month+"��"+day);
		//���㵱ǰ������ѧ�ڿ�ʼʱ���ʱ���
		dateDiff(new SimpleDateFormat("yyyy<#>MM<#>dd").format(new Date()), year+"<#>"+month+"<#>"+day, "yyyy<#>MM<#>dd");
        //��ʱ����
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
        //���㵱ǰ����Ϊ�ڼ���
        Constant.currtWeeksNum=trueDiff/7+1;
        System.out.println("currtWeeksNum"+Constant.currtWeeksNum);
        return Constant.currtWeeksNum;
	}
	//���ѧ�ڵ�������ʼʱ��
	@SuppressWarnings("deprecation")
	public static void startDate()  
	{  
		//�����ݿ��ȡѧ�ڿ�ʼ����
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
	    //ѧ�ڿ�ʼ���ڲ�Ϊ��һ��������һ������
	    if(week!=0)
	    {
	    	//��õ�һ�ܵ���һ����
			Date date=new Date(year,month,day);
			date=getDateAfter(date,-week);
			String[] dd=format.format(date).split("<#>");
			//������ѧ�ڿ�ʼ����
			year=Integer.parseInt(dd[0])-1900;
			month=Integer.parseInt(dd[1])-1;
			day=Integer.parseInt(dd[2]);
	    }  
	    System.out.println("true_year"+year);
	    System.out.println("true_month"+month);
	    System.out.println("true_day"+day);
	} 
	//��õ�ǰ������������
	public static void getNowTime()
	{
		final Calendar c = Calendar.getInstance();  
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
        now_year = Integer.parseInt(String.valueOf(c.get(Calendar.YEAR))); // ��ȡ��ǰ���   
        now_month = Integer.parseInt(String.valueOf(c.get(Calendar.MONTH) + 1));// ��ȡ��ǰ�·�   
        now_day = Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));// ��ȡ��ǰ�·ݵ����ں���   
        now_week= Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_WEEK)));  
//        if("1".equals(mWay)){  
//            mWay ="��";  
//        }else if("2".equals(mWay)){  
//            mWay ="һ";  
//        }else if("3".equals(mWay)){  
//            mWay ="��";  
//        }else if("4".equals(mWay)){  
//            mWay ="��";  
//        }else if("5".equals(mWay)){  
//            mWay ="��";  
//        }else if("6".equals(mWay)){  
//            mWay ="��";  
//        }else if("7".equals(mWay)){  
//            mWay ="��";  
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