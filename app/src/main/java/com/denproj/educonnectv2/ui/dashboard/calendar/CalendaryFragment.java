package com.denproj.educonnectv2.ui.dashboard.calendar;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentCalendaryBinding;
import com.denproj.educonnectv2.room.entity.Events;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.CalendarViewModel;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;


public class CalendaryFragment extends Fragment {

    EventsRCVAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCalendaryBinding binding = FragmentCalendaryBinding.inflate(inflater);
        CalendarViewModel viewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        binding.eventRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        viewModel.getAllEvents(new UITask<List<Events>>() {
            @Override
            public void onSuccess(List<Events> result) {
                adapter = new EventsRCVAdapter(result);
                Toast.makeText(requireContext(), "Events " + adapter, Toast.LENGTH_SHORT).show();
                binding.eventRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFail(String message) {

            }
        });
        NavController navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
        binding.addEventCardRedirect.setOnClickListener(view -> {
            navController.navigate(R.id.action_calendaryFragment_to_addEventFragment);
        });

        binding.filterDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (datePicker, year, month, day) -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate localDate = LocalDate.of(year, month, day);
                    adapter.filterEventsByStartDate(localDate.toEpochDay());
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        return binding.getRoot();
    }


}