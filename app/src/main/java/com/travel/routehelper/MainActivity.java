package com.travel.routehelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
        
        loadRoutes();

        fab.setOnClickListener(view -> showCreateRouteDialog());
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
