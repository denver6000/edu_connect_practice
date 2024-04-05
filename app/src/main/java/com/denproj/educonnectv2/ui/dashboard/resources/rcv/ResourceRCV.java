package com.denproj.educonnectv2.ui.dashboard.resources.rcv;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denproj.educonnectv2.R;
import com.denproj.educonnectv2.databinding.ResourceCardBinding;
import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Resource;
import com.denproj.educonnectv2.room.entity.User;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.QueryTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

public class ResourceRCV extends RecyclerView.Adapter<ResourceRCV.ViewHolder>{

    List<Resource> resourceList;
    UserDao userDao;
    public ResourceRCV(List<Resource> resourceList, UserDao userDao) {
        this.resourceList = resourceList;
        this.userDao = userDao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setResource(resourceList.get(position));
        holder.binding.imageView7.setOnClickListener(view -> {
            AsyncRunner.runAsync(new QueryTask<Void>() {
                @Override
                public Void onTask() throws Exception {
                    Resource resource = resourceList.get(holder.getAdapterPosition());
                    List<String> files = userDao.getAllFilesFromAResourceId(resource.resourceId);
                    Context context = holder.binding.getRoot().getContext();
                    ContextWrapper contextWrapper = new ContextWrapper(context);
                    File resourcePath = contextWrapper.getDir("resources", Context.MODE_PRIVATE);
                    File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File resourceFolder = new File(downloadDir, resource.resourceName);
                    resourceFolder.mkdir();
                    for (String file : files) {

                        File file_ = new File(resourceFolder, file);
                        File fileInResources = new File(resourcePath, file);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            InputStream inputStream = Files.newInputStream(fileInResources.toPath());
                            OutputStream outputStream = Files.newOutputStream(file_.toPath());

                            int bit;

                            byte[] buffer = new byte[4 * 2024];
                            while ((bit = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bit);
                            }

                        }


                    }
                    return null;
                }

                @Override
                public void onSuccess(Void result) {

                }

                @Override
                public void onFail(String message) {

                }

                @Override
                public void onUI(Void result) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ResourceCardBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ResourceCardBinding.bind(itemView);
        }
    }
}
