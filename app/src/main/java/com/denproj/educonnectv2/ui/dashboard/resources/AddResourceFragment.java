package com.denproj.educonnectv2.ui.dashboard.resources;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FragmentAddResourceBinding;
import com.denproj.educonnectv2.room.entity.ResourceFile;
import com.denproj.educonnectv2.ui.dashboard.resources.rcv.SelectFilesRCV;
import com.denproj.educonnectv2.util.UITask;
import com.denproj.educonnectv2.viewModel.ResourceViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddResourceFragment extends Fragment {

    FragmentAddResourceBinding binding;

    ActivityResultLauncher<String> resultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddResourceBinding.inflate(inflater);
        SelectFilesRCV adapter = new SelectFilesRCV(new HashMap<>());
        ResourceViewModel viewModel = new ViewModelProvider(requireActivity()).get(ResourceViewModel.class);
        binding.filesListRcv.setLayoutManager(new LinearLayoutManager(requireContext()));



        resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), o -> {
            String fileName = getFileNameFromUri(o);
            adapter.addItem(fileName, o);
            Toast.makeText(requireContext(), "The file is " + fileName, Toast.LENGTH_SHORT).show();
        });

        binding.addFileToResourceAction.setOnClickListener(view -> {
            resultLauncher.launch("*/*");

        });
        binding.filesListRcv.setAdapter(adapter);

        binding.addResourceAction.setOnClickListener(view -> {
            viewModel.addResources(requireContext(), adapter.getResourceFileHashMap(), new UITask<Long>() {
                @Override
                public void onSuccess(Long result) {
                    Toast.makeText(requireContext(), "Resource Successfully Added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return binding.getRoot();
    }

    String getFileNameFromUri(Uri uri) {
        String displayName = "";
        try (Cursor cursor = requireContext().getContentResolver().query(uri, new String[] {OpenableColumns.DISPLAY_NAME}, null, null, null);) {
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index != -1) {
                    displayName = cursor.getString(index);
                }
            }
        } catch (Exception e) {
            Log.e("Cursor", e.getLocalizedMessage(), e);
        }
        return displayName;
    }
}