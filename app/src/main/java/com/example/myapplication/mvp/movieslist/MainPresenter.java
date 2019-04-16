package com.example.myapplication.mvp.movieslist;

import android.util.Log;

import com.example.myapplication.base.BasePresenter;
import com.example.myapplication.base.BaseRepositoryInput;
import com.example.myapplication.model.MoviesListResponse;
import com.example.myapplication.networking.BaseResponse;
import com.example.myapplication.networking.ErrorResponse;
import com.example.myapplication.networking.FailureException;

import java.io.IOException;
import java.util.List;


public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Actions {


    private MainInteractor mainInteractor;

    public MainPresenter(MainContract.View view) {
        super(view);
        mainInteractor = new MainInteractor();
    }

    public void initScreen() {
        _view.setupViews();
        _view.setupRecyclerViewAdapter();
    }

    @Override
    public void getAccessCodeFromServer() {
        if (_view.getInternetConnectivityStatus()) {
            _view.showLoading();
            mainInteractor.callWebApi(1, new BaseRepositoryInput.OnRepositoryResponse<BaseResponse<List<MoviesListResponse>>>() {
                @Override
                public void onSuccess(BaseResponse<List<MoviesListResponse>> response) {
                    _view.hideLoading();
                    _view.showSuccess(response.getPage().toString());
                    _view.showData(response.getResults());
                }

                @Override
                public void onFailure(Throwable error) {
                    _view.hideLoading();
                    if (error instanceof FailureException) {
                            //ErrorResponse errorResponse= ((FailureException) error).getErrorBodyAs(ErrorResponse.class);
                            ErrorResponse errorResponse= ((FailureException) error).getError();
                            _view.showFailure(errorResponse.getStatusMessage());

                    } else {
                        _view.showFailure("Unknown Error Message");
                    }
                }
            });

        } else {
            _view.hideLoading();
            _view.showInternetError("Internet Connection Failed!");
        }

    }
}
