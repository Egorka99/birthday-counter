package com.tstu;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern datePattern = Pattern.compile("[0-9]{2}-[0-9]{2}");

    public static final String DASH_STRING = "-";

    public static void isValidDate(String srt) throws Exception {
        if (!datePattern.matcher(srt).find()) throw new Exception("Неверный формат даты");
        String[] parts = srt.split(DASH_STRING);
        if (Integer.parseInt(parts[0]) > 31) throw new Exception("Больше чем 31 день");
        if (Integer.parseInt(parts[1]) > 12) throw new Exception("Больше чем 12 месяцев");
    }

    public static Date getBirthdayDate(Date date) {
        Date now = new Date();
        if(now.getTime() > date.getTime()) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 1);
            return calendar.getTime();
        }
        return date;
    }
}
