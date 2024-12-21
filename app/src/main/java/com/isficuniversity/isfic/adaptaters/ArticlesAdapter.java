package com.isficuniversity.isfic.adaptaters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.isficuniversity.isfic.R;
import com.isficuniversity.isfic.modules.Article;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> articles;

    public ArticlesAdapter(List<Article> articles) {
        // Limite la taille de la liste à 12 articles
        if (articles.size() > 12) {
            this.articles = articles.subList(0, 12);
        } else {
            this.articles = articles;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());

        // Charger l'image avec Glide
        Glide.with(holder.image.getContext())
                .load(article.getImage())
                .placeholder(R.drawable.baseline_photo_size_select_actual_24)
                .error(R.drawable.baseline_report_gmailerrorred_24)
                .into(holder.image);

        // Gérer le clic sur un article pour ouvrir l'URL dans Chrome
        holder.itemView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            holder.itemView.getContext().startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            image = itemView.findViewById(R.id.articleImage);
        }
    }
}
