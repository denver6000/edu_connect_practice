package com.denproj.educonnectv2.util;

public interface QueryTask<T> {

    T onTask() throws Exception;
    void onSuccess(T result);
    void onFail(String message);

    void onUI(T result);

}
