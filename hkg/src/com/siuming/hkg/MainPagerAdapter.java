package com.siuming.hkg;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MainPagerAdapter extends PagerAdapter {
	private List<View> mListViews;
	private List<String> mtitleList;

	public MainPagerAdapter(List<View> mListViews, List<String> mtitleList) {
		this.mListViews = mListViews;
		this.mtitleList = mtitleList;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));// 删除页卡
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		container.addView(mListViews.get(position), 0);// 添加页卡
		return mListViews.get(position);
	}
	
	@Override  
    public CharSequence getPageTitle(int position) {  

        return mtitleList.get(position);  
    }  

	@Override
	public int getCount() {
		return mListViews.size();// 返回页卡的数量
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;// 官方提示这样写
	}
}