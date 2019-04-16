package com.example.myapplication.mvp.moviedetails;


import com.example.myapplication.base.BaseRepositoryInput;
import com.example.myapplication.model.MoviesDetailResponse;
import com.example.myapplication.model.MoviesListResponse;
import com.example.myapplication.networking.AppConstants;
import com.example.myapplication.networking.BaseResponse;
import com.example.myapplication.networking.CustomRetrofitCallback;
import com.example.myapplication.networking.CustomRetrofitCallbackInterface;
import com.example.myapplication.networking.NetworkController;

import java.util.List;


public class DetailsInteractor implements BaseRepositoryInput<Integer, MoviesDetailResponse> {

    @Override
    public void callWebApi(Integer input,final OnRepositoryResponse<MoviesDetailResponse> repositoryResponse) {

      NetworkController.getInstance().GenericNetworkCall(NetworkController.getInstance().getApiMethods().getMovieDetails(input,AppConstants.HTTP.API_KEY,"en-US"), new CustomRetrofitCallback<>(new CustomRetrofitCallbackInterface<MoviesDetailResponse>() {

            @Override
            public void onSuccess(MoviesDetailResponse response) {
                repositoryResponse.onSuccess(response);
            }

            @Override
            public void onError(Throwable throwable) {
                repositoryResponse.onFailure(throwable);
            }
        }));
    }
}
