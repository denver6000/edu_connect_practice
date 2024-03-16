package com.denproj.educonnectv2.util;

public interface QueryTask<T> {

    T onTask();
    void onSuccess(T result);
    void onFail(String message);

}
