package com.augylabs.mypackagelisting;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GitHub {

//    @GET("/repos/square/retrofit/issues")
    public List<Issue> listRetrofitIssues() throws ExecutionException, InterruptedException {
        String url = "https://api.github.com/repos/square/retrofit/issues";
        RequestQueue requestQueue = App.getInstance().getRequestQueue();
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future);
        requestQueue.add(stringRequest);
        String s = future.get();
        try {
            List<Issue> issues = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Issue issue = new Issue();
                issue.setTitle(jsonArray.getJSONObject(i).getString("title"));
                issues.add(issue);

            }
            return issues;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;

    }
}
