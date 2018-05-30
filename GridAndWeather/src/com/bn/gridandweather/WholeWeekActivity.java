package com.bn.gridandweather;

import com.bn.adapter.WholeWeekAdapter;
import com.bn.util.Constant;
import com.bn.util.DateUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class WholeWeekActivity extends Activity
{
	Spinner spinner;//换周列表
	Button changeFace;//换肤按钮
	Button oddDays;//单日按钮
	LinearLayout rtlayout;
	ListView listview;
	//临时变量
	int index=0;
	//spinner选中的周数
//	public static int weeksNum=1;
	public AlertDialog.Builder builder = null;
	TextView curmonth,monday,tuesday,thursday,wednesday,friday,saturday,sunday;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whole_week);
		
		rtlayout=(LinearLayout)this.findViewById(R.id.layout);
		
		initFace();//初始化皮肤
		initButton();//初始化功能按钮
		changeTimes();//定期的改变时间
		initListView();//初始化ListView
	}
	//初始化皮肤
	public void initFace()
	{
		switch(Constant.skinsSelection)
		{
		case 0:rtlayout.setBackgroundResource(R.color.white);
		break;
		case 1:rtlayout.setBackgroundResource(R.drawable.netskin);
		break;
		case 2:rtlayout.setBackgroundResource(R.drawable.flower);
		break;
		case 3:rtlayout.setBackgroundResource(R.drawable.fengche);
		break;
		case 4:rtlayout.setBackgroundResource(R.drawable.bear);
		break;
		case 5:rtlayout.setBackgroundResource(R.drawable.butterfly);
		break;
		case 6:rtlayout.setBackgroundResource(R.drawable.green);
		break;
		}
	}
	//初始化功能按钮
	public void initButton()
	{
		//单日
		oddDays=(Button)this.findViewById(R.id.odddays);
		//监听，切换到整周
		oddDays.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						Intent intent=new Intent();
						intent.setClass(WholeWeekActivity.this, OddDaysActivity.class);
						WholeWeekActivity.this.startActivity(intent);
					}
				}
				);
		//皮肤
		changeFace=(Button)this.findViewById(R.id.changeFace);
		changeFace.setOnClickListener(
    			new OnClickListener(){
    				@Override
    			    public void onClick(View v){
    					new AlertDialog.Builder(WholeWeekActivity.this)
						.setTitle("皮肤")
						.setIcon(android.R.drawable.ic_dialog_info)                
						.setSingleChoiceItems(Constant.skins, index,
						  new DialogInterface.OnClickListener() 
						  {              
						     public void onClick(DialogInterface dialog, int which)
						     {
						    	 index=which;
						    	 try
						    	 {
						    		 switch(which)
						    		 {
						    		 	case 0:rtlayout.setBackgroundResource(R.color.white);
						    		 	Constant.skinsSelection=0;
						    			break;
						    			case 1:rtlayout.setBackgroundResource(R.drawable.netskin);
						    			Constant.skinsSelection=1;
						    			break;
						    			case 2:rtlayout.setBackgroundResource(R.drawable.flower);
						    			Constant.skinsSelection=2;
						    			break;
						    			case 3:rtlayout.setBackgroundResource(R.drawable.fengche);
						    			Constant.skinsSelection=3;
						    			break;
						    			case 4:rtlayout.setBackgroundResource(R.drawable.bear);
						    			Constant.skinsSelection=4;
						    			break;
						    			case 5:rtlayout.setBackgroundResource(R.drawable.butterfly);
						    			Constant.skinsSelection=5;
						    			break;
						    			case 6:rtlayout.setBackgroundResource(R.drawable.green);
						    			Constant.skinsSelection=6;
						    			break;
						    		 } 
						 	     }
						    	 catch(Exception e)
						    	 {
						 	    	e.printStackTrace();
						 	     }
						         dialog.dismiss();
						      }
						   }
						)
						.setNegativeButton("取消", null)
						.show();
    			     }
    		});
		//选择周数
		spinner=(Spinner)this.findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Constant.spinnerInfo);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(Constant.spinnerSelection);
		spinner.setOnItemSelectedListener(
				new OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
						switch(arg2)
						{
						case 0:Constant.weeksNum2=1;break;
						case 1:Constant.weeksNum2=2;break;
						case 2:Constant.weeksNum2=3;break;
						case 3:Constant.weeksNum2=4;break;
						case 4:Constant.weeksNum2=5;break;
						case 5:Constant.weeksNum2=6;break;
						case 6:Constant.weeksNum2=7;break;
						case 7:Constant.weeksNum2=8;break;
						case 8:Constant.weeksNum2=9;break;
						case 9:Constant.weeksNum2=10;break;
						case 10:Constant.weeksNum2=11;break;
						case 11:Constant.weeksNum2=12;break;
						case 12:Constant.weeksNum2=13;break;
						case 13:Constant.weeksNum2=14;break;
						case 14:Constant.weeksNum2=15;break;
						case 15:Constant.weeksNum2=16;break;
						case 16:Constant.weeksNum2=17;break;
						case 17:Constant.weeksNum2=18;break;
						case 18:Constant.weeksNum2=19;break;
						case 19:Constant.weeksNum2=20;break;
						}
						Constant.spinnerSelection=Constant.weeksNum2-1;
						changeTimes();
						listview.setAdapter(new WholeWeekAdapter(WholeWeekActivity.this));
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				});
	}
	//定期的改变时间
	public void changeTimes()
	{
		//月份
		curmonth=(TextView)this.findViewById(R.id.month);
		
		//日期--周几
		monday=(TextView)this.findViewById(R.id.textView1);
		tuesday=(TextView)this.findViewById(R.id.textView2);
		wednesday=(TextView)this.findViewById(R.id.textView3);
		thursday=(TextView)this.findViewById(R.id.textView4);
	    friday=(TextView)this.findViewById(R.id.textView5);
		saturday=(TextView)this.findViewById(R.id.textView6);
		sunday=(TextView)this.findViewById(R.id.textView7);
		//清空原有日期
		monday.setText("");
		tuesday.setText("");
		wednesday.setText("");
		thursday.setText("");
		friday.setText("");
		saturday.setText("");
		sunday.setText("");
		//获得整周日期
		int[] WholeWeekDate=DateUtil.wholeWeekDate(Constant.spinnerSelection+1);
		//设置月份和周一--周日的日期
		curmonth.setText(WholeWeekDate[0]+"月");
	    monday.setText(WholeWeekDate[1]+"\n"+"周一");
	    tuesday.setText(WholeWeekDate[2]+"\n"+"周二");
	    wednesday.setText(WholeWeekDate[3]+"\n"+"周三");
	    thursday.setText(WholeWeekDate[4]+"\n"+"周四");
	    friday.setText(WholeWeekDate[5]+"\n"+"周五");
	    saturday.setText(WholeWeekDate[6]+"\n"+"周六");
	    sunday.setText(WholeWeekDate[7]+"\n"+"周日");
	    //当前周数显示周几
	    if(Constant.spinnerSelection+1==Constant.currtWeeksNum)
	    {
	    	System.out.println("WholeWeekActivity");
	    	switch(DateUtil.now_week)//获得当前日期为周几
	    	{
	    	case 1:sunday.setBackgroundColor(Color.GRAY);//周日
				break;
	    	case 2:monday.setBackgroundColor(Color.GRAY);//周一
	    		break;
	    	case 3:tuesday.setBackgroundColor(Color.GRAY);//周二
	    		break;
	    	case 4:wednesday.setBackgroundColor(Color.GRAY);//周三
	    		break;
	    	case 5:thursday.setBackgroundColor(Color.GRAY);//周四
	    		break;
	    	case 6:friday.setBackgroundColor(Color.GRAY);//周五
	    		break;
	    	case 7:saturday.setBackgroundColor(Color.GRAY);//周六
    			break;
	    	
	    	}
	    }
	    //不为当前周，设置背景为透明
	    else
	    {
	    	curmonth.setBackgroundColor(Color.TRANSPARENT);//月份
	    	sunday.setBackgroundColor(Color.TRANSPARENT);//周日
	    	saturday.setBackgroundColor(Color.TRANSPARENT);//周六
	    	monday.setBackgroundColor(Color.TRANSPARENT);//周一
	    	tuesday.setBackgroundColor(Color.TRANSPARENT);//周二
	    	wednesday.setBackgroundColor(Color.TRANSPARENT);//周三
	    	thursday.setBackgroundColor(Color.TRANSPARENT);//周四
	    	friday.setBackgroundColor(Color.TRANSPARENT);//周五
	    }
	}
	//初始化listview
	public void initListView()
	{
		listview=(ListView)this.findViewById(R.id.listView1);
        listview.setAdapter(new WholeWeekAdapter(WholeWeekActivity.this));//为ListView设置内容适配器
	}
}
