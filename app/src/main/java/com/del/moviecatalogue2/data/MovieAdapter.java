package com.del.moviecatalogue2.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.del.moviecatalogue2.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context){
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies){
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int i) {
        holder.tvTitle.setText(movies.get(i).getTitle());
        holder.tvDesc.setText(movies.get(i).getDescription());
        holder.image.setImageUrl("https://image.tmdb.org/t/p/w185" + movies.get(i).getPhoto());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ANImageView image;

        public MovieViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.title_main);
            tvDesc = view.findViewById(R.id.desc_main);
            image = view.findViewById(R.id.image_main);
        }
    }
}
