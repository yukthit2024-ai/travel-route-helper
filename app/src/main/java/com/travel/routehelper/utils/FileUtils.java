package com.travel.routehelper.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.travel.routehelper.models.Route;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final String ROUTES_DIR = "routes";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static File getRoutesDirectory(Context context) {
        File dir = new File(context.getExternalFilesDir(null), ROUTES_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static void saveRoute(Context context, Route route) throws IOException {
        File dir = getRoutesDirectory(context);
        File file = new File(dir, route.getRouteName() + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(route, writer);
        }
    }

    public static Route loadRoute(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Route.class);
        }
    }

    public static List<File> listRouteFiles(Context context) {
        File dir = getRoutesDirectory(context);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        List<File> routeFiles = new ArrayList<>();
        if (files != null) {
            for (File f : files) routeFiles.add(f);
        }
        return routeFiles;
    }
}
