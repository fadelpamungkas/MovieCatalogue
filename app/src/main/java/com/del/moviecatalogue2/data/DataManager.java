package com.del.moviecatalogue2.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.del.moviecatalogue2.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataManager {
    public static final String DATABASE_NAME = "android_database";
    public static final String TAG = DataManager.class.getSimpleName();
    public static MovieDatabase database;
    static ArrayList<Movie> movieArrayList;

    public DataManager(Context context){
        database = Room.databaseBuilder(context, MovieDatabase.class, DATABASE_NAME).build();
        movieArrayList = new ArrayList<>();
    }

    public static ArrayList<Movie> getMovies(){
        database.movieDatabase().getFavMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        movieArrayList.clear();
                        movieArrayList.addAll(movies);
                        Log.d(TAG, "accept");
                    }
                });
        return movieArrayList;
    }

    public static List<Movie> findMovie(final String search){
        database.movieDatabase().findMovie(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        movieArrayList.clear();
                        movieArrayList.addAll(movies);
                        Log.d(TAG, "accept findMovie");
                    }
                });
        return movieArrayList;
    }

    public static void addMovies(final Context context, final Movie movie){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.movieDatabase().insert(movie);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "addMovies: onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "addMovies: onComplete");
                        Toast.makeText(context, R.string.added, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "addMovies: onError: " + e.getMessage());
                        Toast.makeText(context, R.string.already_on_database, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void deleteMovies(final Context context, final Movie movie ){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.movieDatabase().delete(movie);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "deleteMovies: onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "deleteMovies: onComplete");
                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "deleteMovies: onError: " + e.getMessage());
                    }
                });
    }
}
