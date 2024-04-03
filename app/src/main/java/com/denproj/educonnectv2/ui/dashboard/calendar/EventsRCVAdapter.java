package com.denproj.educonnectv2.ui.dashboard.calendar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.EventsCardBinding;
import com.denproj.educonnectv2.room.entity.Events;
import com.denproj.educonnectv2.util.FilesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsRCVAdapter extends RecyclerView.Adapter<EventsRCVAdapter.ViewHolder> implements Filterable {

    List<Events> eventsList;
    List<Events> selected = new ArrayList<>();
    long epochDay;


    public EventsRCVAdapter(List<Events> eventsList) {
        this.eventsList = eventsList;
        selected.addAll(eventsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.events_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Events events = eventsList.get(position);
        holder.binding.setEvents(events);
        String[] imagePath = events.posterPath.split("/");
        try {
            holder.binding.imageView6.setImageBitmap(FilesUtil.decodeImageFromPath(imagePath[0], imagePath[1], holder.binding.getRoot().getContext()));
        } catch (IOException e) {
            Log.e("RCV", "Error", e);
            throw new RuntimeException(e);
        }
    }

    public void filterEventsByStartDate(long epochDay) {
        this.epochDay = epochDay;
        this.getFilter().filter("");
    }

    @Override
    public int getItemCount() {
        return selected.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                for (Events event:eventsList) {
                    Log.d("Filter", event.eventStartDateInEpoch + ": OBJ == " + epochDay + ": Param = " + (event.eventStartDateInEpoch == EventsRCVAdapter.this.epochDay));
                    if (event.eventStartDateInEpoch == EventsRCVAdapter.this.epochDay) {
                        selected.add(event);
                    } else {
                        selected.remove(event);
                    }
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EventsCardBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EventsCardBinding.bind(itemView);
        }
    }
}
