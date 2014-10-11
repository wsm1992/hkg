package com.siuming.hkg;

import java.util.ArrayList;

import com.siuming.hkg.goldenApi.ApiListener;
import com.siuming.hkg.goldenApi.ApiModel;
import com.siuming.hkg.goldenApi.ApiService;
import com.siuming.hkg.model.ReplyPageList;
import com.siuming.hkg.view.activity.ReplyActivity;
import com.siuming.hkg.view.component.HkgViewPager;
import com.siuming.hkg.view.page.ListViewPage;

public class ReplyPresenter {
	ReplyActivity activity;
	//HkgViewPager viewPager;
	String messageId;
	ArrayList<ListViewPage> pageList;
	ReplyPageList replyPageList;
	
	boolean isUpdating = false;
	boolean isWaiting = true;
	ApiListener apiListener = new ApiListenerImpl();
	
	
	public ReplyPresenter(String id) {
		messageId = id;
		replyPageList = new ReplyPageList();
		replyPageList.setBufferSize(3);
	}

	public void requestUpdate(int pageNumber){
		//ListViewPage page = new ListViewPage(activity);
		//pageList.add(page);
		//viewPager.addPage(page, "第"+page+"頁");
		//page.setListViewAdapter(replyPageList.getAdapter(pageNumber));
		loadPost(pageNumber);
	}	

	private void loadPost(int page) {
		ApiService apiService = new ApiService(apiListener);
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

		@Override
		public void onSuccess(String str) {
			replyPageList.addReplyPage(str);
			isUpdating = false;
			
			if(replyPageList.canLoad()){
				requestUpdate(replyPageList.loadedPage() + 1);
			}
			
			if(isWaiting){
				replyPageList.updateData();
				isWaiting = false;
			}
		}

		@Override
		public void ontFail(String str) {
			isUpdating = false;
			requestUpdate(replyPageList.loadedPage());
		}		
	}
}
