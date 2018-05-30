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
	Spinner spinner;//�����б�
	Button changeFace;//������ť
	Button oddDays;//���հ�ť
	LinearLayout rtlayout;
	ListView listview;
	//��ʱ����
	int index=0;
	//spinnerѡ�е�����
//	public static int weeksNum=1;
	public AlertDialog.Builder builder = null;
	TextView curmonth,monday,tuesday,thursday,wednesday,friday,saturday,sunday;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whole_week);
		
		rtlayout=(LinearLayout)this.findViewById(R.id.layout);
		
		initFace();//��ʼ��Ƥ��
		initButton();//��ʼ�����ܰ�ť
		changeTimes();//���ڵĸı�ʱ��
		initListView();//��ʼ��ListView
	}
	//��ʼ��Ƥ��
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
	//��ʼ�����ܰ�ť
	public void initButton()
	{
		//����
		oddDays=(Button)this.findViewById(R.id.odddays);
		//�������л�������
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
		//Ƥ��
		changeFace=(Button)this.findViewById(R.id.changeFace);
		changeFace.setOnClickListener(
    			new OnClickListener(){
    				@Override
    			    public void onClick(View v){
    					new AlertDialog.Builder(WholeWeekActivity.this)
						.setTitle("Ƥ��")
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
						.setNegativeButton("ȡ��", null)
						.show();
    			     }
    		});
		//ѡ������
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
	//���ڵĸı�ʱ��
	public void changeTimes()
	{
		//�·�
		curmonth=(TextView)this.findViewById(R.id.month);
		
		//����--�ܼ�
		monday=(TextView)this.findViewById(R.id.textView1);
		tuesday=(TextView)this.findViewById(R.id.textView2);
		wednesday=(TextView)this.findViewById(R.id.textView3);
		thursday=(TextView)this.findViewById(R.id.textView4);
	    friday=(TextView)this.findViewById(R.id.textView5);
		saturday=(TextView)this.findViewById(R.id.textView6);
		sunday=(TextView)this.findViewById(R.id.textView7);
		//���ԭ������
		monday.setText("");
		tuesday.setText("");
		wednesday.setText("");
		thursday.setText("");
		friday.setText("");
		saturday.setText("");
		sunday.setText("");
		//�����������
		int[] WholeWeekDate=DateUtil.wholeWeekDate(Constant.spinnerSelection+1);
		//�����·ݺ���һ--���յ�����
		curmonth.setText(WholeWeekDate[0]+"��");
	    monday.setText(WholeWeekDate[1]+"\n"+"��һ");
	    tuesday.setText(WholeWeekDate[2]+"\n"+"�ܶ�");
	    wednesday.setText(WholeWeekDate[3]+"\n"+"����");
	    thursday.setText(WholeWeekDate[4]+"\n"+"����");
	    friday.setText(WholeWeekDate[5]+"\n"+"����");
	    saturday.setText(WholeWeekDate[6]+"\n"+"����");
	    sunday.setText(WholeWeekDate[7]+"\n"+"����");
	    //��ǰ������ʾ�ܼ�
	    if(Constant.spinnerSelection+1==Constant.currtWeeksNum)
	    {
	    	System.out.println("WholeWeekActivity");
	    	switch(DateUtil.now_week)//��õ�ǰ����Ϊ�ܼ�
	    	{
	    	case 1:sunday.setBackgroundColor(Color.GRAY);//����
				break;
	    	case 2:monday.setBackgroundColor(Color.GRAY);//��һ
	    		break;
	    	case 3:tuesday.setBackgroundColor(Color.GRAY);//�ܶ�
	    		break;
	    	case 4:wednesday.setBackgroundColor(Color.GRAY);//����
	    		break;
	    	case 5:thursday.setBackgroundColor(Color.GRAY);//����
	    		break;
	    	case 6:friday.setBackgroundColor(Color.GRAY);//����
	    		break;
	    	case 7:saturday.setBackgroundColor(Color.GRAY);//����
    			break;
	    	
	    	}
	    }
	    //��Ϊ��ǰ�ܣ����ñ���Ϊ͸��
	    else
	    {
	    	curmonth.setBackgroundColor(Color.TRANSPARENT);//�·�
	    	sunday.setBackgroundColor(Color.TRANSPARENT);//����
	    	saturday.setBackgroundColor(Color.TRANSPARENT);//����
	    	monday.setBackgroundColor(Color.TRANSPARENT);//��һ
	    	tuesday.setBackgroundColor(Color.TRANSPARENT);//�ܶ�
	    	wednesday.setBackgroundColor(Color.TRANSPARENT);//����
	    	thursday.setBackgroundColor(Color.TRANSPARENT);//����
	    	friday.setBackgroundColor(Color.TRANSPARENT);//����
	    }
	}
	//��ʼ��listview
	public void initListView()
	{
		listview=(ListView)this.findViewById(R.id.listView1);
        listview.setAdapter(new WholeWeekAdapter(WholeWeekActivity.this));//ΪListView��������������
	}
}
