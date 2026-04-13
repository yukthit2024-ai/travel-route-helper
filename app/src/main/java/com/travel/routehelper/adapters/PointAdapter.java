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
import android.text.TextUtils;
import android.location.Location;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import android.graphics.Color;

public class PointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_POINT = 0;
    private static final int TYPE_CURRENT_LOCATION = 1;

    private List<Point> points;
    private OnPointClickListener listener;
    private Location currentLocation;
    private int userRowPosition = 0; // Default to top

    public interface OnPointClickListener {
        void onPointClick(int position);
    }

    public PointAdapter(List<Point> points, OnPointClickListener listener) {
        this.points = points;
        this.listener = listener;
    }

    public void updateCurrentLocation(Location location) {
        this.currentLocation = location;
        notifyDataSetChanged();
    }

    public void setUserRowPosition(int position) {
        if (points != null && position >= 0 && position <= points.size()) {
            this.userRowPosition = position;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userRowPosition) ? TYPE_CURRENT_LOCATION : TYPE_POINT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CURRENT_LOCATION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_current_location, parent, false);
            return new CurrentLocationViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point, parent, false);
            return new PointViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CurrentLocationViewHolder) {
            bindCurrentLocation((CurrentLocationViewHolder) holder);
        } else if (holder instanceof PointViewHolder) {
            // Adjust position for the points list
            int pointIndex = (position > userRowPosition) ? position - 1 : position;
            bindPoint((PointViewHolder) holder, pointIndex);
        }
    }

    private void bindCurrentLocation(CurrentLocationViewHolder holder) {
        // We could update status text based on distance to next point if needed
        if (currentLocation != null) {
            holder.textViewStatus.setText("Tracking current location...");
        } else {
            holder.textViewStatus.setText("Waiting for GPS...");
        }
    }

    private void bindPoint(PointViewHolder holder, int position) {
        if (points == null || position < 0 || position >= points.size()) return;
        Point point = points.get(position);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        
        // 1. Add Point Name (Bold)
        int startName = builder.length();
        builder.append(point.getName());
        builder.setSpan(new StyleSpan(Typeface.BOLD), startName, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 2. Add Distance if available (Bold, Color)
        if (currentLocation != null) {
            float[] results = new float[1];
            Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    point.getLatitude(), point.getLongitude(), results);
            float distanceInMeters = results[0];
            
            String distanceStr;
            if (distanceInMeters < 1000) {
                distanceStr = String.format(" (%.0f m)", distanceInMeters);
            } else {
                distanceStr = String.format(" (%.1f km)", distanceInMeters / 1000f);
            }
            
            int startDist = builder.length();
            builder.append(distanceStr);
            builder.setSpan(new StyleSpan(Typeface.BOLD), startDist, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#009688")), startDist, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        holder.textViewPointName.setText(builder);

        List<String> types = point.getTypes();
        if (types != null && !types.isEmpty()) {
            holder.textViewPointTypes.setVisibility(View.VISIBLE);
            holder.textViewPointTypes.setText(TextUtils.join(", ", types));
        } else {
            holder.textViewPointTypes.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                // In this method, the 'position' parameter is already the adjusted point index.
                listener.onPointClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (points != null) ? points.size() + 1 : 1;
    }

    public void updateData(List<Point> newPoints) {
        this.points = newPoints;
        notifyDataSetChanged();
    }

    static class PointViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPointName, textViewPointLocation, textViewPointTimestamp, textViewPointTypes;

        PointViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPointName = itemView.findViewById(R.id.textViewPointName);
            textViewPointLocation = itemView.findViewById(R.id.textViewPointLocation);
            textViewPointTimestamp = itemView.findViewById(R.id.textViewPointTimestamp);
            textViewPointTypes = itemView.findViewById(R.id.textViewPointTypes);
        }
    }

    static class CurrentLocationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStatus;

        CurrentLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textViewCurrentStatus);
        }
    }
}
