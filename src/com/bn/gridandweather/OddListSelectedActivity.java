package com.bn.gridandweather;

import com.bn.adapter.MyBaseAdapter;
import com.bn.util.Constant;
import com.bn.util.SQLiteUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OddListSelectedActivity extends Activity{
	TextView tv1,tv2,tv3,tv4,tv5,tv6,title;//���ȫ���γ���Ϣ
	Button edit,back,delete;//�༭�����ء�ɾ����ť
	String value="";//Intent����ֵ
	String[] names;//��ÿγ���
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.odd_list_selected);
		
		initButton();//��ʼ����ť
		initTextView();//��ʼ��TextView
	}
	public void initButton()
	{
		edit=(Button)this.findViewById(R.id.edit);//�༭��ť
		back=(Button)this.findViewById(R.id.back);//���ذ�ť
		delete=(Button)this.findViewById(R.id.delete);//ɾ����ť
		edit.setOnClickListener(//���༭��ť��Ӽ���
				new OnClickListener()
				{
					public void onClick(View v)
					{
						Intent intent=new Intent();
						intent.setClass(OddListSelectedActivity.this, EditCourseActivity.class);
						intent.putExtra("name",value);
						OddListSelectedActivity.this.startActivity(intent);
					}
				});
		back.setOnClickListener(//�����ذ�ť��Ӽ���
				new OnClickListener()
				{
					public void onClick(View v)
					{
						Intent intent=new Intent();
						intent.setClass(OddListSelectedActivity.this, OddDaysActivity.class);
						OddListSelectedActivity.this.startActivity(intent);
					}
				});
		delete.setOnClickListener(//��ɾ�����ڿΰ�ť��Ӽ���
				new OnClickListener()
				{
					public void onClick(View v)
					{
						SQLiteUtil.DeleteCourse(names);
						Intent intent=new Intent();
						intent.setClass(OddListSelectedActivity.this, OddDaysActivity.class);
						OddListSelectedActivity.this.startActivity(intent);
						MondayActivity.listview.setAdapter(new MyBaseAdapter(OddListSelectedActivity.this,"����һ"));
						TuesdayActivity.listview.setAdapter(new MyBaseAdapter(OddListSelectedActivity.this,"���ڶ�"));
						WednesdayActivity.listview.setAdapter(new MyBaseAdapter(OddListSelectedActivity.this,"������"));
						ThursdayActivity.listview.setAdapter(new MyBaseAdapter(OddListSelectedActivity.this,"������"));
						FridayActivity.listview.setAdapter(new MyBaseAdapter(OddListSelectedActivity.this,"������"));
						SaturdayActivity.listview.setAdapter(new MyBaseAdapter(OddListSelectedActivity.this,"������"));
						SundayActivity.listview.setAdapter(new MyBaseAdapter(OddListSelectedActivity.this,"������"));
					}
				});
	}
	public void initTextView(){
		title=(TextView)this.findViewById(R.id.textshow);
		tv1=(TextView)this.findViewById(R.id.nameT01);
		tv2=(TextView)this.findViewById(R.id.teacherT01);
		tv3=(TextView)this.findViewById(R.id.addressT01);
		tv4=(TextView)this.findViewById(R.id.numT01);
		tv5=(TextView)this.findViewById(R.id.weeksT01);
		tv6=(TextView)this.findViewById(R.id.weekT01);
		
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		//���ѡ����ı��
		value=bundle.getString("info");
		names=value.split("<#>");
		Constant.showWeeks=AddCourseActivity.DivideString(names[4]);
		title.setText(names[0]);//����
		tv1.append(":  "+names[0]);//�γ�����
		tv2.append(":  "+names[1]);//�γ̽�ʦ
		tv3.append(":  "+names[2]);//�γ̵ص�
		tv4.append(":  "+names[3]);//�γ̽���
		tv5.append(":  "+Constant.showWeeks);//�γ�����
		tv6.append(":  "+names[5]);//�γ�����
	}
}
