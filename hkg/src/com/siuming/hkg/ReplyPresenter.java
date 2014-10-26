package com.siuming.hkg;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

import com.siuming.hkg.goldenApi.ApiListener;
import com.siuming.hkg.goldenApi.ApiModel;
import com.siuming.hkg.goldenApi.ApiService;
import com.siuming.hkg.model.ReplyPageList;
import com.siuming.hkg.view.activity.ReplyActivity;
import com.siuming.hkg.view.component.HkgViewPager;
import com.siuming.hkg.view.page.ListViewPage;

public class ReplyPresenter {
	ReplyActivity activity;
	HkgViewPager viewPager;
	String messageId;
	ArrayList<ListViewPage> pageList;
	ReplyPageList replyPageList;
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	Handler mHandler = new Handler();
	
	boolean isUpdating = false;
	boolean isWaiting = true;
	
	
	public ReplyPresenter(String id) {
		messageId = id;
		replyPageList = new ReplyPageList();
		replyPageList.setBufferSize(3);
		pageList = new ArrayList<ListViewPage>();
	}
	
	public void setActivity(ReplyActivity replyActivity) {
		activity = replyActivity;
		viewPager = new HkgViewPager(activity);
		
		if(replyPageList.loadedFirstPage()){
			int firstPage = replyPageList.getFirstPage();
			ListViewPage pageView = new ListViewPage(activity);
			viewPager.addPage(pageView, "第"+firstPage+"頁");
			pageView.setListViewAdapter(replyPageList.getAdapter(firstPage));
		}else{
			isWaiting = true;
		}
	}

	public void requestUpdate(int pageNumber){
		loadPost(pageNumber);
	}	

	private void loadPost(int page) {
		ApiService apiService = new ApiService(new ApiListenerImpl(page),executorService);
		apiService.setThreadName("get Topic List");			
		ApiModel apiModel = createGetReplyModel(page);
		apiService.request(apiModel);		
		isUpdating = true;
	}
	
	private ApiModel createGetReplyModel(int page){
		ApiModel apiModel = new ApiModel();
		setModelConfig(apiModel);
		apiModel.setGoal(ApiModel.REPLY);
		apiModel.setPage(page);
		apiModel.setMessageId(messageId);
		return apiModel;
	}
	
	private void setModelConfig(ApiModel apiModel) {
		apiModel.setFiltermode(GoldenConfig.getFilterMode());
		apiModel.setSensormode(GoldenConfig.getSensorMode());
		apiModel.setType(GoldenConfig.getTypeShot());
	}
	
	class ApiListenerImpl implements ApiListener{
		int page;
		
		public ApiListenerImpl(int p){
			page = p;
		}
		
		@Override
		public void onSuccess(String str) {
			mHandler.post(new InitListTask(str));			
		}
		
		class InitListTask implements Runnable{
			String str;
			InitListTask(String str){
				this.str = str;
			}
			
			@Override
			public void run() {
				replyPageList.addReplyPage(str,page);
				isUpdating = false;
				
				if (replyPageList.canLoad()) {
					requestUpdate(replyPageList.nextToLoad());
				}
				

				if(viewPager!=null){
					ListViewPage pageView = new ListViewPage(activity);
					if(replyPageList.isFull(replyPageList.showedPage())){
						viewPager.addPage(pageView, "第"+page+"頁");
						pageView.setListViewAdapter(replyPageList.getAdapter(page));
					}
				}
				
				if(true){//waiting is true
					replyPageList.updateData();
					isWaiting = false;
				}			
			}
			
		}

		@Override
		public void ontFail(String str) {
			isUpdating = false;
			//requestUpdate(replyPageList.loadedPage());
		}
	}
}
