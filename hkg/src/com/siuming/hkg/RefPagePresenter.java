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
	int showedPage = 0;
	int updatedPage = 0;
	boolean isUpdating = false;
	boolean isWaiting = false;
	
	public RefPagePresenter(RefListViewPage p){
		page = p;
		topicPageList = new TopicPageList();
		
		page.setOnRefreshListener(new RefreshListenerImpl());
		page.setListViewAdapter(topicPageList.getAdapter());		
	}
	
	public void requestRefresh(){
		page.showLoading();
		page.showMessage("loading request");
		
		ApiService apiService = new ApiService(new ApiListenerImplRefresh());
		apiService.setThreadName("get Topic List");
		
		ApiModel apiModel = new ApiModel();
		setModelConfig(apiModel);
		apiModel.setGoal(ApiModel.TOPIC);
		apiModel.setPage(1);

		topicPageList.clear();
		
		isUpdating = true;
		apiService.request(apiModel);
	}
	
	public void requestUpdate(int page){
		if(!isUpdating){
			ApiService apiService = new ApiService(new ApiListenerImplRefresh());
			apiService.setThreadName("get Topic List");
			
			ApiModel apiModel = new ApiModel();
			setModelConfig(apiModel);
			apiModel.setGoal(ApiModel.TOPIC);
			apiModel.setPage(page);
			isUpdating = true;
			apiService.request(apiModel);
		}
	}

	private void setModelConfig(ApiModel apiModel) {
		apiModel.setFiltermode(GoldenConfig.getFilterMode());
		apiModel.setSensormode(GoldenConfig.getSensorMode());
		apiModel.setType(GoldenConfig.getTypeShot());
	}
	
	class ApiListenerImplRefresh implements ApiListener{

		@Override
		public void onSuccess(String str) {
			topicPageList.addTopicPage(str);
			topicPageList.updateHashMapList(0);
			page.unShowLoading();
			page.showMessage("load complete 1");
			showedPage = 1;
			updatedPage = 1;
		}

		@Override
		public void ontFail(String str) {
			page.showMessage(str);
		}		
	}
	
	class ApiListenerImplUpdate implements ApiListener{

		@Override
		public void onSuccess(String str) {
			topicPageList.addTopicPage(str);
			updatedPage++;
			isUpdating = false;
		}

		@Override
		public void ontFail(String str) {
			page.showMessage(str);
			isUpdating = false;
			if(isWaiting){
				topicPageList.updateHashMapList(updatedPage++);
			}
		}		
	}
	
	class RefreshListenerImpl implements RefreshListener{

		@Override
		public void refreshing() {
			requestRefresh();
		}

		@Override
		public void loadUpdate() {

		}

		@Override
		public void showUpdate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void waitUpdate() {
			isWaiting = true;
		}
		
	}
}
