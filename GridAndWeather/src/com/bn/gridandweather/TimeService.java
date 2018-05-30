package com.bn.gridandweather;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimeService extends Service 
{
	boolean flag=true;//线程循环标志
	Thread task;//定时刷新时间的任务线程
	final Calendar c=Calendar.getInstance();
	private static String mYear;  //显示年份
    private static String mMonth;  //显示月份
    private static String mDay;  //显示日期号
    private static String mWay;//显示总的时间
    private static String mHour;//显示小时
    private static String mMinute;//显示分钟
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		//因为本例用不到Bind功能，因此直接返回null
		return null;
	}
   
	@Override
	public void onCreate()
	{
		super.onCreate();
		//创建定时更新时间的任务线程
		task=new Thread()
		{
			public void run()
			{
				while(flag)
				{
					//定时发送Intent更新时间
					Intent intent = new Intent("wyf.action.time_upadte");
					mWay=StringData();
					intent.putExtra("time", mWay);
					TimeService.this.sendBroadcast(intent);	
					
					//定时发送Intent更新时间，并给整个桌面View添加监听器
					//防止切屏后widget不工作了
					intent = new Intent("wyf.action.load_xq");
					TimeService.this.sendBroadcast(intent);	
					try 
					{
						Thread.sleep(500);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	@Override
	public void onStart(Intent intent, int id)
	{		
		//启动任务线程
		task.start();
	}
	
	@Override
	public void onDestroy()
	{
		//关闭定时更新时间的任务线程
		flag=false;
	}
	public static String StringData(){  
		final Calendar c = Calendar.getInstance();  
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
		mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份   
		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份   
		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号   
		mHour=(c.get(Calendar.HOUR_OF_DAY)<10?"0":"")+String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		mMinute=(c.get(Calendar.MINUTE)<10?"0":"")+String.valueOf(c.get(Calendar.MINUTE));
		return mYear + "年" + mMonth + "月" + mDay+"日 \t\t"+mHour+":"+mMinute;  
	}  
}
