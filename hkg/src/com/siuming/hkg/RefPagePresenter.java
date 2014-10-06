package com.siuming.hkg;

import com.siuming.hkg.goldenApi.ApiModel;
import com.siuming.hkg.goldenApi.ApiService;
import com.siuming.hkg.goldenApi.GoldenDataHandler.ApiListener;
import com.siuming.hkg.model.TopicPageList;
import com.siuming.hkg.view.component.RefreshListView.RefreshListener;
import com.siuming.hkg.view.page.RefListViewPage;

public class RefPagePresenter {
	RefListViewPage page;
	TopicPageList topicPageList;
	
	public RefPagePresenter(RefListViewPage p){
		page = p;
		topicPageList = new TopicPageList();
		
		page.setOnRefreshListener(new RefreshListenerImpl());
		page.setListViewAdapter(topicPageList.getAdapter());
		page.showLoading();
		
	}
	
	public void requestRefresh(){
		ApiService apiService = new ApiService(new ApiListenerImpl());
		apiService.setThreadName("get Topic List");
		
		ApiModel apiModel = new ApiModel();
		setModelConfig(apiModel);
		apiModel.setGoal(ApiModel.TOPIC);
		apiModel.setPage(1);
		
		apiService.request(apiModel);
	}
	
	public void requestUpdate(int page){
		ApiService apiService = new ApiService(new ApiListenerImpl());
		apiService.setThreadName("get Topic List");
		
		ApiModel apiModel = new ApiModel();
		setModelConfig(apiModel);
		apiModel.setGoal(ApiModel.TOPIC);
		apiModel.setPage(page);
		
		apiService.request(apiModel);
	}

	private void setModelConfig(ApiModel apiModel) {
		apiModel.setFiltermode(GoldenConfig.getFilterMode());
		apiModel.setSensormode(GoldenConfig.getSensorMode());
		apiModel.setType(GoldenConfig.getTypeShot());
	}
	
	class ApiListenerImpl implements ApiListener{

		@Override
		public void onSuccess(String str) {
			topicPageList.addTopicPage(str);
			page.unShowLoading();
			//TopicPage topicPage = new TopicPage(str);
			//page.setListViewAdapter(topicPage.getAdapter());
		}

		@Override
		public void ontFail(String str) {

		}		
	}
	
	class RefreshListenerImpl implements RefreshListener{

		@Override
		public void refreshing() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadUpdate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void showUpdate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void waitUpdate() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
