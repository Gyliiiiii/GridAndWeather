package com.bn.gridandweather;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimeService extends Service 
{
	boolean flag=true;//�߳�ѭ����־
	Thread task;//��ʱˢ��ʱ��������߳�
	final Calendar c=Calendar.getInstance();
	private static String mYear;  //��ʾ���
    private static String mMonth;  //��ʾ�·�
    private static String mDay;  //��ʾ���ں�
    private static String mWay;//��ʾ�ܵ�ʱ��
    private static String mHour;//��ʾСʱ
    private static String mMinute;//��ʾ����
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		//��Ϊ�����ò���Bind���ܣ����ֱ�ӷ���null
		return null;
	}
   
	@Override
	public void onCreate()
	{
		super.onCreate();
		//������ʱ����ʱ��������߳�
		task=new Thread()
		{
			public void run()
			{
				while(flag)
				{
					//��ʱ����Intent����ʱ��
					Intent intent = new Intent("wyf.action.time_upadte");
					mWay=StringData();
					intent.putExtra("time", mWay);
					TimeService.this.sendBroadcast(intent);	
					
					//��ʱ����Intent����ʱ�䣬������������View��Ӽ�����
					//��ֹ������widget��������
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
		//���������߳�
		task.start();
	}
	
	@Override
	public void onDestroy()
	{
		//�رն�ʱ����ʱ��������߳�
		flag=false;
	}
	public static String StringData(){  
		final Calendar c = Calendar.getInstance();  
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
		mYear = String.valueOf(c.get(Calendar.YEAR)); // ��ȡ��ǰ���   
		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// ��ȡ��ǰ�·�   
		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// ��ȡ��ǰ�·ݵ����ں�   
		mHour=(c.get(Calendar.HOUR_OF_DAY)<10?"0":"")+String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		mMinute=(c.get(Calendar.MINUTE)<10?"0":"")+String.valueOf(c.get(Calendar.MINUTE));
		return mYear + "��" + mMonth + "��" + mDay+"�� \t\t"+mHour+":"+mMinute;  
	}  
}
