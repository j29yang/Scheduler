package com.scm.dashboard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtils {

	public static String pageFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	/**
	 * this is for count duration between two timestamp; parameter must be String type,and will return how many hours for this
	 * duration
	 * @param startTime
	 * @param endTime
	 * @return 
	 * @throws ParseException
	 */
	public static long countDuration(String startTime, String endTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = df.parse(startTime);
		Date end = df.parse(endTime);
		long duration = end.getTime()-start.getTime();
		return duration/(60*60*1000);
	}

	public static boolean isExpire(Date date, int threshold) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, threshold);

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		return calendar.before(now);
	}
	
	public static Date parse(String time) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(time);	
	}
	
	public static Date parseCorrectToDate(String time) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(time);	
	}
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 7, 7, 1, 0, 0);

		System.out.println(isExpire(calendar.getTime(), 48));
		System.out.println(isExpire(calendar.getTime(), 24));
	}
}
