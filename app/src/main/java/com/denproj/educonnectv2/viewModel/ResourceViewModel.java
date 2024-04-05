package com.denproj.educonnectv2.viewModel;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.denproj.educonnectv2.room.dao.UserDao;
import com.denproj.educonnectv2.room.entity.Resource;
import com.denproj.educonnectv2.room.entity.ResourceFile;
import com.denproj.educonnectv2.ui.dashboard.resources.ResourcesFragment;
import com.denproj.educonnectv2.util.AsyncRunner;
import com.denproj.educonnectv2.util.FilesUtil;
import com.denproj.educonnectv2.util.QueryTask;
import com.denproj.educonnectv2.util.UITask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ResourceViewModel extends ViewModel {


    public ObservableField<String> resourceName = new ObservableField<>("");

    public UserDao userDao;

    @Inject
    public ResourceViewModel(UserDao userDao) {
        this.userDao = userDao;
    }



    public void addResources(Context context, HashMap<String, Uri> fileNameAndUriMap, UITask<Long> integerUITask) {
        String resourceName_ = resourceName.get();
        if (!resourceName_.isEmpty()) {
            AsyncRunner.runAsync(new QueryTask<Long>() {
                @Override
                public Long onTask() throws Exception {
                    long resourceId = userDao.insertResource(new Resource(resourceName_));
                    fileNameAndUriMap.forEach((s, uri) -> {
                        insertFileToInternalStorage(s, uri, context);
                        userDao.insertResourceFile(new ResourceFile(s, resourceId));
                    });
                    return resourceId;
                }

                @Override
                public void onSuccess(Long result) {
                    Log.d("Int", result.toString() + " ");
                }

                @Override
                public void onFail(String message) {
                    integerUITask.onFail(message);
                }

                @Override
                public void onUI(Long result) {
                    integerUITask.onSuccess(result);
                }
            });
        } else {
            integerUITask.onFail("Please add a name to the resource.");
        }
    }

   void insertFileToInternalStorage(String fileName, Uri uri, Context context) {
        InputStream inputStream = null;
        if (uri != null) {
            try {
                ContextWrapper contextWrapper = new ContextWrapper(context);
                inputStream = contextWrapper.getContentResolver().openInputStream(uri);
                File actualFile = new File(contextWrapper.getDir(ResourcesFragment.RESOURCE_FOLDER, Context.MODE_PRIVATE), fileName);
                OutputStream fos = new FileOutputStream(actualFile);
                assert inputStream != null;
                int bit;
                byte[] buffer = new byte[4 * 2024];

                while ((bit = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bit);
                }
                inputStream.close();
                fos.close();
            } catch ( FileNotFoundException e){
                Log.e("FileThings", "File Error", e);
            } catch (IOException e) {
                Log.e("FileThings", "File Error", e);
            }
        }
    }

    public void getAllResource(UITask<List<Resource>> uiTask) {
        AsyncRunner.runAsync(new QueryTask<List<Resource>>() {
            @Override
            public List<Resource> onTask() throws Exception {
                return userDao.getAllResource();
            }

            @Override
            public void onSuccess(List<Resource> result) {

            }

            @Override
            public void onFail(String message) {
                uiTask.onFail(message);
            }

            @Override
            public void onUI(List<Resource> result) {
                uiTask.onSuccess(result);
            }
        });
    }

}
