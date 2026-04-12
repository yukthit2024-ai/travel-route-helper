package com.travel.routehelper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.travel.routehelper.R;
import com.travel.routehelper.models.Point;
import java.util.List;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.PointViewHolder> {

    private List<Point> points;

    public PointAdapter(List<Point> points) {
        this.points = points;
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point, parent, false);
        return new PointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        Point point = points.get(position);
        holder.textViewPointName.setText(point.getName());
        holder.textViewPointLocation.setText(String.format("Lat: %.6f, Lng: %.6f", point.getLatitude(), point.getLongitude()));
        holder.textViewPointTimestamp.setText(point.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return points.size();
    }

    public void updateData(List<Point> newPoints) {
        this.points = newPoints;
        notifyDataSetChanged();
    }

    static class PointViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPointName, textViewPointLocation, textViewPointTimestamp;

        PointViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPointName = itemView.findViewById(R.id.textViewPointName);
            textViewPointLocation = itemView.findViewById(R.id.textViewPointLocation);
            textViewPointTimestamp = itemView.findViewById(R.id.textViewPointTimestamp);
        }
    }
}
