package com.siuming.hkg.util;

import java.text.DateFormat;
import java.util.Date;

public class TimeUtil {
	static public String getRelativeTime(long date){
		long nowDate = System.currentTimeMillis();
		return countTime(date, nowDate);
	}

	static public String countTime(long postDate, long nowDate) {
		final int SECOND = 1000;
		final int MINUTE = 60 * SECOND;
		final int HOUR = 60 * MINUTE;
		final int DAY = 24 * HOUR;
		String result = "";
		long time = nowDate - postDate;
		if (time / DAY < 7) {
			if (time / DAY > 0) {
				result = time / DAY + "天前";
			} else if (time / HOUR > 0) {
				result = time / HOUR + "小時前";
			} else if (time / MINUTE > 0) {
				result = time / MINUTE + "分鐘前";
			} else if (time / SECOND > 0) {
				result = time / SECOND + "秒前";
			}
		} else {
			DateFormat dateFormat = DateFormat.getDateTimeInstance();
			Date date = new Date(postDate);
			result = dateFormat.format(date);
		}
		return result;
	}
}
