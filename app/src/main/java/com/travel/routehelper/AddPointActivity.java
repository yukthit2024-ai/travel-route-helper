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
import android.widget.CheckBox;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPointActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_LOCATION = 1001;
    
    private String filePath;
    private String pointTimestamp = null;
    private MaterialButton buttonDelete;
    private FusedLocationProviderClient fusedLocationClient;
    private double currentLat = 0;
    private double currentLng = 0;
    
    private com.google.android.material.textfield.TextInputEditText editTextPointName;
    private android.widget.TextView textViewLocation;
    private android.widget.CheckBox checkboxPetrol, checkboxFood, checkboxToll, checkboxToilet;
    private com.google.android.gms.tasks.CancellationTokenSource cancellationTokenSource;

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
        checkboxPetrol = findViewById(R.id.checkboxPetrol);
        checkboxFood = findViewById(R.id.checkboxFood);
        checkboxToll = findViewById(R.id.checkboxToll);
        checkboxToilet = findViewById(R.id.checkboxToilet);
        MaterialButton buttonSave = findViewById(R.id.buttonSave);
        MaterialButton buttonRefresh = findViewById(R.id.buttonRefreshLocation);
        buttonDelete = findViewById(R.id.buttonDelete);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        pointTimestamp = getIntent().getStringExtra("POINT_TIMESTAMP");
        if (pointTimestamp != null) {
            getSupportActionBar().setTitle("Edit Point");
            String name = getIntent().getStringExtra("POINT_NAME");
            currentLat = getIntent().getDoubleExtra("POINT_LAT", 0);
            currentLng = getIntent().getDoubleExtra("POINT_LNG", 0);
            ArrayList<String> types = getIntent().getStringArrayListExtra("POINT_TYPES");
            
            editTextPointName.setText(name);
            textViewLocation.setText(String.format("Lat: %.6f\nLng: %.6f", currentLat, currentLng));
            
            if (types != null) {
                if (types.contains("Petrol")) checkboxPetrol.setChecked(true);
                if (types.contains("Food")) checkboxFood.setChecked(true);
                if (types.contains("Toll")) checkboxToll.setChecked(true);
                if (types.contains("Toilet")) checkboxToilet.setChecked(true);
            }
            
            buttonRefresh.setEnabled(false);
            buttonRefresh.setAlpha(0.5f);
            
            buttonDelete.setVisibility(View.VISIBLE);
            buttonDelete.setOnClickListener(v -> confirmDeletePoint());
        } else {
            requestLocation();
        }

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
        cancellationTokenSource = new com.google.android.gms.tasks.CancellationTokenSource();

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

    private void confirmDeletePoint() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.confirm_delete_title)
            .setMessage(R.string.confirm_delete_msg)
            .setPositiveButton(R.string.btn_delete, (dialog, which) -> deletePoint())
            .setNegativeButton(R.string.btn_cancel, null)
            .show();
    }

    private void deletePoint() {
        try {
            File file = new File(filePath);
            Route route = FileUtils.loadRoute(file);
            
            for (Point p : route.getPoints()) {
                if (p.getTimestamp().equals(pointTimestamp)) {
                    p.setDeleted(true);
                    break;
                }
            }
            
            FileUtils.saveRoute(this, route);
            Toast.makeText(this, R.string.point_deleted, Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            Toast.makeText(this, "Failed to delete point: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

        List<String> selectedTypes = new ArrayList<>();
        if (checkboxPetrol.isChecked()) selectedTypes.add("Petrol");
        if (checkboxFood.isChecked()) selectedTypes.add("Food");
        if (checkboxToll.isChecked()) selectedTypes.add("Toll");
        if (checkboxToilet.isChecked()) selectedTypes.add("Toilet");

        try {
            File file = new File(filePath);
            Route route = FileUtils.loadRoute(file);
            
            if (pointTimestamp != null) {
                // Edit Mode: find point by timestamp
                for (Point p : route.getPoints()) {
                    if (p.getTimestamp().equals(pointTimestamp)) {
                        // Update fields
                        // Note: We create a new Point object or update existing one.
                        // Point model doesn't have setters for all fields currently, so let's check.
                        // Actually, name, lat, lng are final-ish (private).
                        // I'll replace it in the list if necessary, or better, add setters to Point.
                        // For now, I'll find its index and replace it.
                        int index = route.getPoints().indexOf(p);
                        Point updatedPoint = new Point(name, p.getLatitude(), p.getLongitude(), p.getTimestamp(), selectedTypes);
                        updatedPoint.setDeleted(false); // Ensure it's not deleted if we're saving it
                        route.getPoints().set(index, updatedPoint);
                        break;
                    }
                }
            } else {
                // Add Mode
                Point newPoint = new Point(name, currentLat, currentLng, DateUtils.getCurrentTimestampISO(), selectedTypes);
                route.addPoint(newPoint);
            }
            
            FileUtils.saveRoute(this, route);
            
            Toast.makeText(this, pointTimestamp != null ? "Point updated" : "Point saved", Toast.LENGTH_SHORT).show();
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
