package com.siuming.hkg.model;

import org.json.JSONObject;
import org.json.JSONStringer;

public class Topic {
	final String MESSAGE_ID = "Message_ID";
	final String MESSAGE_TITLE = "Message_Title";
	final String AUTHOR_ID = "Author_ID";
	final String AUTHOR_NAME = "Author_Name";
	final String LAST_REPLY_DATE = "Last_Reply_Date";
	final String TOTAL_REPLIES = "Total_Replies";
	final String MESSAGE_STATUS = "Message_Status";
	final String MESSAGE_BODY = "Message_Body";
	final String RATING = "Rating";
	String messageId;
	String messageTitle;
	String autherId;
	String authorName;
	String lastReplayDate;
	String totalReplies;
	String messageStatus;
	String messageBody;
	String rating;


	public Topic(JSONObject topicJobj) {
		try {
			messageId = topicJobj.getString(MESSAGE_ID);
			messageTitle = topicJobj.getString(MESSAGE_TITLE);
			autherId = topicJobj.getString(AUTHOR_ID);
			authorName = topicJobj.getString(AUTHOR_NAME);
			lastReplayDate = topicJobj.getString(LAST_REPLY_DATE);
			lastReplayDate = lastReplayDate.substring(6, lastReplayDate.length()-2);
			totalReplies = topicJobj.getString(TOTAL_REPLIES);
			messageStatus = topicJobj.getString(MESSAGE_STATUS);
			messageBody = topicJobj.getString(MESSAGE_BODY);
			rating = topicJobj.getString(RATING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMessageId() {
		return messageId;
	}
	
	public int getMessageIdInt() {
		return Integer.parseInt(messageId);
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public String getAutherId() {
		return autherId;
	}

	public String getAutherName() {
		return authorName;
	}

	public String getLastReplayDate() {
		return lastReplayDate;
	}

	public String getTotalReplies() {
		return "回覆: " + totalReplies;
	}
	
	public int getTotalRepliesInt() {
		return Integer.parseInt(totalReplies);
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public String getRatingStr() {
		return "評分: " + rating;
	}

	public void genJsonStringer(JSONStringer jsonText) {
		try {
			jsonText.object();
			jsonText.key(MESSAGE_ID).value(messageId);
			jsonText.key(MESSAGE_TITLE).value(messageTitle);
			jsonText.key(AUTHOR_ID).value(autherId);
			jsonText.key(AUTHOR_NAME).value(authorName);
			jsonText.key(LAST_REPLY_DATE).value(lastReplayDate);
			jsonText.key(TOTAL_REPLIES).value(totalReplies);
			jsonText.key(MESSAGE_STATUS).value(messageStatus);
			jsonText.key(MESSAGE_BODY).value(messageBody);
			jsonText.key(RATING).value(rating);
			jsonText.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
