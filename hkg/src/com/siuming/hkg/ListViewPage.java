package com.siuming.hkg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ListViewPage extends LinearLayout  {
	
	public ListViewPage(Context context) {
		super(context,null);
		LayoutInflater.from(context).inflate(R.layout.page, this); 
	}

	public ListViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.page, this); 
	}



	RefreshListView refreshListView;



	public void setRefreshListView(int id) {
		
	}

}
