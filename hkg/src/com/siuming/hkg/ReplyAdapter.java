package com.siuming.hkg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.siuming.hkg.model.Reply.Gender;

public class ReplyAdapter extends SimpleAdapter {

	public ReplyAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
			String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) this.getItem(position);

			changeAuthorNameColor(view, map);
			changeMessageBody(view, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	private void changeAuthorNameColor(View view, HashMap<String, Object> map) {
		TextView textViewName = (TextView) view.findViewById(R.id.textViewAuthorName);
		if ((Gender) map.get("AuthorGender") == Gender.F) {
			textViewName.setTextColor(GoldenConfig.getContext().getResources()
					.getColor(R.color.userRed));
		}
	}

	private void changeMessageBody(View view, HashMap<String, Object> map) {
		TextView textViewMessageBody = (TextView) view.findViewById(R.id.textViewMessageBody);
		textViewMessageBody.setMovementMethod(LinkMovementMethod.getInstance());
//		URLImageGetter urlImgGetter = new URLImageGetter(GoldenConfig.getContext(),
//				textViewMessageBody);
		textViewMessageBody.setText(Html.fromHtml((String) map.get("messageBody"), null,
				null));
	}

}
