package com.personalapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

    public static String formatDate(Date date) {
        return (date != null) ? DATE_FORMAT.format(date) : null;
    }

    public static Date parseDate(String dateStr) {
        try {
            return (dateStr != null && !dateStr.isEmpty()) ? DATE_FORMAT.parse(dateStr) : null;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Format tanggal tidak valid! Gunakan format dd-MMM-yyyy");
        }
    }

}

