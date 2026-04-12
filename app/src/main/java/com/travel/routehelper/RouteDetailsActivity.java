package com.travel.routehelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travel.routehelper.adapters.PointAdapter;
import com.travel.routehelper.models.Route;
import com.travel.routehelper.utils.FileUtils;
import java.io.File;
import java.io.IOException;

public class RouteDetailsActivity extends AppCompatActivity {

    private String filePath;
    private Route currentRoute;
    private PointAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        filePath = getIntent().getStringExtra("FILE_PATH");
        if (filePath == null) {
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewPoints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddPoint);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(RouteDetailsActivity.this, AddPointActivity.class);
            intent.putExtra("FILE_PATH", filePath);
            startActivity(intent);
        });

        loadRouteData();
    }

    private void loadRouteData() {
        try {
            currentRoute = FileUtils.loadRoute(new File(filePath));
            getSupportActionBar().setTitle(currentRoute.getRouteName());
            
            if (adapter == null) {
                adapter = new PointAdapter(currentRoute.getPoints());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateData(currentRoute.getPoints());
            }
        } catch (IOException e) {
            Toast.makeText(this, "Failed to load route: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRouteData();
    }
}
