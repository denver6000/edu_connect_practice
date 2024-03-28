package com.denproj.educonnectv2.ui.dashboard.group;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentAddGroupBinding;
import com.denproj.educonnectv2.room.entity.Sections;
import com.denproj.educonnectv2.room.entity.Student;
import com.denproj.educonnectv2.ui.dashboard.group.rcv.StudentRecyclerViewAdapter;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.GroupViewModel;

import java.util.List;


public class AddGroupFragment extends Fragment {


    StudentRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAddGroupBinding binding = FragmentAddGroupBinding.inflate(inflater);

        GroupViewModel viewModel = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);
        binding.studentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.setViewModel(viewModel);

        viewModel.loadAllSection(new UITask<List<Sections>>() {
            @Override
            public void onSuccess(List<Sections> result) {
                ArrayAdapter<Sections> arrayAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, result);
                binding.sectionSortSpinner.setAdapter(arrayAdapter);
                viewModel.sectionsList.setValue(result);
            }

            @Override
            public void onFail(String message) {

            }
        });


        adapter = new StudentRecyclerViewAdapter();
        adapter.notifyCheckedChange = (index) -> {
            Student student = viewModel.studentList.getValue().get(index);
            Toast.makeText(requireContext(), student.isSelected + "", Toast.LENGTH_SHORT).show();
        };

        viewModel.studentList.observe(getViewLifecycleOwner(), students -> {
            adapter.setOriginalList(students);
            binding.studentRecyclerView.setAdapter(adapter);

        });



        viewModel.sectionsList.observe(getViewLifecycleOwner(), sections -> {
            if (sections != null && !sections.isEmpty()) {
                binding.sectionSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedSection = sections.get(i).sectionName;
                        Toast.makeText(requireContext(), selectedSection, Toast.LENGTH_SHORT).show();
                        adapter.getFilter().filter(selectedSection);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });
        binding.createGroupAction.setOnClickListener(view -> {
            Toast.makeText(requireContext(), viewModel.groupName.get(), Toast.LENGTH_SHORT).show();
            viewModel.addStudentsToGroup(new UITask<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Toast.makeText(requireContext(), "Group Created. Students were also added.", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.dashboardFragmentContainer);
                    navController.navigate(R.id.action_addGroupFragment_to_groupFragment);
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            });

        });


        return binding.getRoot();
    }
}