package com.denproj.educonnectv2.ui.dashboard.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentAddEventBinding;
import com.denproj.educonnectv2.room.entity.Events;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.CalendarViewModel;
import com.denproj.educonnectv2.viewModel.DashboardViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddEventFragment extends Fragment {


    public static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
    };
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    FragmentAddEventBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddEventBinding.inflate(inflater);
        CalendarViewModel viewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), o -> {
            viewModel.uriLiveData.setValue(o);
            binding.imageView5.setImageURI(o);
        });

        binding.setViewModel(viewModel);

        binding.startDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (datePicker, i, i1, i2) -> {
                binding.startDate.setText(MONTHS[i1] + " " + i2 + " " + i);
                LocalDate localDate = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    localDate = LocalDate.of(i, i1, i2);
                    viewModel.startDayInEpoch.setValue(localDate.toEpochDay());
                    binding.startDate.setText(MONTHS[i1] + " " + i2 + " " + i);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        binding.endDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (datePicker, i, i1, i2) -> {
                LocalDate localDate = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    localDate = LocalDate.of(i, i1, i2);
                    viewModel.endDayInEpoch.setValue(localDate.toEpochDay());
                    binding.endDate.setText(MONTHS[i1] + " " + i2 + " " + i);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        binding.startTime.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(requireContext(), (timePicker, i, i1) -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalTime localTime = LocalTime.of(i, i1);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                    binding.startTime.setText(localTime.format(formatter));
                    viewModel.startTimeMs.setValue(convertHoursAndMinuteToMd(i, i1));
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        });

        binding.endTime.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(requireContext(), (timePicker, i, i1) -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalTime localTime = LocalTime.of(i, i1);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                    binding.endTime.setText(localTime.format(formatter));
                    viewModel.endTimeMs.setValue(convertHoursAndMinuteToMd(i, i1));
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        });

        viewModel.startTimeMs.observe(getViewLifecycleOwner(), aLong -> {

        });

        viewModel.endTimeMs.observe(getViewLifecycleOwner(), aLong -> {

        });

        binding.addEventActionButton.setOnClickListener(view -> {
            viewModel.addEvent(requireContext(), new UITask<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.imageView4.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
        });

        return binding.getRoot();
    }

    void clearAlLFields() {
        binding.imageView5.setImageURI(null);
        binding.startTime.setText("Start Time");
        binding.endTime.setText("Start Time");
        binding.startDate.setText("Start Date");
        binding.endDate.setText("End Date");
        binding.eventNameField.setText("");
        binding.eventDescField.setText("");
    }

    public long convertHoursAndMinuteToMd(int hour, int minute) {
        return (hour * 60 * 60 * 1000) + (minute * 60 * 1000);
    }

    public HashMap<String, Long> getHourAndMinuteFromMs(long millisecond) {
        long total_seconds = millisecond / 1000;
        long total_minute = total_seconds / 60;
        long total_hour = total_minute / 60;
        HashMap<String, Long> hashMap = new HashMap<>();
        hashMap.put("hour", total_hour);
        hashMap.put("minute", total_minute % 60);

        return hashMap;
    }
}