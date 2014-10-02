package com.siuming.hkg;

import com.siuming.hkg.GoldenDataHandler.ApiListener;
import com.siuming.hkg.goldenApi.ApiService;
import com.siuming.hkg.view.page.RefListViewPage;

public class RefPagePresenter {
	RefListViewPage page;
	
	public RefPagePresenter(RefListViewPage p){
		page = p;
	}
	
	public void requestRefresh(){
		ApiService apiService = new ApiService(new ApiListenerImpl());
		apiService.setPage(1);
		apiService.setThreadName("get Topic List");
		apiService.request();
	}
	
	class ApiListenerImpl implements ApiListener{

		@Override
		public void onSuccess(String str) {
			String abc = str;
			String bbb = abc + "aaa";
		}

		@Override
		public void ontFail(String str) {

		}		
	}
}
