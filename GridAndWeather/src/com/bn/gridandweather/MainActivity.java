package com.bn.gridandweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		/* �½�һ��Intent���� */
        Intent intent = new Intent();
        /* ָ��intentҪ�������� */
        intent.setClass(MainActivity.this,OddDaysActivity.class);
        /* ����һ���µ�Activity */
        MainActivity.this.startActivity(intent);
        /* �رյ�ǰ��Activity */
        MainActivity.this.finish();
	}
}
