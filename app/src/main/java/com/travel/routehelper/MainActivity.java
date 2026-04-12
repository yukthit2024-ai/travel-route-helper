package com.travel.routehelper;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travel.routehelper.adapters.RouteAdapter;
import com.travel.routehelper.models.Route;
import com.travel.routehelper.utils.DateUtils;
import com.travel.routehelper.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RouteAdapter.OnRouteClickListener {

    private static final int PERMISSION_REQUEST_STORAGE = 2001;
    private static final int PERMISSION_REQUEST_MANAGE_STORAGE = 2002;

    private RecyclerView recyclerView;
    private RouteAdapter adapter;
    private TextView textViewEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewRoutes);
        textViewEmptyState = findViewById(R.id.textViewEmptyState);
        FloatingActionButton fab = findViewById(R.id.fabAddRoute);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (checkStoragePermission()) {
            loadRoutes();
        } else {
            requestStoragePermission();
        }

        fab.setOnClickListener(view -> {
            if (checkStoragePermission()) {
                showCreateRouteDialog();
            } else {
                requestStoragePermission();
            }
        });
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, PERMISSION_REQUEST_MANAGE_STORAGE);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, PERMISSION_REQUEST_MANAGE_STORAGE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadRoutes();
            } else {
                Toast.makeText(this, "Storage permission is required to list routes.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_MANAGE_STORAGE) {
            if (checkStoragePermission()) {
                loadRoutes();
            } else {
                Toast.makeText(this, "All Files Access is required to list routes from Downloads.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadRoutes() {
        List<File> routeFiles = FileUtils.listRouteFiles(this);
        if (routeFiles.isEmpty()) {
            textViewEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textViewEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        if (adapter == null) {
            adapter = new RouteAdapter(routeFiles, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateData(routeFiles);
        }
    }

    private void showCreateRouteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Route");

        final EditText input = new EditText(this);
        input.setHint("Enter route name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String routeName = input.getText().toString().trim();
            if (!routeName.isEmpty()) {
                createNewRoute(routeName);
            } else {
                Toast.makeText(MainActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void createNewRoute(String name) {
        try {
            Route newRoute = new Route(name, DateUtils.getCurrentTimestampISO());
            FileUtils.saveRoute(this, newRoute);
            loadRoutes();
            Toast.makeText(this, "Route created", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Failed to create route: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRouteClick(File file) {
        Intent intent = new Intent(this, RouteDetailsActivity.class);
        intent.putExtra("FILE_PATH", file.getAbsolutePath());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRoutes();
    }
}
