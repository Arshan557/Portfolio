package com.portfolio.arshan.portfolio;

/**
 * Created by Arshan on 10-Oct-2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<NewsGetSet> newsGetSetListsList;
    private NewsClickListener newsClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView newTitle, newsDesc, publishedAt;
        public ImageView bmp;
        public String newsUri;

        public MyViewHolder(View view) {
            super(view);
            newTitle = (TextView) view.findViewById(R.id.news_title);
            newsDesc = (TextView) view.findViewById(R.id.news_desc);
            bmp = (ImageView) view.findViewById(R.id.news_image);
            publishedAt = (TextView) view.findViewById(R.id.publishedAt);

            newTitle.setOnClickListener(this);
            newsDesc.setOnClickListener(this);
            bmp.setOnClickListener(this);
            publishedAt.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (newsClickListener != null) {
                newsClickListener.itemClicked(v, getPosition());
            }
        }
    }


    public NewsAdapter(List<NewsGetSet> newsGetSetListsList) {
        this.newsGetSetListsList = newsGetSetListsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row_news, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsGetSet newsGetSet = newsGetSetListsList.get(position);
        holder.newTitle.setText(newsGetSet.getTitle());
        holder.newsDesc.setText(newsGetSet.getDesc());
        holder.bmp.setImageBitmap(newsGetSet.getBmp());
        holder.publishedAt.setText(newsGetSet.getPublishedAt());
    }

    public void setClickListener(NewsClickListener newsClickListener) {
        this.newsClickListener = newsClickListener;
    }

    @Override
    public int getItemCount() {
        return newsGetSetListsList.size();
    }

    public interface NewsClickListener {
        public void itemClicked(View view, int position);
    }
}