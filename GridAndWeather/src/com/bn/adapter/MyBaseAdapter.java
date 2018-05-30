package com.bn.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bn.gridandweather.AddCourseActivity;
import com.bn.gridandweather.R;
import com.bn.util.Constant;
import com.bn.util.SQLiteUtil;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter
{
	LayoutInflater inflater;//声明布局
	String[] content=new String[5];//课程内容
	Context context=null;
	List<String[]> list=new ArrayList<String[]>();//存储从数据库获取的信息
	int i=0;
	String week="";//星期几
	String oddlistselected="";//选择查看课程的上课星期数
	static List<String[]> ls=new ArrayList<String[]>();
	public static String[] allinfo=new String[5];//星期一下的课程
	public static String[] allinfo1=new String[5];//星期二下的课程
	public static String[] allinfo2=new String[5];//星期三下的课程
	public static String[] allinfo3=new String[5];//星期四下的课程
	public static String[] allinfo4=new String[5];//星期五下的课程
	public static String[] allinfo5=new String[5];//星期六下的课程
	public static String[] allinfo6=new String[5];//星期日下的课程
	//计数器
	int count=0;
	public MyBaseAdapter(Context context,String week) 
	{
		this.context=context;
		this.week=week;
		//当前周数不为-1，则获取当前周数的课程信息
		if(Constant.weeksNum!=-1)
		{
			list=SQLiteUtil.QueryAllCourceMess(Constant.weeksNum+"");
		}
		//list为空，不显示，赋值为空
		if(list==null)
		{
			for(int j=0;j<5;j++)
			{
				content[j]="";
				allinfo[j]="";
				allinfo1[j]="";
				allinfo2[j]="";
				allinfo3[j]="";
				allinfo4[j]="";
				allinfo5[j]="";
				allinfo6[j]="";
			}
		}
		//list不为空，显示
		else
		{
			//计数器
			int i=0;
			//遍历list
			for(String[] s:list)
			{
				//临时变量，存储每一条记录的六项信息
				String[] aa=new String[6];
				for(String ss:s)
				{
					aa[i]=ss;
					i++;
				}
				//计数器清零
				i=0;
				oddlistselected=aa[5];
				String[] divide=aa[4].split(",");
				
				for(int z=0;z<divide.length;z++){
					if((Constant.weeksNum+"").equals(divide[z]))//如果所选周数与课程的周数相同
					{
						count++;//计数器加1
						aa[4]=AddCourseActivity.DivideString(aa[4]);
						JudgeIfShowInSingle(s,aa);//赋值
					}
					else
					{
						if(count==0&&z==divide.length-1)
						{
							for(int j=0;j<5;j++)
							{
								allinfo[j]="";
								allinfo1[j]="";
								allinfo2[j]="";
								allinfo3[j]="";
								allinfo4[j]="";
								allinfo5[j]="";
								allinfo6[j]="";
							}
						}
					}
				}
			}
		}
		inflater=LayoutInflater.from(context);//获得相应布局
}
	public void JudgeIfShowInSingle(String[] s,String[] aa)//判断得到的课程是否显示在单日界面上
	{
		if(oddlistselected.equals("星期一")){	//课程上课星期数为“星期一”，则赋值给周一信息的字符串
			allinfo=GetSelectedInfo(Constant.weeksNum+"","星期一");
		}else if(oddlistselected.equals("星期二")){
			allinfo1=GetSelectedInfo(Constant.weeksNum+"","星期二");
		}else if(oddlistselected.equals("星期三")){
			allinfo2=GetSelectedInfo(Constant.weeksNum+"","星期三");
		}else if(oddlistselected.equals("星期四")){
			allinfo3=GetSelectedInfo(Constant.weeksNum+"","星期四");
		}else if(oddlistselected.equals("星期五")){
			allinfo4=GetSelectedInfo(Constant.weeksNum+"","星期五");
		}else if(oddlistselected.equals("星期六")){
			allinfo5=GetSelectedInfo(Constant.weeksNum+"","星期六");
		}else if(oddlistselected.equals("星期日")){
			allinfo6=GetSelectedInfo(Constant.weeksNum+"","星期日");
		}
		if(s[s.length-1].equals(week)){//课程上课星期数等于week（需要星期数），则将本条信息赋值给content[0]。
			if(s[3].equals(Constant.nums[0])){
				content[0]=aa[0]+"\n*"+aa[1]+"\n@"+aa[2]+"\n~"+aa[4];
			}
			else if(s[3].equals(Constant.nums[1])){
				content[1]=aa[0]+"\n*"+aa[1]+"\n@"+aa[2]+"\n~"+aa[4];
			}
			else if(s[3].equals(Constant.nums[2])){
				content[2]=aa[0]+"\n*"+aa[1]+"\n@"+aa[2]+"\n~"+aa[4];
			}
			else if(s[3].equals(Constant.nums[3])){
				content[3]=aa[0]+"\n*"+aa[1]+"\n@"+aa[2]+"\n~"+aa[4];
			}
			else if(s[3].equals(Constant.nums[4])){
				content[4]=aa[0]+"\n*"+aa[1]+"\n@"+aa[2]+"\n~"+aa[4];
			}
		}
	}
	public static String[] GetSelectedInfo(String num,String week)
	{
		int[] x=new int[5];
		//标志数组的初始化
		for(int z=0;z<5;z++){
			x[z]=-1;
		}
		String[] result=new String[5];
		String[] temp=new String[100];
		ls=SQLiteUtil.GetCourceByWeeks(num, week);
		if(ls==null){
			for(int j=0;j<5;j++){
				result[j]="";
				temp[j]="";
			}
		}else{
			for(int j=0;j<5;j++){
				result[j]="";
				temp[j]="";
			}
			for(String[] ok:ls){
				for(int h=0;h<ok.length;h++){
					if(h==3){
						if(ok[3].equals(Constant.nums[0]))//如果是01-02
						{
							x[0]=0;//赋值为0
						}
						if(ok[3].equals(Constant.nums[1]))//如果是03-04
						{
							x[1]=1;//赋值为1
						}
						if(ok[3].equals(Constant.nums[2]))//如果是05-06
						{
							x[2]=2;//赋值为2
						}
						if(ok[3].equals(Constant.nums[3]))//如果是07-08
						{
							x[3]=3;//赋值为3
						}
						if(ok[3].equals(Constant.nums[4]))//如果是09-10
						{
							x[4]=4;//赋值为4
						}
					}
				}
			}
			int k=0;
			for(String[] group:ls){
				String[] divide=group[4].split(",");
				for(int z=0;z<divide.length;z++)
				{
					if((Constant.weeksNum+"").equals(divide[z]))
					{
						for(String str:group){
							temp[k]+=str+"<#>";//循环赋值
						}
					}
				}
				k++;
			}
			int s=0;
			for(int a=0;a<5;a++){
				if(x[a]>=0){
					result[a]=temp[s];//根据课程节数间接赋值
					s++;
				}
			}
		}
		return result;
	}
	@Override
	public int getCount() {
		//长度为课程节数数组的长度
		return Constant.nums.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) 
	{
		LinearLayout ll=(LinearLayout)convertView;
		if(ll==null)
		{
			//获得布局的引用
			ll=(LinearLayout)(inflater.inflate(R.layout.list,null).findViewById(R.id.list));
		}
		TextView tv1=(TextView)ll.getChildAt(0);//获得该布局的第一个子控件
		TextView tv2=(TextView)ll.getChildAt(1);
		tv1.setText("\n"+Constant.nums[arg0]+"\n");//显示节数
		tv1.setTextSize(18);//设置字体
		tv1.setGravity(Gravity.LEFT);//设置位置   
		tv2.setTextSize(24);//设置字体大小
		tv2.setText(content[arg0]);//显示课程信息
		tv2.setGravity(Gravity.LEFT);
		return ll;
	}
}
