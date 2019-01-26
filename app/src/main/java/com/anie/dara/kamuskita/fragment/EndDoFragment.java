package com.anie.dara.kamuskita.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.anie.dara.kamuskita.DetailKeyActivity;
import com.anie.dara.kamuskita.KamusAdapter;
import com.anie.dara.kamuskita.MainActivity;
import com.anie.dara.kamuskita.R;
import com.anie.dara.kamuskita.db.DoengHelper;
import com.anie.dara.kamuskita.db.KamusHelper;
import com.anie.dara.kamuskita.kamus;

import java.util.ArrayList;


public class EndDoFragment extends android.support.v4.app.Fragment  implements KamusAdapter.OnKlikKeyword{

    RecyclerView rvKamus;
    KamusAdapter kamusAdapter;
    KamusHelper kamusHelper;
    Activity activity;
    private SearchView searchView;
    SearchView.OnQueryTextListener queryTextListener;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_end_do, container, false);


        kamusHelper = new KamusHelper(activity);
        kamusHelper.open();
        ArrayList<kamus> kamusList = kamusHelper.getAllData();
        kamusHelper.close();

        kamusAdapter = new KamusAdapter();
        kamusAdapter.addItem(kamusList);
        kamusAdapter.setClickHandler(this);

        rvKamus = view.findViewById(R.id.rvKamus);
        rvKamus.setAdapter(kamusAdapter);
        rvKamus.setLayoutManager(new LinearLayoutManager(activity));
        ((AppCompatActivity)activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void OnKlikKeyword(kamus kamusItem) {
        Intent detail = new Intent(activity, DetailKeyActivity.class);
        detail.putExtra("data_kamus", kamusItem);
        startActivity(detail);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.kamus, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        if (item != null) {
            searchView = (SearchView) item.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

         queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    kamusHelper.open();
                    ArrayList<kamus> kamusList = kamusHelper.cariKeyword(newText);
                    kamusAdapter.addItem(new ArrayList<kamus>(kamusList));
                    kamusHelper.close();

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    kamusHelper.open();
                    ArrayList<kamus> kamusList = kamusHelper.cariKeyword(query);
                    kamusHelper.close();
                    kamusAdapter.addItem(new ArrayList<kamus>(kamusList));
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.app_bar_search:

               return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }




}
