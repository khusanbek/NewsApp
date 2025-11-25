package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView newsListView;
    private List<Article> articleList;
    private NewsAdapter adapter;
    private final String API_KEY = "ad006d60e4a045158e3c0cd5505a1772";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListView = findViewById(R.id.newsListView);
        articleList = new ArrayList<>();
        adapter = new NewsAdapter(this, articleList);
        newsListView.setAdapter(adapter);

        newsListView.setOnItemClickListener((parent, view, position, id) -> {
            Article article = articleList.get(position);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("url", article.getDescription()); // description holds the URL
            startActivity(intent);
        });

        fetchNews();
    }

    private void fetchNews() {

        String url = "https://hn.algolia.com/api/v1/search?query=android";
        // String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {

                        JSONArray hits = response.getJSONArray("hits");
                        for (int i = 0; i < hits.length(); i++) {

                            JSONObject obj = hits.getJSONObject(i);
                            String title = obj.optString("title", "No Title");
                            String description = obj.optString("url", "No link");
                            articleList.add(new Article(title, description));
                        }
                        adapter.notifyDataSetChanged();
                        /*
                        JSONArray articles = response.getJSONArray("articles");
                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject obj = articles.getJSONObject(i);
                            String title = obj.getString("title");
                            String description = obj.optString("description", "No description");
                            articleList.add(new Article(title, description));
                        }
                        adapter.notifyDataSetChanged();
                         */
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Failed to fetch news", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
