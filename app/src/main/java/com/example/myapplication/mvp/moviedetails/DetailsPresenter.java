package com.example.myapplication.mvp.moviedetails;

import com.example.myapplication.base.BasePresenter;
import com.example.myapplication.base.BaseRepositoryInput;
import com.example.myapplication.model.MoviesDetailResponse;
import com.example.myapplication.model.MoviesListResponse;
import com.example.myapplication.networking.BaseResponse;
import com.example.myapplication.networking.ErrorResponse;
import com.example.myapplication.networking.FailureException;

import java.util.List;


public class DetailsPresenter extends BasePresenter<DetailsContract.View> implements DetailsContract.Actions {


    private DetailsInteractor detailsInteractor;

    public DetailsPresenter(DetailsContract.View view) {
        super(view);
        detailsInteractor = new DetailsInteractor();
    }

    public void initScreen() {
        _view.setupViews();
    }

    @Override
    public void getDetailsFromAPI(int movieID) {
        if (_view.getInternetConnectivityStatus()) {
            _view.showLoading();
            detailsInteractor.callWebApi(movieID, new BaseRepositoryInput.OnRepositoryResponse<MoviesDetailResponse>() {
                @Override
                public void onSuccess(MoviesDetailResponse response) {
                    _view.hideLoading();
                    _view.showSuccess("Success!");
                    _view.showData(response);
                }

                @Override
                public void onFailure(Throwable error) {
                    _view.hideLoading();
                    if (error instanceof FailureException) {
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
