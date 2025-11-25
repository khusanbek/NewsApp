package com.example.newsapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<Article> {
    public NewsAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), android.R.layout.simple_list_item_2, null);
        }

        Article article = getItem(position);
        TextView title = convertView.findViewById(android.R.id.text1);
        TextView description = convertView.findViewById(android.R.id.text2);

        title.setText(article.getTitle());
        description.setText(article.getDescription());

        return convertView;
    }
}

