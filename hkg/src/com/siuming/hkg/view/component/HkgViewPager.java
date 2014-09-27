package com.siuming.hkg.view.component;

import java.util.ArrayList;
import java.util.List;

import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.MainPagerAdapter;
import com.siuming.hkg.R;
import com.siuming.hkg.R.color;
import com.siuming.hkg.R.id;

import android.app.Activity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;

public class HkgViewPager {
	ViewPager viewPager;
	PagerTabStrip pagerTabStrip;//一个viewpager的指示器，效果就是一个横的粗的下划线  
	List<View> viewList = new ArrayList<View>();//把需要滑动的页卡添加到这个list中  
    List<String> titleList = new ArrayList<String>();//viewpager的标题  
    MainPagerAdapter pagerAdapter;    
    	
	public HkgViewPager(Activity activity) {
		setViewPager((ViewPager) activity.findViewById(R.id.viewpager));
		setPagerTabStripper((PagerTabStrip) activity.findViewById(R.id.pagertab));  
        setLayout();    
    	pagerAdapter = new MainPagerAdapter(viewList,titleList);
		viewPager.setAdapter(pagerAdapter);
	}

	public void setViewPager(ViewPager viewPager){
		this.viewPager = viewPager;
	}
	
	public void setPagerTabStripper(PagerTabStrip pagerTab){
		pagerTabStrip = pagerTab;
	}

	private void setLayout() {
		pagerTabStrip.setTabIndicatorColor(GoldenConfig.getContext().getResources().getColor(R.color.pagerIndicator));  
        pagerTabStrip.setDrawFullUnderline(false); 
        pagerTabStrip.setBackgroundColor(GoldenConfig.getContext().getResources().getColor(R.color.pagerBackground)); 
        pagerTabStrip.setTextSpacing(50);
	}

	public void addPage(View view,String title){		
		viewList.add(view);  
		titleList.add(title);  
		pagerAdapter.notifyDataSetChanged();
	}
}
