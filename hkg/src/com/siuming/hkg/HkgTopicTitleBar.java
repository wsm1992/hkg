package com.siuming.hkg;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

// the view of main title bar
public class HkgTopicTitleBar {
	Spinner spinnerType;
	Button buttonAddTopic;
	Button buttonSetting;
	TitleBarListener titleBarListener;
	
	public HkgTopicTitleBar(Activity activity){
		spinnerType = (Spinner) activity.findViewById(R.id.spinnerType);
		buttonAddTopic = (Button) activity.findViewById(R.id.buttonAddTopic);
		buttonSetting = (Button) activity.findViewById(R.id.buttonSetting);
	}
	
	public void setTitleBarListener(TitleBarListener listener){
		titleBarListener = listener;
		setSpinner();
		setSettingButton();
	}

	private void setSpinner() {
		spinnerType.setSelection(GoldenConfig.getType());
		spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
				titleBarListener.onSpinnerSelected(parent, view, position, arg3);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void setSettingButton() {
		buttonSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				titleBarListener.onSettingButtonClick(arg0);
			}
		});
	}
	
	public interface TitleBarListener {
		void onSpinnerSelected(AdapterView<?> parent, View view, int position, long arg3);

		void onSettingButtonClick(View arg0);

		void onPostButtonClick(View arg0);
	}

}
