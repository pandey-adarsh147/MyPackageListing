package com.augylabs.volleyloader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.concurrent.ExecutionException;

/**
 * Created by adarshpandey on 7/23/15.
 */
public abstract class VolleyLoader <Result, Service> extends AsyncTaskLoader<Response<Result>> {

    private final Service mService;

    private Response<Result> mCachedResponse;

    public VolleyLoader(Context context, Service service) {

        super(context);

        mService = service;
    }

    @Override
    public Response<Result> loadInBackground() {

        try {

            final Result data = call(mService);
            mCachedResponse = Response.ok(data);

        } catch (Exception ex) {

            mCachedResponse = Response.error(ex);
        }

        return mCachedResponse;
    }

    @Override
    protected void onStartLoading() {

        super.onStartLoading();

        if (mCachedResponse != null) {

            deliverResult(mCachedResponse);
        }

        if (takeContentChanged() || mCachedResponse == null) {

            forceLoad();
        }
    }

    @Override
    protected void onReset() {

        super.onReset();

        mCachedResponse = null;
    }

    public abstract Result call(Service service) throws ExecutionException, InterruptedException;
}
