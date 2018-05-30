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
	EditText et1,et2,et3,et4,et5,et6;//显示全部课程信息的EditText
	LinearLayout ll;
	Spinner sp3,sp4;//显示课程节数和课程星期
	String weeks[]=new String[2];
	String selectNum="";//选择课程节数
	String selectWeek="";//选择星期
	final int COMMON_DIALOG0=0;//各个对话框的ID
	final int COMMON_DIALOG1=1;
	final int COMMON_DIALOG2=2;
	public AlertDialog.Builder builder = null;//设置对话框
	String[] insertCourse=new String[3];//插入的课程名称、教师、地点
	String[] insertTime=new String[3];//插入的课程节数、周数、星期
	int click[]=new int[20];//各个按钮的被点击次数
	Button[] bt=new Button[20];//初始化20个按钮
	String[] info=new String[20];//20个按钮上显示的字符串
	Button singleB,doubleB,allB;//返回、确认、单周、双周、全选按钮
	boolean bb1=false;//判断添加课程是否成功
	boolean bb2=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcource_activity);
		initEdit();
		initButton();
	}
	//点击确认添课按钮后需要进行的相应操作
	public void initButton()
	{
		for(int i=0;i<3;i++)
		{
			insertCourse[i]="";
			insertTime[i]="";
		}
		Button bt=(Button)this.findViewById(R.id.button1);//添课按钮
		bt.setOnClickListener(
				new OnClickListener(){
					@Override
					public void onClick(View v){
						bb1=false;
						bb2=false;
						insertCourse=new String[3];//获得课程信息
						insertCourse[0]=et1.getText().toString();
						insertCourse[1]=et2.getText().toString();
						insertCourse[2]=et3.getText().toString();
						System.out.println("insertMess:"+insertCourse[0]);
						//如果添加的课程不存在并且每项内容不为空
						if(SQLiteUtil.QueryCourseIfExist(insertCourse[0])||((!insertCourse[0].trim().equals(""))&&
								(!insertCourse[1].trim().equals(""))&&(!insertCourse[2].trim().equals("")))){
							SQLiteUtil.insertCourceMess(insertCourse);//添加此课程进数据库
							bb1=true;
						}
						insertTime=new String[3];//获得课程时间新型
						insertTime[0]=et4.getText().toString();
						insertTime[1]=Constant.editText;
						insertTime[2]=et6.getText().toString();
						if(insertTime[0].trim().equals("请输入上课节数")||insertTime[1].trim().equals("请输入上课周数")||
								insertTime[2].trim().equals("请输入上课星期")||insertTime[0].trim().equals("")||
								insertTime[1].trim().equals("")||insertTime[2].trim().equals(""))
						{
							Toast.makeText(AddCourseActivity.this, "添加的课程不符合要求，请重新添加！", Toast.LENGTH_SHORT).show();
							bb2=false;
						}else 
						{
							if(SQLiteUtil.QueryTimeIfExist(insertCourse[0], insertTime)&&SQLiteUtil.QueryCourseIfRepeat(insertTime)){//如果此课程的此时间段没有添加
								SQLiteUtil.insertCourceTime(insertCourse[0],insertTime);//将此课程的时间添加进数据库
								bb2=true;
							}
						}
						if(bb1&&bb2){
							Toast.makeText(AddCourseActivity.this, "此课程添加成功！", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(AddCourseActivity.this, "添加的课程不符合要求，请重新添加！", Toast.LENGTH_SHORT).show();
						}
						MondayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"星期一"));
						TuesdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"星期二"));
						WednesdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"星期三"));
						ThursdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"星期四"));
						FridayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"星期五"));
						SaturdayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"星期六"));
						SundayActivity.listview.setAdapter(new MyBaseAdapter(AddCourseActivity.this,"星期日"));
					}	
				});
	}
	//初始化添课的各项信息
	public void initEdit(){
		et1=(EditText)this.findViewById(R.id.editText1);
		et2=(EditText)this.findViewById(R.id.editText2);
		et3=(EditText)this.findViewById(R.id.editText3);
		et4=(EditText)this.findViewById(R.id.editText4);
		et5=(EditText)this.findViewById(R.id.editText5);
		et6=(EditText)this.findViewById(R.id.editText6);
    	et1.setOnTouchListener(//填写课程名称
			new OnTouchListener(){
				@Override
			    public boolean onTouch(View v, MotionEvent event) {
			        et1.setText("");
			        return false;
			     }
		});
    	et2.setOnTouchListener(//填写课程教师
    			new OnTouchListener(){
    				@Override
    			    public boolean onTouch(View v, MotionEvent event){
    			        et2.setText("");
    			        return false;
    			     }
    		});
    	et3.setOnTouchListener(//填写课程地点
    			new OnTouchListener(){
    				@Override
    			    public boolean onTouch(View v, MotionEvent event) {
    			        et3.setText("");
    			        return false;
    			     }
    		});
    	et4.setOnTouchListener(//填写课程节数
    			new OnTouchListener(){
					@Override
    			    public boolean onTouch(View v, MotionEvent event) {
    					et4.setText("");
    					showDialog(COMMON_DIALOG0);
    			        return false;
    			     }
    		});
    	et5.setOnTouchListener(//填写课程周数
    			new OnTouchListener(){
					@Override
    			    public boolean onTouch(View v, MotionEvent event){
    			        et5.setText("");
    			        showDialog(COMMON_DIALOG1);
    			        return false;
    			     }
    		});
    	et6.setOnTouchListener(//填写课程星期
    			new OnTouchListener(){
					@Override
    			    public boolean onTouch(View v, MotionEvent event) {
    			        et6.setText("");
    			        showDialog(COMMON_DIALOG2);
    			        return false;
    			     }
    		});
	}
	
	//获得上课周数
	public void initWeeksSpinner(final LayoutInflater inflater)
	{
		for(int i=0;i<bt.length;i++){
			bt[i].setOnClickListener(
					new MyOnClickListener(this,i)//自定义按钮点击监听方法
					);
		}
		
		singleB.setOnClickListener(//点击单周按钮
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
		doubleB.setOnClickListener(//点击双周按钮
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
		allB.setOnClickListener(//单击全选按钮
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
	//获得星期
	public void initDaysSpinner(final LayoutInflater inflater)
	{
		 //为Spinner准备内容适配器
		BaseAdapter ba1=new BaseAdapter()
		 {
			 @Override
			 public int getCount() {
				 return Constant.weekId.length;//7天
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
				 //初始化TextView
				 TextView tv=(TextView)ll.getChildAt(0);
				 tv.setTextSize(20);
				 tv.setText(Constant.weekId[arg0]);//设置内容
				 return ll;
			 }        	
		 };
		 sp4.setAdapter(ba1);//为Spinner设置内容适配器
		 //设置星期选项选中的监听器
		 sp4.setOnItemSelectedListener(
		      new OnItemSelectedListener()
		      {
     	    	 @Override
     	    	 public void onItemSelected(AdapterView<?> arg0, View arg1,
     	    			 int arg2, long arg3) {//重写选项被选中事件的处理方法
     	    		 selectWeek=getResources().getText(Constant.weekId[arg2])+"";
     	    	 }

     	    	 @Override
     	    	 public void onNothingSelected(AdapterView<?> arg0) { }        	   
		      }
		 );
	}
	public void initCource(final LayoutInflater inflater)//获得上课节数
	{
		
		 //为Spinner准备内容适配器
		 BaseAdapter ba2=new BaseAdapter()
		 {
			 @Override
			 public int getCount() {
				 return Constant.nums.length;//总共7项
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
				 //初始化TextView
				 TextView tv=(TextView)ll.getChildAt(0);
				 tv.setText(Constant.nums[arg0]);//设置内容
				 tv.setTextSize(20);//设置字体大小
				 return ll;
			 }        	
		 };
		 sp3.setAdapter(ba2);//为Spinner设置内容适配器
		 //设置节数选项选中的监听器
		 sp3.setOnItemSelectedListener(
		       new OnItemSelectedListener()
			   {
		    	   @Override
		    	   public void onItemSelected(AdapterView<?> arg0, View arg1,
		    			   int arg2, long arg3) {//重写选项被选中事件的处理方法
		    		   selectNum=getResources().getText(Constant.numId[arg2])+"";
		    	   }
		    	   @Override
		    	   public void onNothingSelected(AdapterView<?> arg0) { }        	   
			   }
		 );
	}
	public static String DivideString(String msg){//将带，的字符串改成带-的字符串
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
	//弹出各项对话框
	@SuppressLint("ResourceAsColor")
	public Dialog onCreateDialog(int id){
		Dialog dialog=null;
		final LayoutInflater inflater = LayoutInflater.from(AddCourseActivity.this);  
		switch(id)
		{
    	 case COMMON_DIALOG0://选择课程节数
    		 builder = new Builder(this);  
    		 View viewDialog0 = inflater.inflate(R.layout.courcespinner,null); 
    		 sp3=(Spinner)viewDialog0.findViewById(R.id.spinner1);
    		 builder.setView(viewDialog0);
    		 builder.setTitle("请选择课程时间安排");
    		 builder.setNegativeButton("取消",null);
    		 builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
    			 @Override
    			 public void onClick(DialogInterface arg0, int arg1) {
    				 et4.setText(selectNum);
			  }});
    		 initCource(inflater);
    		 dialog=builder.create();
    	 break;
    	 case COMMON_DIALOG1://选择课程周数
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
    		 builder.setTitle("请选择周数安排");
    		 builder.setNegativeButton("取消",null);
    		 builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
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
    	 case COMMON_DIALOG2://选择课程星期
    		 builder = new Builder(this);  
    		 View viewDialog2= inflater.inflate(R.layout.daysspinner,null); 
    		 sp4=(Spinner)viewDialog2.findViewById(R.id.spinner3);
    		 builder.setView(viewDialog2);
    		 builder.setTitle("请选择星期安排");
    		 builder.setNegativeButton("取消",null);
    		 builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
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
class MyOnClickListener implements OnClickListener{//自定义按钮点击的监听类
	AddCourseActivity ta;
	int kk=0;
	public MyOnClickListener(AddCourseActivity ta,int kk){
		this.ta=ta;
		this.kk=kk;
	}
	@Override
	public void onClick(View v){
		ta.click[kk]++;
		if(ta.click[kk]%2!=0){	//当点击奇数次时设置为选中状态
			ta.bt[kk].setBackgroundResource(R.color.yellow1);
			ta.info[kk]=ta.bt[kk].getText().toString();
		}else{		//当点击偶数次时设置为未选中状态
			ta.bt[kk].setBackgroundResource(R.color.white);
			ta.info[kk]="";
		}
	}
}

