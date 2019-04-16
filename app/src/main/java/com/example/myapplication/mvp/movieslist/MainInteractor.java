package com.example.myapplication.mvp.movieslist;


import com.example.myapplication.base.BaseRepositoryInput;
import com.example.myapplication.model.MoviesListResponse;
import com.example.myapplication.networking.AppConstants;
import com.example.myapplication.networking.BaseResponse;
import com.example.myapplication.networking.CustomRetrofitCallback;
import com.example.myapplication.networking.CustomRetrofitCallbackInterface;
import com.example.myapplication.networking.NetworkController;

import java.util.List;


public class MainInteractor implements BaseRepositoryInput<Integer, BaseResponse<List<MoviesListResponse>>> {

    @Override
    public void callWebApi(Integer input,final BaseRepositoryInput.OnRepositoryResponse<BaseResponse<List<MoviesListResponse>>> repositoryResponse) {

      NetworkController.getInstance().GenericNetworkCall(NetworkController.getInstance().getApiMethods().getMoviesList(AppConstants.HTTP.API_KEY), new CustomRetrofitCallback<>(new CustomRetrofitCallbackInterface<BaseResponse<List<MoviesListResponse>>>() {

            @Override
            public void onSuccess(BaseResponse<List<MoviesListResponse>> response) {
                repositoryResponse.onSuccess(response);
            }

            @Override
            public void onError(Throwable throwable) {
                repositoryResponse.onFailure(throwable);
            }
        }));
    }
}
