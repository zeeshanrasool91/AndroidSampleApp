package com.example.myapplication.mvp.moviedetails.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RecyclerViewAdapter;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ActivityMovieDetailsBinding;
import com.example.myapplication.model.MoviesDetailResponse;
import com.example.myapplication.model.MoviesListResponse;
import com.example.myapplication.mvp.moviedetails.DetailsContract;
import com.example.myapplication.mvp.moviedetails.DetailsPresenter;
import com.example.myapplication.mvp.movieslist.view.MainActivity;
import com.example.myapplication.utils.AppUtils;

import java.util.List;

public class MovieDetails extends BaseActivity implements  DetailsContract.View{

    DetailsPresenter mainPresenter;
    ActivityMovieDetailsBinding activityMovieDetailsBinding;
    public static String TAG = MovieDetails.class.getSimpleName();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(MovieDetails.this, R.layout.activity_movie_details);
        mainPresenter = new DetailsPresenter(this);
        activityMovieDetailsBinding.setPresenter(mainPresenter);
        mainPresenter.initScreen();
    }
    @Override
    public void setupViews() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.details_activity_title));
        activityMovieDetailsBinding.setMovieID(getIntent().getIntExtra("movie_id",0));
        mainPresenter.getDetailsFromAPI(getIntent().getIntExtra("movie_id",0));
    }

    @Override
    public void showLoading() {
        activityMovieDetailsBinding.progressCircular.setVisibility(View.VISIBLE);
        activityMovieDetailsBinding.appCompatButton.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        activityMovieDetailsBinding.progressCircular.setVisibility(View.GONE);
        activityMovieDetailsBinding.appCompatButton.setVisibility(View.GONE);
    }


    @Override
    public void showData(MoviesDetailResponse dataResponse) {
        activityMovieDetailsBinding.setDetailsModel(dataResponse);
        activityMovieDetailsBinding.appCompatButton.setVisibility(View.GONE);
    }


    @Override
    public void showSuccess(String message) {
        Toast.makeText(MovieDetails.this, message, Toast.LENGTH_LONG).show();
        activityMovieDetailsBinding.appCompatButton.setVisibility(View.GONE);
    }

    @Override
    public void showFailure(String message) {
        Toast.makeText(MovieDetails.this, message, Toast.LENGTH_LONG).show();
        activityMovieDetailsBinding.appCompatButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInternetError(String showInternetError) {
        Toast.makeText(MovieDetails.this, showInternetError, Toast.LENGTH_LONG).show();
        activityMovieDetailsBinding.appCompatButton.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean getInternetConnectivityStatus() {
        return checkConnection();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
