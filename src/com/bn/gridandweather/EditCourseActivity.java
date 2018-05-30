package com.bn.gridandweather;

import com.bn.adapter.MyBaseAdapter;
import com.bn.util.Constant;
import com.bn.util.SQLiteUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressWarnings("deprecation")
public class EditCourseActivity extends Activity
{
	EditText et1,et2,et3,et4,et5,et6;//��ʾȫ���γ���Ϣ��EditText
	Spinner sp3,sp4;//��ʾ�γ̽����Ϳγ�����
	Button back,ok,singleB,doubleB,allB;//���ء�ȷ�ϡ����ܡ�˫�ܡ�ȫѡ��ť
	Button[] bt=new Button[20];
	String[] updateMess=new String[3];//���¿γ̵�����
	String[] updateTime=new String[3];
	String[] info=new String[20];
	int[] click=new int[20];//������ť�ı��������
	String selectNum="";//ѡ��γ̽���
	String selectWeek="";//ѡ������
	String firstName="";//û�иı�֮ǰ�Ŀγ�����
	final int COMMON_DIALOG0=0;//�����Ի����ID
	final int COMMON_DIALOG1=1;
	final int COMMON_DIALOG2=2;
	public AlertDialog.Builder builder = null;//���öԻ���
	String[] ii=new String[6];
	Context context=null;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_course);
		
		context=EditCourseActivity.this;
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		//���ѡ����ı��
		String value=bundle.getString("name");
		ii=value.split("<#>");
		
		initEditText();
		initButton();
	}
	public void initEditText()
	{
		firstName=ii[0];//û�иı�֮ǰ�Ŀγ�����
		Constant.editText=ii[4];//ѡ�������
		Constant.showWeeks=AddCourseActivity.DivideString(ii[4]);
		et1=(EditText)this.findViewById(R.id.editText1);
		et1.setText(ii[0]);//�γ�����
		et2=(EditText)this.findViewById(R.id.editText2);
		et2.setText(ii[1]);//�γ̽�ʦ
		et3=(EditText)this.findViewById(R.id.editText3);
		et3.setText(ii[2]);//�γ̵ص�
		et4=(EditText)this.findViewById(R.id.editText4);
		et4.setText(ii[3]);//�γ̽���
		et5=(EditText)this.findViewById(R.id.editText5);
		et5.setText(Constant.showWeeks);//�γ�����
		et6=(EditText)this.findViewById(R.id.editText6);
		et6.setText(ii[5]);//�γ�����
		 
		et4.setOnTouchListener(
				new OnTouchListener(){
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						showDialog(COMMON_DIALOG0);
						return false;
					}
				});
		et5.setOnTouchListener(
				new OnTouchListener(){
					@Override
					public boolean onTouch(View v, MotionEvent event){
						showDialog(COMMON_DIALOG1);
						return false;
					}
				});
		et6.setOnTouchListener(
				new OnTouchListener(){
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						showDialog(COMMON_DIALOG2);
						return false;
					}
				});
	}
	@SuppressLint("ResourceAsColor")
	public Dialog onCreateDialog(int id){
		Dialog dialog=null;
		final LayoutInflater inflater = LayoutInflater.from(EditCourseActivity.this);  
		switch(id){
    	 case COMMON_DIALOG0://ѡ��γ̽���
    		 builder = new Builder(this);  
    		 View viewDialog0 = inflater.inflate(R.layout.courcespinner,null); 
    		 sp3=(Spinner)viewDialog0.findViewById(R.id.spinner1);
    		 builder.setView(viewDialog0);
    		 builder.setTitle("��ѡ��γ�ʱ�䰲��");
    		 builder.setNegativeButton("ȡ��",null);
    		 builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
    			 @Override
    			 public void onClick(DialogInterface arg0, int arg1) {
    				 et4.setText(selectNum);
			  }});
    		 initCource(inflater);
    		 dialog=builder.create();
    	 break;
    	 case COMMON_DIALOG1://ѡ��γ�����
    		 builder = new Builder(this);  
    		 View viewDialog1= inflater.inflate(R.layout.tableweekslayout,null); 
    		 singleB=(Button)viewDialog1.findViewById(R.id.danzhou);
    		 doubleB=(Button)viewDialog1.findViewById(R.id.shuangzhou);
    		 allB=(Button)viewDialog1.findViewById(R.id.quanxuan);
    		 for(int j=0;j<bt.length;j++){
    				bt[j]=(Button)viewDialog1.findViewById(Constant.buttonId[j]);
    				click[j]=0;
    				info[j]="";
    			}
    		 builder.setView(viewDialog1);
    		 builder.setTitle("��ѡ����������");
    		 builder.setNegativeButton("ȡ��",null);
    		 builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
    		 {
    			 @Override
    			 public void onClick(DialogInterface arg0, int arg1) {
    				 Constant.editText="";
    				 Constant.showWeeks="";
    				 for(int i=0;i<info.length;i++){
    					 if(!info[i].equals("")){
    						 Constant.editText+=info[i].trim()+",";
    					 }
    				 }
    				 Constant.showWeeks=AddCourseActivity.DivideString(Constant.editText);
    				 et5.setText(Constant.showWeeks);
			  }
    		 });
    		 initWeeksSpinner(inflater);
    		 dialog=builder.create();
    	 break;
    	 case COMMON_DIALOG2://ѡ��γ�����
    		 builder = new Builder(this);  
    		 View viewDialog2= inflater.inflate(R.layout.daysspinner,null); 
    		 sp4=(Spinner)viewDialog2.findViewById(R.id.spinner3);
    		 builder.setView(viewDialog2);
    		 builder.setTitle("��ѡ�����ڰ���");
    		 builder.setNegativeButton("ȡ��",null);
    		 builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
    			 @Override
    			 public void onClick(DialogInterface arg0, int arg1) {
    				 et6.setText(selectWeek);
			  }
    		 });
    		 initDaysSpinner(inflater);
    		 dialog=builder.create();
    	 break;
    	 }
		return dialog;
	}
	public void initButton()
	{
		back=(Button)this.findViewById(R.id.back);//���ذ�ť
		ok=(Button)this.findViewById(R.id.ok);//ȷ�ϰ�ť
		
		back.setOnClickListener(//�����ذ�ť��Ӽ���
				new OnClickListener()
				{
					public void onClick(View v)
					{
						Intent intent=new Intent();
						intent.setClass(EditCourseActivity.this, OddDaysActivity.class);
						EditCourseActivity.this.startActivity(intent);
					}
				});
		ok.setOnClickListener(//���¿γ�
				new OnClickListener()
				{
					public void onClick(View v)
					{
						updateMess[0]=et1.getText().toString();
						updateMess[1]=et2.getText().toString();
						updateMess[2]=et3.getText().toString();
						
						updateTime[0]=et4.getText().toString();
						updateTime[1]=Constant.editText;
						updateTime[2]=et6.getText().toString();
						SQLiteUtil.ReSetCource(firstName,updateMess,updateTime);
						Toast.makeText(EditCourseActivity.this, "�˿γ��Ѿ����£�", Toast.LENGTH_SHORT).show();
						
						MondayActivity.listview.setAdapter(new MyBaseAdapter(context,"����һ"));
						TuesdayActivity.listview.setAdapter(new MyBaseAdapter(context,"���ڶ�"));
						WednesdayActivity.listview.setAdapter(new MyBaseAdapter(context,"������"));
						ThursdayActivity.listview.setAdapter(new MyBaseAdapter(context,"������"));
						FridayActivity.listview.setAdapter(new MyBaseAdapter(context,"������"));
						SaturdayActivity.listview.setAdapter(new MyBaseAdapter(context,"������"));
						SundayActivity.listview.setAdapter(new MyBaseAdapter(context,"������"));
					}
				});
		
	}
	public void initWeeksSpinner(final LayoutInflater inflater)
	{
		for(int i=0;i<bt.length;i++){
			bt[i].setOnClickListener(
					new MyClickListener(this,i)//�Զ��尴ť�����������
					);
		}
		
		singleB.setOnClickListener(//������ܰ�ť
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						for(int j=0;j<click.length;j++){
							click[j]=0;
						}
						for(int k=0;k<bt.length;k++)
						{
							if(k%2==0){
								click[k]++;
								bt[k].setBackgroundResource(R.color.yellow1);
								info[k]=bt[k].getText().toString();
							}else{
								bt[k].setBackgroundResource(R.color.white);
								info[k]="";
							}
						}
					}	
				});
		doubleB.setOnClickListener(//���˫�ܰ�ť
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						for(int j=0;j<click.length;j++){
							click[j]=0;
						}
						for(int k=0;k<bt.length;k++)
						{
							if(k%2==0){
								bt[k].setBackgroundResource(R.color.white);
								info[k]="";
							}else{
								click[k]++;
								bt[k].setBackgroundResource(R.color.yellow1);
								info[k]=bt[k].getText().toString();
							}
						}
					}	
				});
		allB.setOnClickListener(//����ȫѡ��ť
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						for(int j=0;j<click.length;j++){
							click[j]=0;
						}
						for(int k=0;k<bt.length;k++)
						{
							click[k]++;
							bt[k].setBackgroundResource(R.color.yellow1);
							info[k]=bt[k].getText().toString();
						}
					}	
				});
	}
	public void initDaysSpinner(final LayoutInflater inflater)
	{
		 //ΪSpinner׼������������
		BaseAdapter ba1=new BaseAdapter()
		 {
			 @Override
			 public int getCount() {
				 return Constant.weekId.length;//�ܹ�����ѡ��
			 }

			 @Override
			 public Object getItem(int arg0) { return null; }

			 @Override
			 public long getItemId(int arg0) { return 0; }

			 @Override
			 public View getView(int arg0, View arg1, ViewGroup arg2) {
				 LinearLayout ll=(LinearLayout)arg1;
				
				 if(ll==null){
					ll=(LinearLayout)(inflater.inflate(R.layout.spinnertext1,null).findViewById(R.id.listlayout));
				 }
				 //��ʼ��TextView
				 TextView tv=(TextView)ll.getChildAt(0);
				 tv.setTextSize(20);
				 tv.setText(Constant.weekId[arg0]);//��������
				 return ll;
			 }        	
		 };
     
		 sp4.setAdapter(ba1);//ΪSpinner��������������
		 //��������ѡ��ѡ�еļ�����
		 sp4.setOnItemSelectedListener(
		      new OnItemSelectedListener()
		      {
     	    	 @Override
     	    	 public void onItemSelected(AdapterView<?> arg0, View arg1,
     	    			 int arg2, long arg3) {//��дѡ�ѡ���¼��Ĵ�����
     	    		 selectWeek=getResources().getText(Constant.weekId[arg2])+"";
     	    	 }

     	    	 @Override
     	    	 public void onNothingSelected(AdapterView<?> arg0) { }        	   
		      }
		 );
	}
	public void initCource(final LayoutInflater inflater)
	{
		
		 //ΪSpinner׼������������
		 BaseAdapter ba2=new BaseAdapter()
		 {
			 @Override
			 public int getCount() {
				 return Constant.numId.length;//�ܹ�����ѡ��
			 }

			 @Override
			 public Object getItem(int arg0) { return null; }

			 @Override
			 public long getItemId(int arg0) { return 0; }

			 @Override
			 public View getView(int arg0, View arg1, ViewGroup arg2) {
				 LinearLayout ll=(LinearLayout)arg1;
				 if(ll==null)
				 {
   		  			ll=(LinearLayout)(inflater.inflate(R.layout.spinnertext2,null).findViewById(R.id.listlayout2));
   		  			}
				 //��ʼ��TextView
				 TextView tv=(TextView)ll.getChildAt(0);
				 tv.setText(Constant.numId[arg0]);//��������
				 tv.setTextSize(20);//���������С
				 return ll;
			 }        	
		 };
     
		 sp3.setAdapter(ba2);//ΪSpinner��������������
		 //���ý���ѡ��ѡ�еļ�����
		 sp3.setOnItemSelectedListener(
		       new OnItemSelectedListener()
			   {
		    	   @Override
		    	   public void onItemSelected(AdapterView<?> arg0, View arg1,
		    			   int arg2, long arg3) {//��дѡ�ѡ���¼��Ĵ�����
		    		   selectNum=getResources().getText(Constant.numId[arg2])+"";
		    	   }
		    	   @Override
		    	   public void onNothingSelected(AdapterView<?> arg0) { }        	   
			   }
		 );
	}
}


class MyClickListener implements OnClickListener{//�Զ��尴ť�ļ�������
	EditCourseActivity ta;
	int kk=0;
	public MyClickListener(EditCourseActivity ta,int kk){
		this.ta=ta;
		this.kk=kk;}
	@Override
	public void onClick(View v){
		ta.click[kk]++;
		if(ta.click[kk]%2!=0){
			ta.bt[kk].setBackgroundResource(R.color.yellow1);
			ta.info[kk]=ta.bt[kk].getText().toString();
		}else{
			ta.bt[kk].setBackgroundResource(R.color.white);
			ta.info[kk]="";
		}
	}
}
