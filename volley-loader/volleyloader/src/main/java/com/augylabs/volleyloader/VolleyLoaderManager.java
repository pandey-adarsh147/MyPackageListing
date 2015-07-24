package com.augylabs.volleyloader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by adarshpandey on 7/23/15.
 */
public class VolleyLoaderManager {
    public static <D, R> void init(final LoaderManager manager, final int loaderId,
                                   final VolleyLoader<D, R> loader, final Callback<D> callback, final boolean keepLoader) {

        manager.initLoader(loaderId, Bundle.EMPTY, new LoaderManager.LoaderCallbacks<Response<D>>() {

            @Override
            public Loader<Response<D>> onCreateLoader(int id, Bundle args) {

                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Response<D>> loader, Response<D> data) {
                if (!keepLoader) {
                    manager.destroyLoader(loaderId);
                }

                if (data.hasError()) {

                    callback.onFailure(data.getException());

                } else {

                    callback.onSuccess(data.getResult());
                }
            }

            @Override
            public void onLoaderReset(Loader<Response<D>> loader) {

                //Nothing to do here
            }
        });
    }

    public static <D, R> void restart(final LoaderManager manager, final int loaderId,
                                   final VolleyLoader<D, R> loader, final Callback<D> callback) {

        manager.restartLoader(loaderId, Bundle.EMPTY, new LoaderManager.LoaderCallbacks<Response<D>>() {

            @Override
            public Loader<Response<D>> onCreateLoader(int id, Bundle args) {

                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Response<D>> loader, Response<D> data) {

                if (data.hasError()) {

                    callback.onFailure(data.getException());

                } else {

                    callback.onSuccess(data.getResult());
                }
            }

            @Override
            public void onLoaderReset(Loader<Response<D>> loader) {

                //Nothing to do here
            }
        });
    }

    public static <D, R> void reconnect(final LoaderManager manager, final int loaderId, final Callback<D> callback, final boolean keepLoader) {

        VolleyLoader<D, R> preLoader = (VolleyLoader) manager.getLoader(loaderId);
        if (preLoader != null && (!preLoader.isAbandoned() || !preLoader.isReset())) {
            init(manager, loaderId, preLoader, callback, keepLoader);
        }

    }


}
