package com.del.moviecatalogue2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.widget.ANImageView;
import com.del.moviecatalogue2.R;
import com.del.moviecatalogue2.data.DataManager;
import com.del.moviecatalogue2.data.Movie;

public class DetailActivity extends AppCompatActivity {
    Movie movie;
    boolean STATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.detail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvTitle = findViewById(R.id.title_detail);
        TextView tvRating = findViewById(R.id.rating_detail);
        TextView tvRelease = findViewById(R.id.release_detail);
        TextView tvAdult = findViewById(R.id.runtime_detail);
        TextView textAdult = findViewById(R.id.textAdult);
        TextView tvPopularity = findViewById(R.id.genres_detail);
        TextView tvDescription = findViewById(R.id.desc_detail);
        ANImageView imgPhoto = findViewById(R.id.image_detail);

        movie = getIntent().getParcelableExtra("EXTRA");
        STATUS = getIntent().getBooleanExtra("FAV", true);

        tvTitle.setText(movie.getTitle());
        tvRating.setText(String.valueOf(movie.getRating()));
        tvRelease.setText(movie.getRelease());
        tvPopularity.setText(String.valueOf(movie.getPopularity()));
        tvDescription.setText(movie.getDescription());
        imgPhoto.setImageUrl("https://image.tmdb.org/t/p/w185" + movie.getPhoto());
        if (movie.getLanguage() == null){
            textAdult.setText("Adult");
            tvAdult.setText(Boolean.toString(movie.isAdult()));
        } else{
            textAdult.setText("Language");
            tvAdult.setText(movie.getLanguage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if (STATUS){
            getMenuInflater().inflate(R.menu.menu_fav, menu);
        } else{
            getMenuInflater().inflate(R.menu.menu_del, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.fav_db:
                DataManager.addMovies(this, movie);
                Log.d("DetailActivity", "addMovies()");
                break;

            case R.id.del_db:
                DataManager.deleteMovies(this, movie);
                Log.d("DetailActivity", "deleteMovies()");
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        Log.d("DetailActivity", "onBackPressed()");
    }
}
