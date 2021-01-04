package com.del.moviecatalogue2.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.del.moviecatalogue2.BuildConfig;
import com.del.moviecatalogue2.data.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Asynchronous {
    private static final String TAG = Asynchronous.class.getSimpleName();
    public static final String API = BuildConfig.API_KEY;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Movie> movies2 = new ArrayList<>();
    private Context context;

    public Asynchronous(Context context){
        this.context = context;
    }

    public ArrayList<Movie> MovieDiscover(){
        final String URL = "https://api.themoviedb.org/3/discover/movie?api_key="+ Asynchronous.API +"&language=en-US";
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "MovieDiscover onResponse");
                        try{
                            JSONArray list = response.getJSONArray("results");
                            Log.d(TAG, "try");

                            for (int i = 0; i < list.length(); i++){
                                Movie movie = new Movie();
                                movie.setId(list.getJSONObject(i).getInt("id"));
                                movie.setTitle(list.getJSONObject(i).getString("title"));
                                movie.setRelease(list.getJSONObject(i).getString("release_date"));
                                movie.setPhoto(list.getJSONObject(i).getString("poster_path"));
                                movie.setDescription(list.getJSONObject(i).getString("overview"));
                                movie.setPopularity(list.getJSONObject(i).getDouble("popularity"));
                                movie.setRating(list.getJSONObject(i).getDouble("vote_average"));
                                movie.setAdult(list.getJSONObject(i).getBoolean("adult"));
                                movie.setIsMovie(true);
                                movies.add(movie);
                                Log.d(TAG, "movies.add(movie)");
                            }
                            context.startService(new Intent(context, DownloadService.class));

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(context, "Error Loading Data", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "catch exception");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "AndroidNetworking Error");
                    }
                });
        return movies;
    }

    public ArrayList<Movie> TVShowDiscover(){
        final String URL = "https://api.themoviedb.org/3/discover/tv?api_key="+ Asynchronous.API +"&language=en-US";
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "TVShowDiscover onResponse");
                        try{
                            JSONArray list = response.getJSONArray("results");
                            Log.d(TAG, "try");

                            for (int i = 0; i < list.length(); i++){
                                Movie movie = new Movie();
                                movie.setId(list.getJSONObject(i).getInt("id"));
                                movie.setTitle(list.getJSONObject(i).getString("name"));
                                movie.setRelease(list.getJSONObject(i).getString("first_air_date"));
                                movie.setPhoto(list.getJSONObject(i).getString("poster_path"));
                                movie.setDescription(list.getJSONObject(i).getString("overview"));
                                movie.setPopularity(list.getJSONObject(i).getDouble("popularity"));
                                movie.setRating(list.getJSONObject(i).getDouble("vote_average"));
                                movie.setLanguage(list.getJSONObject(i).getString("original_language"));
                                movie.setIsMovie(false);
                                movies2.add(movie);
                                Log.d(TAG, "movies2.add(movie)");
                            }
                            context.startService(new Intent(context, DownloadService.class));

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(context, "Error Loading Data", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "catch exception");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "AndroidNetworking Error");
                    }
                });
        return movies2;
    }
}
