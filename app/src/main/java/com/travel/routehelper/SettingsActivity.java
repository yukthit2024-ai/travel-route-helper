package com.travel.routehelper;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.travel.routehelper.utils.SettingsManager;

public class SettingsActivity extends AppCompatActivity {

    private SettingsManager settingsManager;
    private Spinner spinnerGpsInterval;
    private String[] intervalValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsManager = new SettingsManager(this);
        intervalValues = getResources().getStringArray(R.array.gps_interval_values);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        spinnerGpsInterval = findViewById(R.id.spinnerGpsInterval);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gps_interval_labels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGpsInterval.setAdapter(adapter);

        // Set current selection
        int currentInterval = settingsManager.getGpsRefreshInterval();
        for (int i = 0; i < intervalValues.length; i++) {
            if (Integer.parseInt(intervalValues[i]) == currentInterval) {
                spinnerGpsInterval.setSelection(i);
                break;
            }
        }

        spinnerGpsInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int newValue = Integer.parseInt(intervalValues[position]);
                if (newValue != settingsManager.getGpsRefreshInterval()) {
                    settingsManager.setGpsRefreshInterval(newValue);
                    Toast.makeText(SettingsActivity.this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
