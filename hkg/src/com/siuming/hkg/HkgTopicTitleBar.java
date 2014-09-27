package com.siuming.hkg;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

public class HkgTopicTitleBar {
	Spinner spinnerType;
	Button buttonAddTopic;
	Button buttonSetting;
	
	public HkgTopicTitleBar(Activity activity){
		spinnerType = (Spinner) activity.findViewById(R.id.spinnerType);
		buttonAddTopic = (Button) activity.findViewById(R.id.buttonAddTopic);
		buttonSetting = (Button) activity.findViewById(R.id.buttonSetting);
	}
	private void setTitleBar() {
		setSpinner();
		setSettingButton();
	}

	private void setSettingButton() {
		buttonSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*try {
					Intent intent = new Intent();
					intent.setClass(TopicActivity.this, SettingActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
		});
	}

	private void setSpinner() {
		spinnerType.setSelection(GoldenConfig.getType());
		spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
				/*Toast.makeText(getApplicationContext(), "selected " + position, Toast.LENGTH_SHORT)
						.show();
				GoldenConfig.setType(position);
				activityPresenter.loadRefreshList();*/
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
}
