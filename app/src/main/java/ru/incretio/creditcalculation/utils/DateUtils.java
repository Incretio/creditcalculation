package ru.incretio.creditcalculation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public static String formatDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return formatDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String formatDate(int year, int month, int dayOfMonth) {
        return String.format("%02d.%02d.%d", new Object[]{Integer.valueOf(dayOfMonth), Integer.valueOf(month), Integer.valueOf(year)});
    }

    public static String formatDateWithoutDayOfMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return formatDateWithoutDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }

    public static String formatDateWithoutDayOfMonth(int year, int month) {
        return String.format("%02d.%d", new Object[]{Integer.valueOf(month), Integer.valueOf(year)});
    }

    public static Date convertStrToDateElseGetNow(String dateString) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date convertStrToDateElseGetDefaultValue(String dateString, Date defaultValue) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            return defaultValue;
        }
    }
}
