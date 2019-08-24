package com.example.newsplus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.newsplus.ModelClass.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> articles;
    private Context context;
    private View myView;

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.ViewHolder holder, int position) {
        Article article = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.loadNews.setVisibility(View.GONE);

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.loadNews.setVisibility(View.GONE);

                return false;
            }
        })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.newsImage);
        holder.newsTitle.setText(article.getTitle());
        holder.newsDescription.setText(article.getDescription());
        holder.newsPublishedAt.setText(article.getPublishedAt());
        holder.newsAuthor.setText(article.getAuthor());
        holder.newsSource.setText(article.getSource().getName());
        holder.newsTime.setText(" \u2022 " + Utils.DateToTimeFormat(article.getPublishedAt()));
        holder.newsPublishedAt.setText(Utils.DateFormat(article.getPublishedAt()));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView newsTitle, newsDescription, newsSource, newsAuthor, newsTime, newsPublishedAt;
        private ImageView newsImage;
        private ProgressBar loadNews;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTime = (TextView)myView.findViewById(R.id.news_time);
            newsTitle = (TextView)myView.findViewById(R.id.news_title);
            newsSource = (TextView)myView.findViewById(R.id.news_source);
            newsDescription = (TextView)myView.findViewById(R.id.news_description);
            newsAuthor = (TextView)myView.findViewById(R.id.author);
            newsPublishedAt = (TextView)myView.findViewById(R.id.published_at);
            newsImage = (ImageView)myView.findViewById(R.id.news_image);
            loadNews = (ProgressBar)myView.findViewById(R.id.progress_load_photo);

        }
    }
}
