package com.travel.routehelper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.travel.routehelper.R;
import java.io.File;
import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private List<File> routeFiles;
    private OnRouteClickListener listener;
    private OnRouteLongClickListener longClickListener;

    public interface OnRouteClickListener {
        void onRouteClick(File file);
    }

    public interface OnRouteLongClickListener {
        void onRouteLongClick(File file);
    }

    public RouteAdapter(List<File> routeFiles, OnRouteClickListener listener, OnRouteLongClickListener longClickListener) {
        this.routeFiles = routeFiles;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        File file = routeFiles.get(position);
        String name = file.getName().replace(".json", "");
        holder.textViewRouteName.setText(name);
        holder.textViewRouteInfo.setText("File: " + file.getName());
        holder.itemView.setOnClickListener(v -> listener.onRouteClick(file));
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onRouteLongClick(file);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return routeFiles.size();
    }

    public void updateData(List<File> newFiles) {
        this.routeFiles = newFiles;
        notifyDataSetChanged();
    }

    static class RouteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRouteName, textViewRouteInfo;

        RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRouteName = itemView.findViewById(R.id.textViewRouteName);
            textViewRouteInfo = itemView.findViewById(R.id.textViewRouteInfo);
        }
    }
}
