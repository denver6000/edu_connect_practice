package com.denproj.educonnectv2.ui.dashboard.calendar;

import android.app.AlertDialog;
import android.icu.util.LocaleData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.EventInfoCardBinding;
import com.denproj.educonnectv2.databinding.EventsCardBinding;
import com.denproj.educonnectv2.room.entity.Events;
import com.denproj.educonnectv2.util.FilesUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        EventInfoCardBinding binding = EventInfoCardBinding.inflate(LayoutInflater.from(holder.binding.getRoot().getContext()));
        holder.binding.getRoot().setOnClickListener(view -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                LocalDate startDate = LocalDate.ofEpochDay(events.eventStartDateInEpoch);
                LocalDate endDate = LocalDate.ofEpochDay(events.eventEndDateInEpoch);
                LocalTime startTime = LocalTime.ofSecondOfDay(events.eventTimeStartInMillis / 1000);
                LocalTime endTime = LocalTime.ofSecondOfDay(events.eventTimeEndInMillis / 1000);

                String eventStartDate = AddEventFragment.MONTHS[startDate.getMonthValue()] + " " + startDate.getDayOfMonth() + " " + startDate.getYear();
                String eventEndDate = AddEventFragment.MONTHS[endDate.getMonthValue()] + " " + endDate.getDayOfMonth() + " " + endDate.getYear();

                String timeStart = startTime.format(formatter);
                String timeEnd = endTime.format(formatter);

                binding.eventDatesInf.setText(eventStartDate +  " to " + eventEndDate);
                binding.timeInfo.setText(timeStart + " to " + timeEnd);
                binding.eventNameInf.setText(events.eventName);

                new AlertDialog.Builder(holder.binding.getRoot().getContext()).setView(binding.getRoot()).show();
            }
        });
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
