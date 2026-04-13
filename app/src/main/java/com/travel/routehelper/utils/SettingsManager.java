package com.travel.routehelper.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private static final String PREF_NAME = "route_helper_prefs";
    private static final String KEY_GPS_INTERVAL = "gps_refresh_interval";
    
    // Default interval is 60000ms (1 minute)
    public static final int DEFAULT_INTERVAL_MS = 60000;

    private final SharedPreferences sharedPreferences;

    public SettingsManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public int getGpsRefreshInterval() {
        return sharedPreferences.getInt(KEY_GPS_INTERVAL, DEFAULT_INTERVAL_MS);
    }

    public void setGpsRefreshInterval(int intervalMs) {
        sharedPreferences.edit().putInt(KEY_GPS_INTERVAL, intervalMs).apply();
    }
}
