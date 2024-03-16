package com.denproj.educonnectv2.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncRunner {

    public static ExecutorService executorService = Executors.newFixedThreadPool(4);
    public static Handler handler = new Handler(Looper.getMainLooper());


    public static <T> void runAsync() {

    }

}
