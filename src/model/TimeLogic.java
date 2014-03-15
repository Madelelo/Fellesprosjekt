package model;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public abstract class TimeLogic {
	
	public static boolean isValidAlarmTime(String alarmTime, String appmntDate, String appmntTime) {
		boolean isValid = false;
		
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
		
		return isValid;
	}
}
