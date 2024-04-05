package com.denproj.educonnectv2.ui.dashboard.resources.rcv;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.FileCardBinding;
import com.denproj.educonnectv2.room.entity.ResourceFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SelectFilesRCV extends RecyclerView.Adapter<SelectFilesRCV.ViewHolder>{

    HashMap<String, Uri> resourceFileHashMap;
    List<String> fileNames;

    public SelectFilesRCV(HashMap<String, Uri> resourceFileHashMap) {
        this.resourceFileHashMap = resourceFileHashMap;
        this.fileNames = new ArrayList<>(resourceFileHashMap.keySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String fileName = fileNames.get(position);
        holder.binding.fileName.setText(fileName);
        holder.binding.removeFileFromList.setOnClickListener(view -> removeItem(fileName));
    }

    @Override
    public int getItemCount() {
        return resourceFileHashMap.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public FileCardBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FileCardBinding.bind(itemView);
        }
    }

    public void addItem(String fileNameKey, Uri uri) {
        resourceFileHashMap.put(fileNameKey, uri);
        fileNames.add(fileNameKey);
        notifyItemInserted(fileNames.size() - 1);
    }

    public void removeItem(String fileNameKey) {
        int index = fileNames.indexOf(fileNameKey);
        fileNames.remove(index);
        resourceFileHashMap.remove(fileNameKey);
        notifyItemRemoved(index);
    }

    public HashMap<String, Uri> getResourceFileHashMap () {
        return resourceFileHashMap;
    }

}
