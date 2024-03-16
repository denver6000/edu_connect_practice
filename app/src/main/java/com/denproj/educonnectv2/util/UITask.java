package com.denproj.educonnectv2.util;

public interface UITask<T> {

    void onSuccess(T result);
    void onFail(String message);

}
