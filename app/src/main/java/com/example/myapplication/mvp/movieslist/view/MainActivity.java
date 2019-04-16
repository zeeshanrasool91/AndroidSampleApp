package com.example.myapplication.mvp.movieslist.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RecyclerViewAdapter;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.model.MoviesListResponse;
import com.example.myapplication.mvp.moviedetails.view.MovieDetails;
import com.example.myapplication.mvp.movieslist.MainContract;
import com.example.myapplication.mvp.movieslist.MainPresenter;
import com.example.myapplication.utils.AppUtils;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View, RecyclerViewAdapter.ItemClickListener {


    MainPresenter mainPresenter;
    ActivityMainBinding activityMainBinding;
    public static String TAG = MainActivity.class.getSimpleName();
    private RecyclerViewAdapter recyclerViewAdapter;
    boolean tabletSize;
    boolean isVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (AppUtils.getScreenOrientation(this) == 1) {
            isVertical = true;
        } else if (AppUtils.getScreenOrientation(this) == 2) {
            isVertical = false;
        }
        mainPresenter = new MainPresenter(this);
        activityMainBinding.setPresenter(mainPresenter);
        mainPresenter.initScreen();
    }




    @Override
    public void setupViews() {
        setSupportActionBar(activityMainBinding.toolbarInclude.toolbarTop);
        activityMainBinding.toolbarInclude.setSetAppTitle(getResources().getString(R.string.main_activity_title));
        mainPresenter.getAccessCodeFromServer();
    }

    @Override
    public void showLoading() {
        activityMainBinding.progressCircular.setVisibility(View.VISIBLE);
        activityMainBinding.btnRetry.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        activityMainBinding.progressCircular.setVisibility(View.GONE);
        activityMainBinding.btnRetry.setVisibility(View.GONE);
    }

    @Override
    public void setupRecyclerViewAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this);
        recyclerViewAdapter.setItemClickListener(this);
        activityMainBinding.recyclerView.setAdapter(recyclerViewAdapter);
        int spanCount = 3;
        int orientation = GridLayoutManager.VERTICAL;
        if (tabletSize) {
            spanCount = 2;
            orientation = GridLayoutManager.HORIZONTAL;
            recyclerViewAdapter.setDeviceWidth(AppUtils.getWindowWidth(this) / 3);
        } else if (!isVertical) {
            spanCount = 1;
            orientation = GridLayoutManager.HORIZONTAL;
            recyclerViewAdapter.setDeviceWidth(AppUtils.getWindowWidth(this) / 3);
        }
        activityMainBinding.recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, spanCount, orientation, false));
        activityMainBinding.recyclerView.setHasFixedSize(true);
    }



    @Override
    public void showData(List<MoviesListResponse> dataResponse) {
        activityMainBinding.btnRetry.setVisibility(View.GONE);
        recyclerViewAdapter.setFilteredItems(dataResponse);
    }


    @Override
    public void showSuccess(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        activityMainBinding.btnRetry.setVisibility(View.GONE);
    }

    @Override
    public void showFailure(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        activityMainBinding.btnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInternetError(String showInternetError) {
        Toast.makeText(MainActivity.this, showInternetError, Toast.LENGTH_LONG).show();
        activityMainBinding.btnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean getInternetConnectivityStatus() {
        return checkConnection();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "Search Action clicked", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(MoviesListResponse moviesListResponse, int position) {
        Log.d(TAG, "onItemClick: " + position);
        Intent intent=new Intent(MainActivity.this, MovieDetails.class);
        intent.putExtra("movie_id",moviesListResponse.getId());
        startActivity(intent);
    }
}
