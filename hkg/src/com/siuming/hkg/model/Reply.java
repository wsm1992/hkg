package com.siuming.hkg.model;

import org.json.JSONObject;
import org.json.JSONStringer;

import com.siuming.hkg.util.TimeUtil;

public class Reply {
	final String REPLY_ID = "Reply_ID";
	final String AUTHOR_NAME = "Author_Name";
	final String AUTHOR_GENDER = "Author_Gender";
	final String AUTHOR_ID = "Author_ID";
	final String MESSAGE_DATE = "Message_Date";
	final String MESSAGE_BODY = "Message_Body";

	String replyId;
	String authorName;
	Gender authorGender;
	String AuthorId;
	String messageDate;
	String messageBody;
	Boolean isBlock;

	public Reply(JSONObject replyJobj) {
		try {
			replyId = replyJobj.getString(REPLY_ID);
			authorName = replyJobj.getString(AUTHOR_NAME);
			authorGender = replyJobj.getString(AUTHOR_GENDER).equals("M") ? Gender.M : Gender.F;
			AuthorId = replyJobj.getString(AUTHOR_ID);
			messageDate = replyJobj.getString(MESSAGE_DATE);
			messageBody = replyJobj.getString(MESSAGE_BODY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getReplyId() {
		return replyId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public Gender getAuthorGender() {
		return authorGender;
	}

	public String getAuthorId() {
		return AuthorId;
	}

	public String getMessageDate() {
		messageDate = messageDate.substring(6, messageDate.length() - 2);
		long replyDate = Long.parseLong(messageDate);		
		return TimeUtil.getRelativeTime(replyDate);
	}	

	public String getMessageBody() {
		return messageBody;
	}

	public Boolean getIsBlock() {
		return isBlock;
	}

	public enum Gender {
		M, F
	}

	public void genJsonStringer(JSONStringer jsonText) {
		try {

			jsonText.object();
			jsonText.key(REPLY_ID).value(replyId);
			jsonText.key(AUTHOR_NAME).value(authorName);
			jsonText.key(AUTHOR_GENDER).value("M");
			jsonText.key(AUTHOR_ID).value(AuthorId);
			jsonText.key(MESSAGE_DATE).value("/Date(1394868171000)/");
			jsonText.key(MESSAGE_BODY).value(messageBody);
			jsonText.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
