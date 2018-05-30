package com.bn.gridandweather;

import com.bn.adapter.MyBaseAdapter;
import com.bn.gridandweather.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SaturdayActivity extends Activity
{
	Context context=null;
	static ListView listview;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_activity);
		
		context=SaturdayActivity.this;
		listview=(ListView)this.findViewById(R.id.listView1);
		listview.setAdapter(new MyBaseAdapter(context,"������"));
		listview.setOnItemClickListener(//��ListView ��Ӽ���
    			new OnItemClickListener()
    			{
    				@Override
    				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
    				{//��дѡ������¼��Ĵ�����
    					String msg=MyBaseAdapter.allinfo5[arg2];
    					if(msg==null||msg.trim().equals(""))
    					{
    						Toast.makeText(SaturdayActivity.this, "��ʱ���û�пγ̣�", Toast.LENGTH_SHORT).show();
    					}
    					else
    					{
    						Intent intent=new Intent();
        					intent.setClass(SaturdayActivity.this,OddListSelectedActivity.class);
        					intent.putExtra("info", msg);	
        					SaturdayActivity.this.startActivity(intent);
    					}
    				}        	   
       });
	}
}
