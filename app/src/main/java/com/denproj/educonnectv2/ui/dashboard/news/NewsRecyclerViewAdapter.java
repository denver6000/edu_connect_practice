package com.denproj.educonnectv2.ui.dashboard.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denproj.educonnectv2.databinding.NewsCardBinding;
import com.denproj.educonnectv2.room.entity.News;

import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    List<News> newsList;

    public NewsRecyclerViewAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(NewsCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.binding.newsTitle.setText(news.newsTitle);
        holder.binding.newsDescription.setText(news.newsDescription);
        Bitmap bitmap = BitmapFactory.decodeByteArray(news.imageByteArray, 0, news.imageByteArray.length);
        holder.binding.newsPoster.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public NewsCardBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NewsCardBinding.bind(itemView);
        }
    }
}
