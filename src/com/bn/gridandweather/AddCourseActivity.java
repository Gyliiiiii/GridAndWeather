package com.bn.gridandweather;

import com.bn.adapter.MyBaseAdapter;
import com.bn.util.Constant;
import com.bn.util.SQLiteUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class AddCourseActivity extends Activity{
	EditText et1,et2,et3,et4,et5,et6;//��ʾȫ���γ���Ϣ��EditText
	LinearLayout ll;
	Spinner sp3,sp4;//��ʾ�γ̽����Ϳγ�����
	String weeks[]=new String[2];
	String selectNum="";//ѡ��γ̽���
	String selectWeek="";//ѡ������
	final int COMMON_DIALOG0=0;//�����Ի����ID
	final int COMMON_DIALOG1=1;
	final int COMMON_DIALOG2=2;
	public AlertDialog.Builder builder = null;//���öԻ���
	String[] insertCourse=new String[3];//����Ŀγ����ơ���ʦ���ص�
	String[] insertTime=new String[3];//����Ŀγ̽���������������
	int click[]=new int[20];//������ť�ı��������
	Button[] bt=new Button[20];//��ʼ��20����ť
	String[] info=new String[20];//20����ť����ʾ���ַ���
	Button singleB,doubleB,allB;//���ء�ȷ�ϡ����ܡ�˫�ܡ�ȫѡ��ť
	boolean bb1=false;//�ж���ӿγ��Ƿ�ɹ�
	boolean bb2=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcource_activity);
		initEdit();
		initButton();
	}
	//���ȷ����ΰ�ť����Ҫ���е���Ӧ����
	public void initButton()
	{
		for(int i=0;i<3;i++)
		{
			insertCourse[i]="";
			insertTime[i]="";
		}
		Button bt=(Button)this.findViewById(R.id.button1);//��ΰ�ť
		bt.setOnClickListener(
				new OnClickListener(){
					@Override
					public void onClick(View v){
						bb1=false;
						bb2=false;
						insertCourse=new String[3];//��ÿγ���Ϣ
						insertCourse[0]=et1.getText().toString();
						insertCourse[1]=et2.getText().toString();
						insertCourse[2]=et3.getText().toString();
						System.out.println("insertMess:"+insertCourse[0]);
						//�����ӵĿγ̲����ڲ���ÿ�����ݲ�Ϊ��
						if(SQLiteUtil.QueryCourseIfExist(insertCourse[0])||((!insertCourse[0].trim().equals(""))&&
								(!insertCourse[1].trim().equals(""))&&(!insertCourse[2].trim().equals("")))){
							SQLiteUtil.insertCourceMess(insertCourse);//��Ӵ˿γ̽����ݿ�
							bb1=true;
						}
						insertTime=new String[3];//��ÿγ�ʱ������
						insertTime[0]=et4.getText().toString();
						insertTime[1]=Constant.editText;
						insertTime[2]=et6.getText().toString();
						if(insertTime[0].trim().equals("�������Ͽν���")||insertTime[1].trim().equals("�������Ͽ�����")||
								insertTime[2].trim().equals("�������Ͽ�����")||insertTime[0].trim().equals("")||
								insertTime[1].trim().equals("")||insertTime[2].trim().equals(""))
						{
							Toast.makeText(AddCourseActivity.this, "��ӵĿγ̲�����Ҫ����������ӣ�", Toast.LENGTH_SHORT).show();
							bb2=false;
						}else 
						{
							if(SQLiteUtil.QueryTimeIfExist(insertCourse[0], insertTime)&&SQLiteUtil.QueryCourseIfRepeat(insertTime)){//����˿γ̵Ĵ�ʱ���û�����
								SQLiteUtil.insertCourceTime(insertCourse[0],insertTime);//���˿γ̵�ʱ����ӽ����ݿ�
								bb2=true;
							}
						}
						if(bb1&&bb2){
							Toast.makeText(AddCourseActivity.this, "�˿γ���ӳɹ���", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(AddCourseActivity.this, "��ӵĿγ̲�����Ҫ����������ӣ�", Toast.LENGTH_SHORT).show();
						}
						MondayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"����һ"));
						TuesdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"���ڶ�"));
						WednesdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"������"));
						ThursdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"������"));
						FridayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"������"));
						SaturdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"������"));
						SundayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"������"));
					}	
				});
	}
	//��ʼ����εĸ�����Ϣ
	public void initEdit(){
		et1=(EditText)this.findViewById(R.id.editText1);
		et2=(EditText)this.findViewById(R.id.editText2);
		et3=(EditText)this.findViewById(R.id.editText3);
		et4=(EditText)this.findViewById(R.id.editText4);
		et5=(EditText)this.findViewById(R.id.editText5);
		et6=(EditText)this.findViewById(R.id.editText6);
    	et1.setOnTouchListener(//��д�γ�����
			new OnTouchListener(){
				@Override
			    public boolean onTouch(View v, MotionEvent event) {
			        et1.setText("");
			        return false;
			     }
		});
    	et2.setOnTouchListener(//��д�γ̽�ʦ
    			new OnTouchListener(){
    				@Override
    			    public boolean onTouch(View v, MotionEvent event){
    			        et2.setText("");
    			        return false;
    			     }
    		});
    	et3.setOnTouchListener(//��д�γ̵ص�
    			new OnTouchListener(){
    				@Override
    			    public boolean onTouch(View v, MotionEvent event) {
    			        et3.setText("");
    			        return false;
    			     }
    		});
    	et4.setOnTouchListener(//��д�γ̽���
    			new OnTouchListener(){
					@Override
    			    public boolean onTouch(View v, MotionEvent event) {
    					et4.setText("");
    					showDialog(COMMON_DIALOG0);
    			        return false;
    			     }
    		});
    	et5.setOnTouchListener(//��д�γ�����
    			new OnTouchListener(){
					@Override
    			    public boolean onTouch(View v, MotionEvent event){
    			        et5.setText("");
    			        showDialog(COMMON_DIALOG1);
    			        return false;
    			     }
    		});
    	et6.setOnTouchListener(//��д�γ�����
    			new OnTouchListener(){
					@Override
    			    public boolean onTouch(View v, MotionEvent event) {
    			        et6.setText("");
    			        showDialog(COMMON_DIALOG2);
    			        return false;
    			     }
    		});
	}
	
	//����Ͽ�����
	public void initWeeksSpinner(final LayoutInflater inflater)
	{
		for(int i=0;i<bt.length;i++){
			bt[i].setOnClickListener(
					new MyOnClickListener(this,i)//�Զ��尴ť�����������
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
	//�������
	public void initDaysSpinner(final LayoutInflater inflater)
	{
		 //ΪSpinner׼������������
		BaseAdapter ba1=new BaseAdapter()
		 {
			 @Override
			 public int getCount() {
				 return Constant.weekId.length;//7��
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
	public void initCource(final LayoutInflater inflater)//����Ͽν���
	{
		
		 //ΪSpinner׼������������
		 BaseAdapter ba2=new BaseAdapter()
		 {
			 @Override
			 public int getCount() {
				 return Constant.nums.length;//�ܹ�7��
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
				 tv.setText(Constant.nums[arg0]);//��������
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
	public static String DivideString(String msg){//���������ַ����ĳɴ�-���ַ���
		String result="";
		String[] group=msg.split(",");
		int last = Integer.parseInt(group[0]);
		int first = last;
		for(int i=1;i<group.length;i++){
			if(Integer.parseInt(group[i]) != last + 1){
				if(first!=last){
					result+=first + "-" + group[i - 1]+",";
				}else{
					result+=first+",";
				}
				first = Integer.parseInt(group[i]);
			}
			last = Integer.parseInt(group[i]);
		}
		if(first!=last){
			result+=first + "-" + group[group.length - 1];
		}else{
			result+=first;
		}		
		return result;
	 }
	//��������Ի���
	@SuppressLint("ResourceAsColor")
	public Dialog onCreateDialog(int id){
		Dialog dialog=null;
		final LayoutInflater inflater = LayoutInflater.from(AddCourseActivity.this);  
		switch(id)
		{
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
    				 for(int i=0;i<info.length;i++){
    					 if(!info[i].equals("")){
    						 Constant.editText+=info[i].trim()+",";
    					 }
    				 }
    				 if(Constant.editText.length() != 0){
    					 Constant.editText=Constant.editText.substring(0,Constant.editText.length()-1);
    					 Constant.showWeeks=DivideString(Constant.editText);
    				 }
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
}
class MyOnClickListener implements OnClickListener{//�Զ��尴ť����ļ�����
	AddCourseActivity ta;
	int kk=0;
	public MyOnClickListener(AddCourseActivity ta,int kk){
		this.ta=ta;
		this.kk=kk;
	}
	@Override
	public void onClick(View v){
		ta.click[kk]++;
		if(ta.click[kk]%2!=0){	//�����������ʱ����Ϊѡ��״̬
			ta.bt[kk].setBackgroundResource(R.color.yellow1);
			ta.info[kk]=ta.bt[kk].getText().toString();
		}else{		//�����ż����ʱ����Ϊδѡ��״̬
			ta.bt[kk].setBackgroundResource(R.color.white);
			ta.info[kk]="";
		}
	}
}

