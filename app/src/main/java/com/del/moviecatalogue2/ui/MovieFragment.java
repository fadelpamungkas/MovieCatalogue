package com.del.moviecatalogue2.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.del.moviecatalogue2.services.Asynchronous;
import com.del.moviecatalogue2.services.ItemClickSupport;
import com.del.moviecatalogue2.data.MovieAdapter;
import com.del.moviecatalogue2.R;
import com.del.moviecatalogue2.data.Movie;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static RecyclerView recyclerView;
    private static ProgressBar progressBar;
    private String KEY_TITLE = "MoviesList";
    private Asynchronous async;
    private static BroadcastReceiver downloadReceiver;
    private boolean isRegistered = false;
    private boolean progress = true;
    ArrayList<Movie> movies;
    MovieAdapter adapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            movies = savedInstanceState.getParcelableArrayList(KEY_TITLE);
            Log.d("MovieFragment", "savedInstanceState != null");
            progress = false;
        } else{
            async = new Asynchronous(getContext());
            movies = async.MovieDiscover();
            Log.d(MainActivity.class.getSimpleName(), "async.movieDiscover()");

            IntentFilter filter = new IntentFilter(MainActivity.STATUS);
            getContext().registerReceiver(MovieFragment.receive(), filter);
            isRegistered = true;
        }
        adapter = new MovieAdapter(getContext());
        adapter.setMovies(this.movies);
        Log.d(MovieFragment.class.getSimpleName(), "adapter.setMovie()");

    }

    @Override
    public void onStop(){
        super.onStop();
        if (isRegistered){
            getContext().unregisterReceiver(downloadReceiver);
            isRegistered = false;
        }
    }

    public static BroadcastReceiver receive(){
        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.d(MovieFragment.class.getSimpleName(), "progressbar: gone");
            }
        };
        return downloadReceiver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progressBar);

        if(!progress){
            progressBar.setVisibility(View.GONE);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("EXTRA", movies.get(position));
                intent.putExtra("FAV", true);
                startActivity(intent);
                Log.d(MovieFragment.class.getSimpleName(), "startActivity");
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelableArrayList(KEY_TITLE, movies);
        Log.d("MovieFragment", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

}
