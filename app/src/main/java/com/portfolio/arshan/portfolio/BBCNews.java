package com.portfolio.arshan.portfolio;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BBCNews extends ActionBarActivity implements NewsAdapter.NewsClickListener{
    private List<NewsGetSet> newsGetSetList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    // URL to get contacts JSON
    private static String url = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=7c8d432c22ba4501bd29d6e85ae68d56";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbc_news);
        recyclerView = (RecyclerView) findViewById(R.id.news_recycle);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetContacts().execute();
            }
        });
        //Make call to Async
        new GetContacts().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recycle view starts
        //recyclerView = (RecyclerView) findViewById(R.id.news_recycle);
        newsAdapter = new NewsAdapter(newsGetSetList);
        newsAdapter.setClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BBCNews.this);
            pDialog.setMessage("Loading News...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray news = jsonObj.getJSONArray("articles");

                    // looping through All News
                    for (int i = 0; i < news.length(); i++) {
                        JSONObject c = news.getJSONObject(i);

                        String title = c.getString("title");
                        String description = c.getString("description");
                        String imageUrl = c.getString("urlToImage");
                        String newsUrl = c.getString("url");
                        String publishedAt = c.getString("publishedAt");

                        URL url = new URL(imageUrl);
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                        NewsGetSet newsGetSet = new NewsGetSet(title, description, bmp, newsUrl, publishedAt);
                        newsGetSetList.add(newsGetSet);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException.. " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            newsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void itemClicked(View view, int position) {
        //Toast.makeText(BBCNews.this,position+":",Toast.LENGTH_SHORT).show();
    }

}
