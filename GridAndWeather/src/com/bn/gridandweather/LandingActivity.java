package com.bn.gridandweather;

import java.util.Calendar;
import com.bn.util.SQLiteUtil;
import com.bn.util.DateUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class LandingActivity extends Activity
{
	String startInfo="";
	final int DATE_DIALOG=0;
    Calendar c=null;
    String dateInfo="";
    String dateViewInfo="";
    EditText starttime;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.another);
		
		SQLiteUtil.createCource();
		SQLiteUtil.createCourceTime();
		SQLiteUtil.createFirstTime();
		if(!SQLiteUtil.QueryFTime().equals(""))
		{
			Intent intent = new Intent();
	        /* ָ��intentҪ�������� */
	        intent.setClass(LandingActivity.this,OddDaysActivity.class);
	        /* ����һ���µ�Activity */
	        LandingActivity.this.startActivity(intent);
	        /* �رյ�ǰ��Activity */
	        LandingActivity.this.finish();
		}else
		{
			DateUtil.getNowTime();
			starttime=(EditText)this.findViewById(R.id.starttime);
			starttime.setText(DateUtil.now_year+"��"+DateUtil.now_month+"��"+DateUtil.now_day+"��");
			dateInfo=DateUtil.now_year+"<#>"+DateUtil.now_month+"<#>"+DateUtil.now_day+"<#>";
			starttime.setOnTouchListener(
					new OnTouchListener()
					{
						@SuppressWarnings("deprecation")
						@Override
						public boolean onTouch(View v, MotionEvent event) 
						{
							showDialog(DATE_DIALOG);
							return false;
						}
					});
			Button next=(Button)this.findViewById(R.id.next);
			next.setOnClickListener(
					new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							//�������ݿ�	
							SQLiteUtil.InsertFTime(dateInfo);
							/* �½�һ��Intent���� */
							Intent intent = new Intent();
							/* ָ��intentҪ�������� */
							intent.setClass(LandingActivity.this,OddDaysActivity.class);
							/* ����һ���µ�Activity */
							LandingActivity.this.startActivity(intent);
							/* �رյ�ǰ��Activity */
							LandingActivity.this.finish();
						}			
					});
		}
	}
	@Override
    public Dialog onCreateDialog(int id){
    	Dialog dialog=null;
    	switch(id){
    	  case DATE_DIALOG://�������ڶԻ���Ĵ���
    		  c=Calendar.getInstance();//��ȡ���ڶ���
    		  dialog=new DatePickerDialog(
    				  this,
    				  new DatePickerDialog.OnDateSetListener()
    				  {
    					  @Override
    					  public void onDateSet(DatePicker arg0, int arg1, int arg2,int arg3) 
    					  {	
    						  arg2=arg2+1;
    						  dateInfo=arg1+"<#>"+arg2+"<#>"+arg3;
    						  dateViewInfo=arg1+"��"+arg2+"��"+arg3+"��";
    						  starttime.setText(dateViewInfo);
    					  } 
    				  },
    				  c.get(Calendar.YEAR),
    				  c.get(Calendar.MONTH),
    				  c.get(Calendar.DAY_OF_MONTH)    		     
    		  );
    	  break;
    	}
    	return dialog;
	}
}

