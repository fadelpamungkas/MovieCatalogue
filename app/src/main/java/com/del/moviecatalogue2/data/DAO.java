package com.del.moviecatalogue2.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DAO {
    @Query("SELECT * FROM movie_database")
    Flowable<List<Movie>> getFavMovie();

    @Query("SELECT * FROM movie_database WHERE title LIKE :search")
    Flowable<List<Movie>> findMovie(String search);

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

}
