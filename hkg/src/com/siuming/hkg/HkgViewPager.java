package com.siuming.hkg;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class HkgViewPager {
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;//一个viewpager的指示器，效果就是一个横的粗的下划线  
	private List<View> viewList = new ArrayList<View>();//把需要滑动的页卡添加到这个list中  
    private List<String> titleList = new ArrayList<String>();//viewpager的标题  
    
	
	public void init(){
		MainPagerAdapter pagerAdapter = new MainPagerAdapter(viewList,titleList);
		viewPager.setAdapter(pagerAdapter);  
	}
	
	public void setViewPager(ViewPager viewPager){
		this.viewPager = viewPager;
	}
	
	public void setPagerTabStripper(PagerTabStrip pagerTab){
		pagerTabStrip = pagerTab;
        pagerTabStrip.setTabIndicatorColor(GoldenConfig.getContext().getResources().getColor(R.color.pagerIndicator));  
        pagerTabStrip.setDrawFullUnderline(false); 
        pagerTabStrip.setBackgroundColor(GoldenConfig.getContext().getResources().getColor(R.color.pagerBackground)); 
        pagerTabStrip.setTextSpacing(50);    
	}

	public void addPage(int resource,String title){
		LayoutInflater lf = LayoutInflater.from(GoldenConfig.getContext());  
		View view = lf.inflate(resource, null);  
		viewList.add(view);  
		titleList.add(title);  
	}
	
	public View getView(int i){
		return viewList.get(i);
	}
}
