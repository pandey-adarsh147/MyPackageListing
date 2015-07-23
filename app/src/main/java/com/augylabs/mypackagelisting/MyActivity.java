package com.augylabs.mypackagelisting;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.augylabs.volleyloader.Callback;
import com.augylabs.volleyloader.VolleyLoader;
import com.augylabs.volleyloader.VolleyLoaderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class MyActivity extends AppCompatActivity implements Callback<List<Issue>> {

    @Inject
    GitHub gitHubService;

    SwipeRefreshLayout swipeView;

    @Override
    public void onFailure(Exception ex) {

        setProgressBarIndeterminateVisibility(false);

        Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        if (swipeView != null) {
            swipeView.setRefreshing(false);
        }
    }

    @Override
    public void onSuccess(List<Issue> result) {

        Log.d("IssuesLoader", "onSuccess");

        displayResults(result);

        if (swipeView != null) {
            swipeView.setRefreshing(false);
        }
    }

    private void displayResults(List<Issue> issues) {

        List<String> strings = toStringList(issues);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);

        ListView viewById = (ListView) findViewById(R.id.listView);
        viewById.setAdapter(adapter);
    }

    private static List<String> toStringList(List<Issue> issues) {

        ArrayList<String> strings = new ArrayList<String>(issues.size());

        for (Issue issue : issues) {

            strings.add(issue.getTitle());
        }

        return strings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((App) getApplication()).inject(this);

        setContentView(R.layout.activity_my);
        Button button = (Button) findViewById(R.id.load);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IssuesLoader loader = new IssuesLoader(MyActivity.this, gitHubService);
                VolleyLoaderManager.init(getSupportLoaderManager(), 0, loader, MyActivity.this);
            }
        });
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                IssuesLoader loader = new IssuesLoader(MyActivity.this, gitHubService);

                VolleyLoaderManager.init(getSupportLoaderManager(), 0, loader, MyActivity.this);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                    }
                }, 3000);
            }
        });


        VolleyLoaderManager.reconnect(getSupportLoaderManager(), 0, this);
    }

    static class IssuesLoader extends VolleyLoader<List<Issue>, GitHub> {

        public IssuesLoader(Context context, GitHub service) {

            super(context, service);
        }

        @Override
        public List<Issue> call(GitHub service) throws ExecutionException, InterruptedException {

            Log.d("IssuesLoader", "call");

            return service.listRetrofitIssues();
        }
    }
}
