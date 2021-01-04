package com.del.moviecatalogue2.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.del.moviecatalogue2.R;
import com.del.moviecatalogue2.data.DataManager;
import com.del.moviecatalogue2.data.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String STATUS = "DOWNLOAD_STATUS";
    private static ArrayList<Movie> getAllMovies;
    private Fragment content;
    private String title;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.menu_movie:
                    content = new MovieFragment();
                    getSupportActionBar().setTitle(R.string.movie);
                    break;

                case R.id.menu_tvshow:
                    content = new TVShowFragment();
                    getSupportActionBar().setTitle(R.string.tv_show);
                    break;

                case R.id.menu_favourite:
                    content = new FavouriteFragment();
                    getSupportActionBar().setTitle(R.string.favourite);
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, content, content.getClass().getSimpleName())
                    .commit();
            title = String.valueOf(getSupportActionBar().getTitle());
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = new MovieFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.menu_movie);
        } else{
            content = getSupportFragmentManager()
                    .getFragment(savedInstanceState, "CONTENT");
            title = savedInstanceState.getString("TITLE");

            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, content).commit();
            getSupportActionBar().setTitle(title);
        }

        AndroidNetworking.initialize(getApplicationContext());
        DataManager data = new DataManager(this);
        getAllMovies = DataManager.getMovies();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("TITLE", title);
        getSupportFragmentManager().putFragment(outState, "CONTENT", content);

        super.onSaveInstanceState(outState);
    }

    public static ArrayList<Movie> getAllMovies(){
        return getAllMovies;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.lang_menu, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null){
            SearchView searchView = (SearchView)(menu.findItem(R.id.searchview)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.find_movies));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    DataManager.findMovie(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Toast.makeText(MainActivity.this, "change: " + s, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.searchview:
                break;
            case R.id.action_change_settings:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
