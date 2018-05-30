package com.bn.gridandweather;

import com.bn.util.JSonInfoUtil;
import com.bn.util.TQYBInfo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class DayMessProvider extends AppWidgetProvider
{
	RemoteViews rv;//获得自定义通知的布局资源
	TQYBInfo info;//获得天气的具体信息
	String msg="烟台";//默认为烟台当地天气
	public DayMessProvider()
	{
		Log.d("MyWidgetProvider","============");
	}
	
	@Override  
	public void onDisabled(Context context) 
	{//若为最后一个实例
		//删除时停止后台定时更新Widget时间的Service
		context.stopService(new Intent(context,TimeService.class));
	}
	
	@Override  
	public void onEnabled (Context context) 
	{//若为第一个实例则打开服务
		//启动后台定时更新时间的Service
		context.startService(new Intent(context,TimeService.class)); 			
	}
	
	//onUpdate为组件在桌面上生成时调用,并更新组件UI
	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) 
	{
		//创建RemoteViews
		rv = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);	
		//创建启动课程信息的Activity的Intent
        Intent intent = new Intent(context,OddDaysActivity.class);
        //创建包裹此Intent的PendingIntent
        PendingIntent pendingIntent=PendingIntent.getActivity
        (
        		context, 
        		0, 
        		intent, 
        		PendingIntent.FLAG_UPDATE_CURRENT
        );

        //获得widget中显示的城市信息
        SharedPreferences scity=context.getSharedPreferences("cct", Context.MODE_PRIVATE);
        String ccity=scity.getString("cty",null );
        if(ccity!=null)//当第一次显示桌面时，当发过来的城市名不为空时，引用该信息
        {
        	 msg=ccity;
        }
        //获得该城市的天气信息
        try{
  			String jStr=JSonInfoUtil.getJSonStr(msg);
  			
  			Log.d("MyWidgetProvider",jStr);
  			//解析读取需要的天气预报信息
  			info=JSonInfoUtil.parseJSon(jStr);
  		}
  		catch(Exception e)
  		{
  			e.printStackTrace();
  		}
        
        if(ccity!=null)
        {
        	rv.setTextViewText(R.id.cityw, ccity);
        }
        //绘制桌面显示的天气图片
        rv.setImageViewResource(R.id.pic,info.pic);
        
        //设置按下Widget界面发送此PendingIntent
        rv.setOnClickPendingIntent(R.id.courselayout1, pendingIntent);  
        
        //right  1 获得当前日期
        SharedPreferences sp1=context.getSharedPreferences("xqsj1", Context.MODE_PRIVATE);
        String date=sp1.getString
        (
        		"dt",   //键值
        		null    //默认值
        );
        if(date!=null)
        {
        	rv.setTextViewText(R.id.date, date);
        }
        //2   获得天气状况
        SharedPreferences sp2=context.getSharedPreferences("xqsj2", Context.MODE_PRIVATE);
        String weather=sp2.getString
        (
        		"wt",   //键值
        		null    //默认值
        );
        if(weather!=null)
        {
        	rv.setTextViewText(R.id.weather, weather);
        }
        //3  获得天气温度
        SharedPreferences sp3=context.getSharedPreferences("xqsj3", Context.MODE_PRIVATE);
        String temperature=sp3.getString
        (
        		"tt",   //键值
        		null    //默认值
        );
        if(temperature!=null)
        {
        	rv.setTextViewText(R.id.temperature, temperature);
        }
        
        //middle 1	获得课程名信息
        SharedPreferences tc1=context.getSharedPreferences("ttc1", Context.MODE_PRIVATE);
        String tcourse1=tc1.getString
        (
        		"tcs1",   //键值
        		null    //默认值
        );
        if(tcourse1!=null)
        {
        	rv.setTextViewText(R.id.tc1, tcourse1);
        }
        //2
        SharedPreferences tc2=context.getSharedPreferences("ttc2", Context.MODE_PRIVATE);
        String tcourse2=tc2.getString
        (
        		"tcs2",   //键值
        		null    //默认值
        );
        if(tcourse2!=null)
        {
        	rv.setTextViewText(R.id.tc2, tcourse2);
        }
        //3
        SharedPreferences tc3=context.getSharedPreferences("ttc3", Context.MODE_PRIVATE);
        String tcourse3=tc3.getString
        (
        		"tcs3",   //键值
        		null    //默认值
        );
        if(tcourse3!=null)
        {
        	rv.setTextViewText(R.id.tc3, tcourse3);
        }
        //4
        SharedPreferences tc4=context.getSharedPreferences("ttc4", Context.MODE_PRIVATE);
        String tcourse4=tc4.getString
        (
        		"tcs4",   //键值
        		null    //默认值
        );
        if(tcourse4!=null)
        {
        	rv.setTextViewText(R.id.tc4, tcourse4);
        }
        //5
        SharedPreferences tc5=context.getSharedPreferences("ttc5", Context.MODE_PRIVATE);
        String tcourse5=tc5.getString
        (
        		"tcs5",   //键值
        		null    //默认值
        );
        if(tcourse5!=null)
        {
        	rv.setTextViewText(R.id.tc5, tcourse5);
        }
        
        //left  1  获得课程节数信息
        SharedPreferences tn1=context.getSharedPreferences("ttn1", Context.MODE_PRIVATE);
        String ttnum1=tn1.getString
        (
        		"tjs1",   //键值
        		null    //默认值
        );
        if(ttnum1!=null)
        {
        	rv.setTextViewText(R.id.tnum1, ttnum1);
        }
        //2
        SharedPreferences tn2=context.getSharedPreferences("ttn2", Context.MODE_PRIVATE);
        String ttnum2=tn2.getString
        (
        		"tjs2",   //键值
        		null    //默认值
        );
        if(ttnum2!=null)
        {
        	rv.setTextViewText(R.id.tnum2, ttnum2);
        }
        //3
        SharedPreferences tn3=context.getSharedPreferences("ttn3", Context.MODE_PRIVATE);
        String ttnum3=tn3.getString
        (
        		"tjs3",   //键值
        		null    //默认值
        );
        if(ttnum3!=null)
        {
        	rv.setTextViewText(R.id.tnum3, ttnum3);
        }
        //4
        SharedPreferences tn4=context.getSharedPreferences("ttn4", Context.MODE_PRIVATE);
        String ttnum4=tn4.getString
        (
        		"tjs4",   //键值
        		null    //默认值
        );
        if(ttnum4!=null)
        {
        	rv.setTextViewText(R.id.tnum4, ttnum4);
        }
        //5
        SharedPreferences tn5=context.getSharedPreferences("ttn5", Context.MODE_PRIVATE);
        String ttnum5=tn5.getString
        (
        		"tjs5",   //键值
        		null    //默认值
        );
        if(ttnum5!=null)
        {
        	rv.setTextViewText(R.id.tnum5, ttnum5);
        }
        
        //right  1      获得上课地点信息
        SharedPreferences tp1=context.getSharedPreferences("ttp1", Context.MODE_PRIVATE);
        String tplace1=tp1.getString
        (
        		"tpc1",   //键值
        		null    //默认值
        );
        if(tplace1!=""&&tplace1!=null)
        {
        	rv.setTextViewText(R.id.tp1, tplace1);
        	rv.setImageViewResource(R.id.imageView1,R.drawable.sitting);//当地点信息不为空时将显示一个地点小图标
        }
        //2
        SharedPreferences tp2=context.getSharedPreferences("ttp2", Context.MODE_PRIVATE);
        String tplace2=tp2.getString
        (
        		"tpc2",   //键值
        		null    //默认值
        );
        if(tplace2!=""&&tplace2!=null)
        {
        	rv.setTextViewText(R.id.tp2, tplace2);
        	rv.setImageViewResource(R.id.imageView2,R.drawable.sitting);
        }
        //3
        SharedPreferences tp3=context.getSharedPreferences("ttp3", Context.MODE_PRIVATE);
        String tplace3=tp3.getString
        (
        		"tpc3",   //键值
        		null    //默认值
        );
        if(tplace3!=""&&tplace3!=null)
        {
        	rv.setTextViewText(R.id.tp3, tplace3);
        	rv.setImageViewResource(R.id.imageView3,R.drawable.sitting);
        }
        //4
        SharedPreferences tp4=context.getSharedPreferences("ttp4", Context.MODE_PRIVATE);
        String tplace4=tp4.getString
        (
        		"tpc4",   //键值
        		null    //默认值
        );
        if(tplace4!=""&&tplace4!=null)
        {
        	rv.setTextViewText(R.id.tp4, tplace4);
        	rv.setImageViewResource(R.id.imageView4,R.drawable.sitting);
        }
        //5
        SharedPreferences tp5=context.getSharedPreferences("ttp5", Context.MODE_PRIVATE);
        String tplace5=tp5.getString
        (
        		"tpc5",   //键值
        		null    //默认值
        );
        if(tplace5!=""&&tplace5!=null)
        {
        	rv.setTextViewText(R.id.tp5, tplace5);
        	rv.setImageViewResource(R.id.imageView5,R.drawable.sitting);
        }
        //更新Widget
        appWidgetManager.updateAppWidget(appWidgetIds, rv);
	}
	
	@Override  //onReceiver 为接收广播时调用更新UI
    public void onReceive(Context context, Intent intent) 
	{
		super.onReceive(context, intent);
        if (rv == null) 
        {
        	//创建RemoteViews
            rv = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
        }
         if (intent.getAction().equals("wyf.action.time_upadte"))//收到的是更新时间的 Action则更新时间
        {
        	rv.setTextViewText(R.id.TextView02,intent.getStringExtra("time"));
        } 
        else if (intent.getAction().equals("wyf.action.city"))//收到的是更新城市信息的 Action则更新城市名
        {
            rv.setTextViewText(R.id.cityw, intent.getStringExtra("ccity"));  
            //向Preferences中写入城市信息
	        SharedPreferences sp=context.getSharedPreferences("cct", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();//获取编辑器
            editor.putString("cty",intent.getStringExtra("ccity"));
            msg=intent.getStringExtra("ccity");			//获得该键所对应的值
            try{
      			String jStr=JSonInfoUtil.getJSonStr(msg);
      			//解析读取需要的天气预报信息
      			info=JSonInfoUtil.parseJSon(jStr);
      		}
      		catch(Exception e)
      		{
      			e.printStackTrace();
      		}
            rv.setImageViewResource(R.id.pic,info.pic);
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tc1"))//收到的是更新课程名信息的 Action则更新课程名
        {
            rv.setTextViewText(R.id.tc1, intent.getStringExtra("tc1"));            
	        SharedPreferences sp=context.getSharedPreferences("ttc1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tcs1",intent.getStringExtra("tc1"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tc2")) 
        {
            rv.setTextViewText(R.id.tc2, intent.getStringExtra("tc2"));            
	        SharedPreferences sp=context.getSharedPreferences("ttc2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tcs2",intent.getStringExtra("tc2"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tc3")) 
        {
            rv.setTextViewText(R.id.tc3, intent.getStringExtra("tc3"));            
	        SharedPreferences sp=context.getSharedPreferences("ttc3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tcs3",intent.getStringExtra("tc3"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tc4")) 
        {
            rv.setTextViewText(R.id.tc4, intent.getStringExtra("tc4"));            
	        SharedPreferences sp=context.getSharedPreferences("ttc4", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tcs4",intent.getStringExtra("tc4"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tc5")) 
        {
            rv.setTextViewText(R.id.tc5, intent.getStringExtra("tc5"));            
	        SharedPreferences sp=context.getSharedPreferences("ttc5", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tcs5",intent.getStringExtra("tc5"));
            editor.commit();
        }
        //
        else if (intent.getAction().equals("wyf.action.tnum1"))//收到的是更新课程节数的 Action则更新课程节数
        {
            rv.setTextViewText(R.id.tnum1, intent.getStringExtra("tnum1"));            
	        SharedPreferences sp=context.getSharedPreferences("ttn1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tjs1",intent.getStringExtra("tnum1"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tnum2")) 
        {
            rv.setTextViewText(R.id.tnum2, intent.getStringExtra("tnum2"));            
	        SharedPreferences sp=context.getSharedPreferences("ttn2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tjs2",intent.getStringExtra("tnum2"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tnum3")) 
        {
            rv.setTextViewText(R.id.tnum3, intent.getStringExtra("tnum3"));            
	        SharedPreferences sp=context.getSharedPreferences("ttn3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tjs3",intent.getStringExtra("tnum3"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tnum4")) 
        {
            rv.setTextViewText(R.id.tnum4, intent.getStringExtra("tnum4"));            
	        SharedPreferences sp=context.getSharedPreferences("ttn4", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tjs4",intent.getStringExtra("tnum4"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tnum5")) 
        {
            rv.setTextViewText(R.id.tnum5, intent.getStringExtra("tnum5"));            
	        SharedPreferences sp=context.getSharedPreferences("ttn5", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tjs5",intent.getStringExtra("tnum5"));
            editor.commit();
        }
        //
        else if (intent.getAction().equals("wyf.action.tp1")) //收到的是更新上课的 Action则更新上课地点
        {
            rv.setTextViewText(R.id.tp1, intent.getStringExtra("tp1"));  
	        SharedPreferences sp=context.getSharedPreferences("ttp1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tpc1",intent.getStringExtra("tp1"));
            if(!intent.getStringExtra("tp1").equals(""))
            {
            	rv.setImageViewResource(R.id.imageView1,R.drawable.sitting);
            }
            editor.commit();
        } 
        else if (intent.getAction().equals("wyf.action.tp2")) 
        {
            rv.setTextViewText(R.id.tp2, intent.getStringExtra("tp2"));            
	        SharedPreferences sp=context.getSharedPreferences("ttp2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tpc2",intent.getStringExtra("tp2"));
            if(!intent.getStringExtra("tp2").equals(""))
            {
            	System.out.println("Dess ooo");
            	rv.setImageViewResource(R.id.imageView2,R.drawable.sitting);
            }
            editor.commit();
        } 
        else if (intent.getAction().equals("wyf.action.tp3")) 
        {
            rv.setTextViewText(R.id.tp3, intent.getStringExtra("tp3"));
            rv.setImageViewResource(R.id.imageView1,R.drawable.sitting);
	        SharedPreferences sp=context.getSharedPreferences("ttp3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tpc3",intent.getStringExtra("tp3"));
            System.out.println("next");
            if(!intent.getStringExtra("tp3").equals(""))
            {
            	rv.setImageViewResource(R.id.imageView3,R.drawable.sitting);
            }
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tp4")) 
        {
            rv.setTextViewText(R.id.tp4, intent.getStringExtra("tp4")); 
	        SharedPreferences sp=context.getSharedPreferences("ttp4", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tpc4",intent.getStringExtra("tp4"));
            if(!intent.getStringExtra("tp4").equals(""))
            {
            	rv.setImageViewResource(R.id.imageView4,R.drawable.sitting);
            }
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.tp5")) 
        {
            rv.setTextViewText(R.id.tp5, intent.getStringExtra("tp5"));
	        SharedPreferences sp=context.getSharedPreferences("ttp5", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tpc5",intent.getStringExtra("tp5"));
            if(!intent.getStringExtra("tp5").equals(""))
            {
            	rv.setImageViewResource(R.id.imageView5,R.drawable.sitting);
            }
            editor.commit();
        } 
        ////收到的是更新天气信息的 Action则更新天气信息
        else if (intent.getAction().equals("wyf.action.msg1")) 
        {
            rv.setTextViewText(R.id.date, intent.getStringExtra("xxq1"));            
	        SharedPreferences sp=context.getSharedPreferences("xqsj1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("dt",intent.getStringExtra("xxq1"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.msg2")) 
        {
            rv.setTextViewText(R.id.weather, intent.getStringExtra("xxq2"));            
	        SharedPreferences sp=context.getSharedPreferences("xqsj2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("wt",intent.getStringExtra("xxq2"));
            editor.commit();
        }
        else if (intent.getAction().equals("wyf.action.msg3")) 
        {
            rv.setTextViewText(R.id.temperature, intent.getStringExtra("xxq3"));            
	        SharedPreferences sp=context.getSharedPreferences("xqsj3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("tt",intent.getStringExtra("xxq3"));
            editor.commit();
        }
        
        else  if (intent.getAction().equals("wyf.action.load_xq")) 
        {	//收到切屏（横竖屏切换）服务,并同步更新时间
            Intent intentTemp = new Intent(context,OddDaysActivity.class);
            //创建包裹此Intent的PendingIntent
            PendingIntent pendingIntent=PendingIntent.getActivity
            (
            		context, 
            		0, 
            		intentTemp, 
            		PendingIntent.FLAG_UPDATE_CURRENT
            );
            //设置按下Widget界面发送此PendingIntent
            rv.setOnClickPendingIntent(R.id.courselayout1, pendingIntent);  
        } 
        //真正更新Widget
        AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
        int[] appIds = appWidgetManger.getAppWidgetIds
        (
        	new ComponentName
        	(
                context, 
                DayMessProvider.class
            )
        );
        appWidgetManger.updateAppWidget(appIds, rv);
	}
}

