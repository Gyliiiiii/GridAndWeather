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
	LayoutInflater inflater;//��������
	String[] content=new String[5];//�γ�����
	Context context=null;
	List<String[]> list=new ArrayList<String[]>();//�洢�����ݿ��ȡ����Ϣ
	int i=0;
	String week="";//���ڼ�
	String oddlistselected="";//ѡ��鿴�γ̵��Ͽ�������
	static List<String[]> ls=new ArrayList<String[]>();
	public static String[] allinfo=new String[5];//����һ�µĿγ�
	public static String[] allinfo1=new String[5];//���ڶ��µĿγ�
	public static String[] allinfo2=new String[5];//�������µĿγ�
	public static String[] allinfo3=new String[5];//�������µĿγ�
	public static String[] allinfo4=new String[5];//�������µĿγ�
	public static String[] allinfo5=new String[5];//�������µĿγ�
	public static String[] allinfo6=new String[5];//�������µĿγ�
	//������
	int count=0;
	public MyBaseAdapter(Context context,String week) 
	{
		this.context=context;
		this.week=week;
		//��ǰ������Ϊ-1�����ȡ��ǰ�����Ŀγ���Ϣ
		if(Constant.weeksNum!=-1)
		{
			list=SQLiteUtil.QueryAllCourceMess(Constant.weeksNum+"");
		}
		//listΪ�գ�����ʾ����ֵΪ��
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
		//list��Ϊ�գ���ʾ
		else
		{
			//������
			int i=0;
			//����list
			for(String[] s:list)
			{
				//��ʱ�������洢ÿһ����¼��������Ϣ
				String[] aa=new String[6];
				for(String ss:s)
				{
					aa[i]=ss;
					i++;
				}
				//����������
				i=0;
				oddlistselected=aa[5];
				String[] divide=aa[4].split(",");
				
				for(int z=0;z<divide.length;z++){
					if((Constant.weeksNum+"").equals(divide[z]))//�����ѡ������γ̵�������ͬ
					{
						count++;//��������1
						aa[4]=AddCourseActivity.DivideString(aa[4]);
						JudgeIfShowInSingle(s,aa);//��ֵ
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
		inflater=LayoutInflater.from(context);//�����Ӧ����
}
	public void JudgeIfShowInSingle(String[] s,String[] aa)//�жϵõ��Ŀγ��Ƿ���ʾ�ڵ��ս�����
	{
		if(oddlistselected.equals("����һ")){	//�γ��Ͽ�������Ϊ������һ������ֵ����һ��Ϣ���ַ���
			allinfo=GetSelectedInfo(Constant.weeksNum+"","����һ");
		}else if(oddlistselected.equals("���ڶ�")){
			allinfo1=GetSelectedInfo(Constant.weeksNum+"","���ڶ�");
		}else if(oddlistselected.equals("������")){
			allinfo2=GetSelectedInfo(Constant.weeksNum+"","������");
		}else if(oddlistselected.equals("������")){
			allinfo3=GetSelectedInfo(Constant.weeksNum+"","������");
		}else if(oddlistselected.equals("������")){
			allinfo4=GetSelectedInfo(Constant.weeksNum+"","������");
		}else if(oddlistselected.equals("������")){
			allinfo5=GetSelectedInfo(Constant.weeksNum+"","������");
		}else if(oddlistselected.equals("������")){
			allinfo6=GetSelectedInfo(Constant.weeksNum+"","������");
		}
		if(s[s.length-1].equals(week)){//�γ��Ͽ�����������week����Ҫ�����������򽫱�����Ϣ��ֵ��content[0]��
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
		//��־����ĳ�ʼ��
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
						if(ok[3].equals(Constant.nums[0]))//�����01-02
						{
							x[0]=0;//��ֵΪ0
						}
						if(ok[3].equals(Constant.nums[1]))//�����03-04
						{
							x[1]=1;//��ֵΪ1
						}
						if(ok[3].equals(Constant.nums[2]))//�����05-06
						{
							x[2]=2;//��ֵΪ2
						}
						if(ok[3].equals(Constant.nums[3]))//�����07-08
						{
							x[3]=3;//��ֵΪ3
						}
						if(ok[3].equals(Constant.nums[4]))//�����09-10
						{
							x[4]=4;//��ֵΪ4
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
							temp[k]+=str+"<#>";//ѭ����ֵ
						}
					}
				}
				k++;
			}
			int s=0;
			for(int a=0;a<5;a++){
				if(x[a]>=0){
					result[a]=temp[s];//���ݿγ̽�����Ӹ�ֵ
					s++;
				}
			}
		}
		return result;
	}
	@Override
	public int getCount() {
		//����Ϊ�γ̽�������ĳ���
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
			//��ò��ֵ�����
			ll=(LinearLayout)(inflater.inflate(R.layout.list,null).findViewById(R.id.list));
		}
		TextView tv1=(TextView)ll.getChildAt(0);//��øò��ֵĵ�һ���ӿؼ�
		TextView tv2=(TextView)ll.getChildAt(1);
		tv1.setText("\n"+Constant.nums[arg0]+"\n");//��ʾ����
		tv1.setTextSize(18);//��������
		tv1.setGravity(Gravity.LEFT);//����λ��   
		tv2.setTextSize(24);//���������С
		tv2.setText(content[arg0]);//��ʾ�γ���Ϣ
		tv2.setGravity(Gravity.LEFT);
		return ll;
	}
}
