package model;

import org.joda.time.DateTime;
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
}
