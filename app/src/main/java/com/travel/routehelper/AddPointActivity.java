package com.travel.routehelper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.travel.routehelper.models.Point;
import com.travel.routehelper.models.Route;
import com.travel.routehelper.utils.DateUtils;
import com.travel.routehelper.utils.FileUtils;
import java.io.File;
import java.io.IOException;

public class AddPointActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_LOCATION = 1001;
    
    private String filePath;
    private FusedLocationProviderClient fusedLocationClient;
    private double currentLat = 0;
    private double currentLng = 0;
    
    private TextInputEditText editTextPointName;
    private TextView textViewLocation;
    private CancellationTokenSource cancellationTokenSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);

        filePath = getIntent().getStringExtra("FILE_PATH");
        if (filePath == null) {
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        editTextPointName = findViewById(R.id.editTextPointName);
        textViewLocation = findViewById(R.id.textViewLocation);
        MaterialButton buttonSave = findViewById(R.id.buttonSave);
        MaterialButton buttonRefresh = findViewById(R.id.buttonRefreshLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestLocation();

        buttonRefresh.setOnClickListener(v -> requestLocation());
        buttonSave.setOnClickListener(v -> savePoint());
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
            return;
        }

        textViewLocation.setText("Requesting fresh location...");
        
        if (cancellationTokenSource != null) {
            cancellationTokenSource.cancel();
        }
        cancellationTokenSource = new CancellationTokenSource();

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
            .addOnSuccessListener(this, location -> {
                if (location != null) {
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                    textViewLocation.setText(String.format("Lat: %.6f\nLng: %.6f", currentLat, currentLng));
                } else {
                    textViewLocation.setText("Location unavailable. Try refreshing.");
                }
            })
            .addOnFailureListener(e -> {
                textViewLocation.setText("Failed to get location: " + e.getMessage());
            });
    }

    private void savePoint() {
        String name = editTextPointName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentLat == 0 && currentLng == 0) {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File file = new File(filePath);
            Route route = FileUtils.loadRoute(file);
            
            Point newPoint = new Point(name, currentLat, currentLng, DateUtils.getCurrentTimestampISO());
            route.addPoint(newPoint);
            
            FileUtils.saveRoute(this, route);
            
            Toast.makeText(this, "Point saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            Toast.makeText(this, "Failed to save point: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                textViewLocation.setText("Permission denied.");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancellationTokenSource != null) {
            cancellationTokenSource.cancel();
        }
    }
}
