package com.augylabs.volleyloader;

/**
 * Created by adarshpandey on 7/23/15.
 */
public interface Callback<Result> {

    void onFailure(Exception ex);

    void onSuccess(Result result);
}
