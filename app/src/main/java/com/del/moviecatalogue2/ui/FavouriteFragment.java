package com.del.moviecatalogue2.ui;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.del.moviecatalogue2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    TabLayout tabLayout;
    private Fragment content;
    private int selectedTab = 0;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            content = getChildFragmentManager()
                    .getFragment(savedInstanceState, "CHILD_CONTENT");
            getChildFragmentManager().beginTransaction().replace(R.id.tabframelayout, content).commit();
            Log.d("FavouriteFragment", "savedInstanceState != null");
        } else{
            content = new MovieFavFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.tabframelayout, content).commit();
            Log.d("FavouriteFragment", "savedInstanceState == null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        content = new MovieFavFragment();
                        break;
                    case 1:
                        content = new TVShowFavFragment();
                        break;
                }
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tabframelayout, content)
                        .commit();
                selectedTab = tabLayout.getSelectedTabPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt("TAB", selectedTab);
        getChildFragmentManager().putFragment(outState, "CHILD_CONTENT", content);
        super.onSaveInstanceState(outState);
    }

}
