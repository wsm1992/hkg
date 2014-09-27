package com.siuming.hkg.view.page;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public interface IListViewPage {
	public void setOnItemClickListener(OnItemClickListener impl);
	
	public void setListViewAdapter(SimpleAdapter adapter);
}
