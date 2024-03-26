package com.denproj.educonnectv2.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncRunner {

    public static ExecutorService executorService = Executors.newFixedThreadPool(4);
    public static Handler handler = new Handler(Looper.getMainLooper());


    public static <T> void runAsync(QueryTask<T> queryTask) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    T result = queryTask.onTask();
                    queryTask.onSuccess(result);

                    handler.post(() -> queryTask.onUI(result));

                } catch (Exception e) {
                    Log.e("AsyncRunner", e.getMessage(), e);
                    handler.post(() -> queryTask.onFail(e.getLocalizedMessage()));
                }
            }
        });
    }

}
