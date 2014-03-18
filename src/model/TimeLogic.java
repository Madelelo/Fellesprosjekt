package model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;

public abstract class TimeLogic {
	
	public static boolean isValidAlarmTime(String alarmTime, String appmntDate, String appmntTime) {
		boolean isValid = false;
		if(isValidTimeString(alarmTime)) {
			int year = Integer.parseInt(appmntDate.split("-")[0]);
			int month = Integer.parseInt(appmntDate.split("-")[1]);
			int day = Integer.parseInt(appmntDate.split("-")[2]);
			int appmntHour = Integer.parseInt(appmntTime.split(":")[0]);
			int appmntMin = Integer.parseInt(appmntTime.split(":")[1]);
			int alarmHour = Integer.parseInt(alarmTime.split(":")[0]);
			int alarmMin = Integer.parseInt(alarmTime.split(":")[1]);
			
			DateTime alarm = new DateTime(year, month, day, alarmHour, alarmMin);
			DateTime appmnt = new DateTime(year, month, day, appmntHour, appmntMin);
			
			if(alarm.getHourOfDay() < appmnt.getHourOfDay()) {
				isValid = true;
			} else if(alarm.getHourOfDay() == appmnt.getHourOfDay() && alarm.getMinuteOfHour() <= appmnt.getMinuteOfHour()) {
				isValid = true;
			}
		}
		
		return isValid;
	}
	
	public static boolean isValidTimeString(String time) {
		boolean isValid = false;
		String[] timeArr = time.split(":");
		
		if(timeArr.length == 3 && timeArr[0].length() == 2 && timeArr[1].length() == 2 && timeArr[2].length() == 2) {
			String hourS = timeArr[0];
			String minS = timeArr[1];
			String secS = timeArr[2];
			
			if(Character.isDigit(hourS.charAt(0)) && Character.isDigit(hourS.charAt(1))
					&& Character.isDigit(minS.charAt(0)) && Character.isDigit(minS.charAt(1))
					&& Character.isDigit(secS.charAt(0)) && Character.isDigit(secS.charAt(1))) {
				
				int hour = Integer.parseInt(hourS);
				int min = Integer.parseInt(minS);
				int sec = Integer.parseInt(secS);
				
				if(hour < 24 && min < 60 && sec < 60) {
					isValid = true;
				}
			}
		}
		
		return isValid;
	}
	
	public static boolean isValidDateString(String date) {
		boolean isValid = false;
		String[] dateArr = date.split("-");
		
		if(dateArr.length == 3 && dateArr[0].length() == 4 && dateArr[1].length() == 2 && dateArr[2].length() == 2) {
			String yearS = dateArr[0];
			String monthS = dateArr[1];
			String dayS = dateArr[2];
			
			if(Character.isDigit(yearS.charAt(0)) && Character.isDigit(yearS.charAt(1))
					&& Character.isDigit(monthS.charAt(0)) && Character.isDigit(monthS.charAt(1))
					&& Character.isDigit(dayS.charAt(0)) && Character.isDigit(dayS.charAt(1))) {
				
				int month = Integer.parseInt(monthS);
				int day = Integer.parseInt(dayS);
				
				if(month <= 12 && day <= 31) {
					isValid = true;
					
					if((month == 2 || month == 4 || month == 6 || month == 9 || month == 11)
							&& day > 30) {
						isValid = false;
					}
					
					if(month == 2 && day > 28) {
						isValid = false;
					}
				}
			}
		}
		
		return isValid;
	}
	
	public static boolean isValidDurationString(String dur) {
		boolean isValid = false;
		String[] durArr = dur.split(":");
		
		if(durArr.length == 2 && durArr[0].length() < 2 && durArr[1].length() < 3) {
			String hourS = durArr[0];
			String minS = durArr[1];
			
			if(Character.isDigit(hourS.charAt(0))
					&& Character.isDigit(minS.charAt(0)) && Character.isDigit(minS.charAt(1))) {
				
				int hour = Integer.parseInt(hourS);
				int min = Integer.parseInt(minS);
				
				if(hour < 10 && min < 60) {
					isValid = true;
				}
			}
		}
		
		return isValid;
	}
	
	public static boolean timeOverlaps(String date1, String start1, String end1, String dur1, String date2, String start2, String end2, String dur2) {
		boolean isOverlap = false;
		
		int year1 = Integer.parseInt(date1.split("-")[0]);
		int month1 = Integer.parseInt(date1.split("-")[1]);
		int day1 = Integer.parseInt(date1.split("-")[2]);
		int year2 = Integer.parseInt(date2.split("-")[0]);
		int month2 = Integer.parseInt(date2.split("-")[1]);
		int day2 = Integer.parseInt(date2.split("-")[2]);
		
		int startHour1 = Integer.parseInt(start1.split(":")[0]);
		int startMin1 = Integer.parseInt(start1.split(":")[1]);
		int endHour1 = -1;
		int endMin1 = -1;
		
		int startHour2 = Integer.parseInt(start2.split(":")[0]);
		int startMin2 = Integer.parseInt(start2.split(":")[1]);
		int endHour2 = -1;
		int endMin2 = -1;
		
		if(!dur1.isEmpty()) {
			endHour1 = startHour1 + Integer.parseInt(dur1.split(":")[0]);
			endMin1 = startMin1 + Integer.parseInt(dur1.split(":")[1]);
			if(endMin1 > 59) {
				endHour1 += 1;
				endMin1 -= 60;
			}
		} else {
			endHour1 = Integer.parseInt(end1.split(":")[0]);
			endMin1 = Integer.parseInt(end1.split(":")[1]);
		}
		
		if(!dur2.isEmpty()) {
			endHour2 = startHour2 + Integer.parseInt(dur2.split(":")[0]);
			endMin2 = startMin2 + Integer.parseInt(dur2.split(":")[1]);
			if(endMin2 > 59) {
				endHour2 += 1;
				endMin2 -= 60;
			}
		} else {
			endHour2 = Integer.parseInt(end2.split(":")[0]);
			endMin2 = Integer.parseInt(end2.split(":")[1]);
		}
		
		DateTime starttime1 = new DateTime(year1, month1, day1, startHour1, startMin1);
		DateTime endtime1 = new DateTime(year1, month1, day1, endHour1, endMin1);
		DateTime starttime2 = new DateTime(year2, month2, day2, startHour2, startMin2);
		DateTime endtime2 = new DateTime(year2, month2, day2, endHour2, endMin2);
		
		Interval time1 = new Interval(starttime1.toInstant(), endtime1.toInstant());
		Interval time2 = new Interval(starttime2.toInstant(), endtime2.toInstant());
		
		if(time1.overlaps(time2)) {
			isOverlap = true;
		}
		
		return isOverlap;
	}
}
