package com.example.newsplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsplus.API.ApiCLient;
import com.example.newsplus.API.ApiInterface;
import com.example.newsplus.ModelClass.Article;
import com.example.newsplus.ModelClass.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "48b69dd644b54337a5171b42d66dcf5c";
    private RecyclerView newsRecyclerView;
    private List<Article> articles = new ArrayList<>();
    private TextView topHeadlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeFields();


        //Set Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        newsRecyclerView.setLayoutManager(layoutManager);

        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsRecyclerView.setNestedScrollingEnabled(false);



        ApiInterface apiInterface = ApiCLient.getApiClient().create(ApiInterface.class);
        String country = Utils.getCountry();

        Call<News> call;
        call = apiInterface.getNews("IN", API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles() != null)
                {
                    if(!articles.isEmpty())
                    {
                        articles.clear();
                    }

                    articles = response.body().getArticles();
                    NewsAdapter newsAdapter = new NewsAdapter(articles, MainActivity.this);
                    newsRecyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }






    private void InitializeFields() {
        newsRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        topHeadlines = (TextView)findViewById(R.id.top_headlines);

    }
}
