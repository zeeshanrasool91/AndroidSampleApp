package com.example.myapplication.mvp.moviedetails;


import android.util.Log;

import com.example.myapplication.model.MoviesDetailResponse;
import com.example.myapplication.model.MoviesListResponse;

import java.util.List;

/**
 * Created by Administrator on 3/20/2018.
 */
public interface DetailsContract{

    /*
   View - this defines the methods that the pure views like Activity or Fragments etc will implement.
    */
    interface View {
        void setupViews();
        void showLoading();
        void hideLoading();
        void showData(MoviesDetailResponse dataResponse);
        void showSuccess(String message);
        void showFailure(String message);
        void showInternetError(String showInternetError);
        boolean getInternetConnectivityStatus();
    }

    /*
    Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
    this is where the app logic is defined.
     */
    interface Actions {
        void initScreen();
        void getDetailsFromAPI(int movieID);
    }

    /*
    this defines the methods that pure model or persistence class like database or server data will implement.
     */
    interface Repository {
        interface OnServiceResponse {
            void onSuccess();
            void onFailure();
        }
        void callWebApi(OnServiceResponse onServiceResponse);
    }

}
