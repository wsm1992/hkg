package com.siuming.hkg;

import com.siuming.hkg.goldenApi.ApiListener;
import com.siuming.hkg.goldenApi.ApiModel;
import com.siuming.hkg.goldenApi.ApiService;
import com.siuming.hkg.model.TopicPageList;
import com.siuming.hkg.view.component.RefreshListView.RefreshListener;
import com.siuming.hkg.view.page.RefListViewPage;

//control the logic of refresh page
public class TopicPresenter {
	RefListViewPage page;
	TopicPageList topicPageList;
	boolean isUpdating = false;
	boolean isWaiting = true;
	ApiListener apiListener = new ApiListenerImpl();
	
	public TopicPresenter(RefListViewPage p){
		page = p;
		topicPageList = new TopicPageList();
		topicPageList.setBufferSize(3);
		
		page.setOnRefreshListener(new RefreshListenerImpl());
		page.setListViewAdapter(topicPageList.getAdapter());		
	}
	
	public void requestRefresh(){
		page.showLoading();
		page.showMessage("loading request");

		topicPageList.clear();
		loadTopic(1);
		isWaiting = true;
	}
	
	public void requestUpdate(int page){
		if(!isUpdating){
			loadTopic(page);
		}
	}

	private void loadTopic(int page) {
		ApiService apiService = new ApiService(apiListener);
		apiService.setThreadName("get Topic List");			
		ApiModel apiModel = createGetTopicModel(page);
		apiService.request(apiModel);		
		isUpdating = true;
	}
	
	private ApiModel createGetTopicModel(int page){
		ApiModel apiModel = new ApiModel();
		setModelConfig(apiModel);
		apiModel.setGoal(ApiModel.TOPIC);
		apiModel.setPage(page);
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
			topicPageList.addTopicPage(str);
			isUpdating = false;

			page.unShowLoading();
			page.showMessage("load complete "+topicPageList.loadedPage());
			if(topicPageList.canLoad()){
				requestUpdate(topicPageList.loadedPage() + 1);
			}
			
			if(isWaiting){
				topicPageList.updateData();
				isWaiting = false;
				if(topicPageList.showedPage()==1){
					page.setSelection(1);
				}
			}
		}

		@Override
		public void ontFail(String str) {
			page.showMessage(str);
			isUpdating = false;
			//requestUpdate(topicPageList.loadedPage());
			//page.showMessage(str+", loading requst again");
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
			
		}

		@Override
		public void waitUpdate() {
			if(!topicPageList.updateData()){
				isWaiting = true;	
			}	
			if(topicPageList.canLoad()){
				requestUpdate(topicPageList.loadedPage());
			}
		}
		
	}
}
