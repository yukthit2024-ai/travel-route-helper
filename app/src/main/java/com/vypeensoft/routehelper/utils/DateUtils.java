package com.vypeensoft.routehelper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    private static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static String getCurrentTimestampISO() {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT, Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date());
    }
}
