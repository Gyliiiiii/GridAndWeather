package com.bn.gridandweather;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import com.bn.adapter.MyBaseAdapter;
import com.bn.adapter.MyPagerAdapter;
import com.bn.gridandweather.R;
import com.bn.util.Constant;
import com.bn.util.DateUtil;
import com.bn.util.JSonInfoUtil;
import com.bn.util.SQLiteUtil;
import com.bn.util.TQYBInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("HandlerLeak")
@SuppressWarnings("deprecation")
public class OddDaysActivity extends Activity 
{
	String[] cities={"济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽"};//可查看天气的城市
	static Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,addCourse,wholeWeek,title;
	Intent intent,intent2,intent3,intent4,intent5,intent6,intent7;
    static Context context=null;
    private static ViewPager pager;
	LocalActivityManager manager=null;
	int index=0,xingqi,week=-1;
	ArrayList<View> list;
	TextView city,changeCity,tv;
	String MyCityName=null;
	Resources res;
	Bitmap bitmap;
	Spinner spinner;
	String[] num;
	String jStr;
	String[] week1={"周日","周一","周二","周三","周四","周五","周六"};
	String[] week2={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	String msg="唐山";
	String str[]=new String[4];//获得天气信息
	String cweek="";//获得今天是星期几
	String allmess[][]=new String[5][5];//获得显示在Widget上的课程信息
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.odd_days);
		
		context=OddDaysActivity.this;
		manager= new LocalActivityManager(this,true);
		manager.dispatchCreate(savedInstanceState);
		pager=(ViewPager)this.findViewById(R.id.viewpager);

		//打开数据库
		SQLiteUtil.createCource();//创建课程表
		SQLiteUtil.createCourceTime();//创建课程时间表
		initCourseWidget();//初始化课程表格
		initSkin();//初始化皮肤
		initWeek();//初始化星期
		initWeeksButton();//初始化星期按钮
		initFunctionButton();//初始化功能按钮
		initPagerViewer();//初始化ViewPager
	}
	public void initSkin()//初始化皮肤
	{
		switch(Constant.skinsSelection)
		{	 
			case 0:pager.setBackgroundResource(R.color.white);
    		break;
    		case 1:pager.setBackgroundResource(R.drawable.netskin);
    		break;
    		case 2:pager.setBackgroundResource(R.drawable.flower);
    		break;
    		case 3:pager.setBackgroundResource(R.drawable.fengche);
    		break;
    		case 4:pager.setBackgroundResource(R.drawable.bear);
    		break;
    		case 5:pager.setBackgroundResource(R.drawable.butterfly);
    		break;
    		case 6:pager.setBackgroundResource(R.drawable.green);
    		break;
		}
	}
	public void initWeek()//初始化星期,默认选中
	{
		final Calendar c = Calendar.getInstance();  
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
        xingqi=Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_WEEK))); 
        switch(xingqi)
        {
        case 1:week=6;break;//周日
        case 2:week=0;break;//周一
        case 3:week=1;break;//周二
        case 4:week=2;break;//周三
        case 5:week=3;break;//周四
        case 6:week=4;break;//周五
        case 7:week=5;break;//周六
        }
	}
	//将课程显示到桌面上
	public void initCourseWidget(){
		String cnums=DateUtil.currtWeeks()+"";//获得当前是第几周
		System.out.println("cnums   "+cnums);
		for(int i=0;i<week1.length;i++){//转换星期信息格式
			if(cweek.equals(week1[i])){
				cweek=week2[i];
			}
		}
		List<String[]> list=SQLiteUtil.QueryWidgetMess(cweek,cnums);//从数据库获得相应课程信息
		if(list!=null){
			String[][] mess = new String[list.size()][list.get(0).length];
			int k=0;
			int x=0;
			for(String[] s:list)
			{
				String[] aa=new String[4];
				for(String ss:s)
				{
					aa[k]=ss;
					k++;
				}
				k=0;
				String[] divide=aa[3].split(",");//将周数信息转换成需要的格式
				for(int z=0;z<divide.length;z++){
					if(cnums.equals(divide[z]))
					{
						mess[x]=aa;
						x++;
					}
				}
			}
			int a=0;
			for(int i=0;i<mess.length;i++)
			{
				int b=0;
				for(int j=0;j<mess[i].length;j++)
				{
					if(mess[i][j]!=null)
					{
						allmess[a][b]=mess[i][j];
						b++;
					}
				}
				a++;
			}
			for(int i=0;i<allmess.length;i++)//当获得信息为空时，避免传Intent时出错，将其转换成需要格式
			{
				for(int j=0;j<allmess[i].length;j++)
				{
					if(allmess[i][j]==null)
					{
						allmess[i][j]="";
					}
				}
			}
			for(int i=1;i<=5;i++)//依次发送课程信息的Intent
			{
						Intent intentm = new Intent("wyf.action.tc"+i+"");
						intentm.putExtra("tc"+i+"",allmess[i-1][1]);
						OddDaysActivity.this.sendBroadcast(intentm);
						
						Intent intentn = new Intent("wyf.action.tnum"+i+"");
						intentn.putExtra("tnum"+i+"",allmess[i-1][0]);
						OddDaysActivity.this.sendBroadcast(intentn);
						
						Intent intentp = new Intent("wyf.action.tp"+i+"");
						intentp.putExtra("tp"+i+"",allmess[i-1][2]);
						OddDaysActivity.this.sendBroadcast(intentp);
				}
			}
		else//若该天没课，则显示"还没添加任何课程"
		{
			String[] mess=new String[5];
			for(int i=0;i<mess.length;i++)
			{
				mess[i]="还没添加任何课程";
			}
			for(int i=1;i<=mess.length;i++)
			{
				Intent intentm = new Intent("wyf.action.tc"+i+"");
				intentm.putExtra("tc"+i+"",mess[i-1]);
				OddDaysActivity.this.sendBroadcast(intentm);
			}
		}
	}
	//初始化功能按钮
	public void initFunctionButton()
	{
		addCourse=(Button)this.findViewById(R.id.addclass);//添课按钮
		addCourse.setOnClickListener(//给添课按钮添加监听
				new OnClickListener()
				{
					public void onClick(View v)
					{
						Intent intent=new Intent();
						intent.setClass(OddDaysActivity.this, AddCourseActivity.class);
						OddDaysActivity.this.startActivity(intent);
					}
				});
		//整周
		wholeWeek=(Button)this.findViewById(R.id.oneweek);
		wholeWeek.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						Intent intent=new Intent();
						intent.setClass(OddDaysActivity.this, WholeWeekActivity.class);
						OddDaysActivity.this.startActivity(intent);
					}
				}
				); 
		
		spinner=(Spinner)this.findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Constant.spinnerInfo);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(Constant.spinnerSelection);
		spinner.setOnItemSelectedListener(//给Spinner添加监听
				new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
					{
						switch(arg2){
						case 0:Constant.weeksNum=1;break;//选择第一周
						case 1:Constant.weeksNum=2;break;//选择第二周
						case 2:Constant.weeksNum=3;break;//选择第三周
						case 3:Constant.weeksNum=4;break;//选择第四周
						case 4:Constant.weeksNum=5;break;//选择第五周
						case 5:Constant.weeksNum=6;break;//选择第六周
						case 6:Constant.weeksNum=7;break;//选择第七周
						case 7:Constant.weeksNum=8;break;//选择第八周
						case 8:Constant.weeksNum=9;break;//选择第九周
						case 9:Constant.weeksNum=10;break;//选择第十周
						case 10:Constant.weeksNum=11;break;//选择第十一周
						case 11:Constant.weeksNum=12;break;//选择第十二周
						case 12:Constant.weeksNum=13;break;//选择第十三周
						case 13:Constant.weeksNum=14;break;//选择第十四周
						case 14:Constant.weeksNum=15;break;//选择第十五周
						case 15:Constant.weeksNum=16;break;//选择第十六周
						case 16:Constant.weeksNum=17;break;//选择第十七周
						case 17:Constant.weeksNum=18;break;//选择第十八周
						case 18:Constant.weeksNum=19;break;//选择第十九周
						case 19:Constant.weeksNum=20;break;//选择第二十周
						}
						Constant.spinnerSelection=Constant.weeksNum-1;
						MondayActivity.listview.setAdapter(new MyBaseAdapter(context,"星期一"));
						TuesdayActivity.listview.setAdapter(new MyBaseAdapter(context,"星期二"));
						WednesdayActivity.listview.setAdapter(new MyBaseAdapter(context,"星期三"));
						ThursdayActivity.listview.setAdapter(new MyBaseAdapter(context,"星期四"));
						FridayActivity.listview.setAdapter(new MyBaseAdapter(context,"星期五"));
						SaturdayActivity.listview.setAdapter(new MyBaseAdapter(context,"星期六"));
						SundayActivity.listview.setAdapter(new MyBaseAdapter(context,"星期日"));
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				}
				);
		city=(TextView)this.findViewById(R.id.city);
        changeCity=(TextView)this.findViewById(R.id.changecity);
        changeCity.setOnClickListener(//切换城市的监听方法
        		new OnClickListener(){
					@Override
					public void onClick(View v)
					{
						new AlertDialog.Builder(OddDaysActivity.this)
						.setTitle("请选择要查看的城市")
						.setIcon(android.R.drawable.ic_dialog_info)                
						.setSingleChoiceItems(cities, index,
						  new DialogInterface.OnClickListener() 
						  {              
						     public void onClick(DialogInterface dialog, int which)
						     {
						    	 index=which;
						    	 city.setText(cities[which]);
						    	 msg=city.getText().toString();
						    	 Intent intents = new Intent("wyf.action.city");
						    	 intents.putExtra("ccity", msg);
						    	 OddDaysActivity.this.sendBroadcast(intents);
						    	 try{
						    		 initThread();//从网络获取天气信息
						 	     }catch(Exception e){
						 	    	e.printStackTrace();
						 	     }
						         dialog.dismiss();
						      }
						   })
						.setNegativeButton("取消", null)
						.show();
					}
        		});
        try{
        	initThread();//从网络获取天气信息
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	public void initThread()//从网络获取天气信息
	{
		MyCityName=city.getText().toString();
		new Thread()
		{
			public void run()
			{
				//根据城市名称得到相关的天气信息
				try {
					jStr=JSonInfoUtil.getJSonStr(MyCityName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}.start();
	}
	//初始化 ViewPager
	private void initPagerViewer() 
	{ 
		//将各个主要的Activity放在list中 传到其适配器中
        list = new ArrayList<View>();
        intent = new Intent(context, MondayActivity.class);//周一
        list.add(getView("MondayActivity", intent));
        intent2 = new Intent(context, TuesdayActivity.class);//周二
        list.add(getView("TuesdayActivity", intent2));
        intent3 = new Intent(context, WednesdayActivity.class);//周三 
        list.add(getView("WednesdayActivity", intent3));
        intent4 = new Intent(context, ThursdayActivity.class);//周四
        list.add(getView("ThursdayActivity", intent4));
        intent5 = new Intent(context, FridayActivity.class);//周五
        list.add(getView("FridayActivity", intent5));
        intent6 = new Intent(context, SaturdayActivity.class);//周六
        list.add(getView("SaturdayActivity", intent6));
        intent7 = new Intent(context, SundayActivity.class);//周日
        list.add(getView("SundayActivity", intent7));
        
        pager.setAdapter(new MyPagerAdapter(list));//将list传到其适配器中
        pager.setCurrentItem(week);
        //添加监听 切Activity时
        pager.setOnPageChangeListener(
        		new  OnPageChangeListener(){
        			@Override  
            	    public void onPageScrollStateChanged(int arg0) 
        			{   
            	    }
            	    @Override  
            	    public void onPageScrolled(int arg0, float arg1, int arg2) 
            	    {  
            	    }
            	    @Override  
            	    public void onPageSelected(int arg0) 
            	    {
            	    	initCourseWidget();
            	    	changeButton(arg0);
            	    }
        		});
	 }
	//设置皮肤
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuItem ok=menu.add(0, 0, 0,"设置皮肤");
    	OnMenuItemClickListener lsn=new OnMenuItemClickListener()
    	{//实现菜单项点击事件监听接口
			@Override
			public boolean onMenuItemClick(MenuItem item) 
			{ 
				new AlertDialog.Builder(OddDaysActivity.this)
				.setTitle("皮肤")
				.setIcon(android.R.drawable.ic_dialog_info)                
				.setSingleChoiceItems(Constant.skins, 0,
				  new DialogInterface.OnClickListener() 
				  {              
				     public void onClick(DialogInterface dialog, int which)
				     {  	
				    	 try{
				    		 switch(which){ 
				    		 case 0:pager.setBackgroundResource(R.color.white);
				    		 Constant.skinsSelection=0;
				    		 break;
				    		 case 1:pager.setBackgroundResource(R.drawable.netskin);
				    		 Constant.skinsSelection=1;
				    		 break;
				    		 case 2:pager.setBackgroundResource(R.drawable.flower);
				    		 Constant.skinsSelection=2;
				    		 break;
				    		 case 3:pager.setBackgroundResource(R.drawable.fengche);
				    		 Constant.skinsSelection=3;
				    		 break;
				    		 case 4:pager.setBackgroundResource(R.drawable.bear);
				    		 Constant.skinsSelection=4;
				    		 break;
				    		 case 5:pager.setBackgroundResource(R.drawable.butterfly);
				    		 Constant.skinsSelection=5;
				    		 break;
				    		 case 6:pager.setBackgroundResource(R.drawable.green);
				    		 Constant.skinsSelection=6;
				    		 break;
				    		 }
				 	     }catch(Exception e){
				 	    	e.printStackTrace();
				 	     }
				         dialog.dismiss();
				      }
				   })
				.setNegativeButton("取消", null)
				.show();
				return true;
			}    		
    	};
    	ok.setOnMenuItemClickListener(lsn);//给确定菜单项添加监听器 
    	return true;
	}
	public void initCityList(String city) throws Exception{
		TQYBInfo info=null;
		//解析读取需要的天气预报信息
		info=JSonInfoUtil.parseJSon(jStr);
		try{
			//获取并设置天气预报图标
			ImageView iv=(ImageView)findViewById(R.id.ImageView01);
			res=this.getResources();
			bitmap=BitmapFactory.decodeResource(res, info.pic);
			iv.setImageBitmap(bitmap);
			
			tv=(TextView)this.findViewById(R.id.date);
			tv.setText("日期:"+info.date);
			str[0]=tv.getText().toString();
			cweek=str[0].substring(3, 5);
			
			tv=(TextView)this.findViewById(R.id.weather);
			tv.setText("天气: "+info.tqms);
			str[1]=tv.getText().toString();
			
			tv=(TextView)this.findViewById(R.id.temperature);
			tv.setText("温度："+info.wd);
			str[2]=tv.getText().toString();
			
			tv=(TextView)this.findViewById(R.id.wind);
			tv.setText("风力风向："+info.flfx);
			initCourseWidget();
			
		}catch(Exception e){
			String msg="当前此城市天气信息不可用！\n请检验网络连接是否正常！";		
			Toast.makeText(OddDaysActivity.this, msg, Toast.LENGTH_LONG).show();
		}
		//若输入合法则发送Intent修改widget中的内容
        Intent intent1 = new Intent("wyf.action.msg1");
        intent1.putExtra("xxq1", str[0]);
        OddDaysActivity.this.sendBroadcast(intent1);	
        
        Intent intent2 = new Intent("wyf.action.msg2");
        intent2.putExtra("xxq2", str[1]);
        OddDaysActivity.this.sendBroadcast(intent2);
        
        Intent intent3 = new Intent("wyf.action.msg3");
        intent3.putExtra("xxq3", str[2]);
        OddDaysActivity.this.sendBroadcast(intent3);
	}
	public Handler handler = new Handler()//获取网络信息成功后，初始化天气列表
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==0)//获取网络信息成功
			{
				try {
					initCityList(MyCityName);//初始化天气列表
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	private View getView(String id, Intent intent)
	{
		//通过LocalActivityManager启动Activity
	    return manager.startActivity(id, intent).getDecorView(); 
	}
	//初始化星期按钮
	public void initWeeksButton()
	{
		pager=(ViewPager)this.findViewById(R.id.viewpager);
		//得到周一到周日按钮的引用
		bt1=(Button)this.findViewById(R.id.button1);
		bt2=(Button)this.findViewById(R.id.button2);
		bt3=(Button)this.findViewById(R.id.button3);
		bt4=(Button)this.findViewById(R.id.button4);
		bt5=(Button)this.findViewById(R.id.button5);
		bt6=(Button)this.findViewById(R.id.button6);
		bt7=(Button)this.findViewById(R.id.button7);
		//初始化按钮背景色
		bt1.setBackgroundColor(context.getResources().getColor(R.color.blue1));
	    bt2.setBackgroundColor(context.getResources().getColor(R.color.blue1));
	    bt3.setBackgroundColor(context.getResources().getColor(R.color.blue1));
	    bt4.setBackgroundColor(context.getResources().getColor(R.color.blue1));
	    bt5.setBackgroundColor(context.getResources().getColor(R.color.blue1));
	    bt6.setBackgroundColor(context.getResources().getColor(R.color.blue1));
	    bt7.setBackgroundColor(context.getResources().getColor(R.color.blue1));
	    switch(week){
	    case 0:bt1.setBackgroundColor(context.getResources().getColor(R.color.dblue));
	    break;
	    case 1:bt2.setBackgroundColor(context.getResources().getColor(R.color.dblue));
	    break;
	    case 2:bt3.setBackgroundColor(context.getResources().getColor(R.color.dblue));
	    break;
	    case 3:bt4.setBackgroundColor(context.getResources().getColor(R.color.dblue));
	    break;
	    case 4:bt5.setBackgroundColor(context.getResources().getColor(R.color.dblue));
	    break;
	    case 5:bt6.setBackgroundColor(context.getResources().getColor(R.color.dblue));
	    break;
	    case 6:bt7.setBackgroundColor(context.getResources().getColor(R.color.dblue));
	    break;
	    }
		//为周一至周日按钮添加监听
		bt1.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						changeButton(0);
					}
				});
		bt2.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						changeButton(1);
					}
				});
		bt3.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						changeButton(2);
					}
				});
		bt4.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						changeButton(3);
					}
				});
		bt5.setOnClickListener(
				new OnClickListener()
				{
		           public void onClick(View v)
		           {
		        	   changeButton(4);
		           }
				});
		bt6.setOnClickListener(
				new OnClickListener()
				{
		           public void onClick(View v)
		           {
		        	   changeButton(5);
		           }
				});
		bt7.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						changeButton(6);
					}
				});	
	}
	public static void changeButton(int i){
    	switch(i){
    	case 0:bt1.setBackgroundColor(context.getResources().getColor(R.color.dblue));
    	bt2.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt3.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt4.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt5.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt6.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt7.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	break;
    	case 1:bt1.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt2.setBackgroundColor(context.getResources().getColor(R.color.dblue));
    	bt3.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt4.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt5.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt6.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt7.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	break;
    	case 2:bt1.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt2.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt3.setBackgroundColor(context.getResources().getColor(R.color.dblue));
    	bt4.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt5.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt6.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt7.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	break;
    	case 3:bt1.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt2.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt3.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt4.setBackgroundColor(context.getResources().getColor(R.color.dblue));
    	bt5.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt6.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt7.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	break;
    	case 4:bt1.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt2.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt3.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt4.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt5.setBackgroundColor(context.getResources().getColor(R.color.dblue));
    	bt6.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt7.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	break;
    	case 5:bt1.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt2.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt3.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt4.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt5.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt6.setBackgroundColor(context.getResources().getColor(R.color.dblue));
    	bt7.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	break;
    	case 6:bt1.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt2.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt3.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt4.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt5.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt6.setBackgroundColor(context.getResources().getColor(R.color.blue1));
    	bt7.setBackgroundColor(context.getResources().getColor(R.color.dblue));
    	break;
    	}
    	pager.setCurrentItem(i);
    }
}
