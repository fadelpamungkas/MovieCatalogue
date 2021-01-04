package com.del.moviecatalogue2.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.del.moviecatalogue2.R;
import com.del.moviecatalogue2.data.DataManager;
import com.del.moviecatalogue2.data.Movie;
import com.del.moviecatalogue2.data.MovieAdapter;
import com.del.moviecatalogue2.services.ItemClickSupport;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Movie> movies;
    private static MovieAdapter adapter;
    private String KEY_TITLE = "DBMoviesList";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        movies = new ArrayList<>();
        adapter = new MovieAdapter(getContext());

        if (savedInstanceState != null){
            movies = savedInstanceState.getParcelableArrayList(KEY_TITLE);
            Log.d("MovieFavFragment", "savedInstanceState != null");
        } else{
            Log.d("MovieFavFragment", "filter()");
            movies.clear();
            for (Movie movie : MainActivity.getAllMovies()){
                if (movie.isMovie()){
                    movies.add(movie);
                }
            }
            Log.d("MovieFavFragment", "savedInstanceState == null");
        }
    }

    public MovieFavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);
        recyclerView = view.findViewById(R.id.rv_movie_fav);

        adapter.setMovies(movies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("EXTRA", movies.get(position));
                intent.putExtra("FAV", false);
                startActivity(intent);
                Log.d(MovieFragment.class.getSimpleName(), "startActivity");
            }
        });
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelableArrayList(KEY_TITLE, movies);
        super.onSaveInstanceState(outState);
    }


}
