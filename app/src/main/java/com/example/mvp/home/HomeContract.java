package com.example.mvp.home;

import com.example.mvp.base.BasePresenter;
import com.example.mvp.data.remote.randomapi.to.Result;
import com.example.mvp.util.NetworkError;

import java.util.List;

public interface HomeContract {

    interface View {
        void showUsers(List<Result> results);
        void showError(NetworkError error);
    }

    interface Presenter extends BasePresenter {

        void fetchUsers(int count);

    }
}
