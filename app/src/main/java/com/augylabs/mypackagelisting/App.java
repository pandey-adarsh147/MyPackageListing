package com.augylabs.mypackagelisting;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import dagger.ObjectGraph;

public class App extends Application {

    private ObjectGraph graph;

    private RequestQueue requestQueue;

    private static App instance;

    @Override public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(MainModule.class);
        requestQueue = Volley.newRequestQueue(this);

        instance = this;
    }

    public void inject(Object object) {
        graph.inject(object);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static App getInstance() {
        return instance;
    }
}