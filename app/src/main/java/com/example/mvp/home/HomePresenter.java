package com.example.mvp.home;

import com.example.mvp.data.remote.randomapi.RandomAPI;
import com.example.mvp.data.remote.randomapi.RandomAPIService;
import com.example.mvp.data.remote.randomapi.to.Result;
import com.example.mvp.util.NetworkError;
import com.example.mvp.util.NetworkManager;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeView;
    private RandomAPIService randomAPIService;

    public HomePresenter(HomeContract.View homeView) {
        this.homeView = homeView;
        NetworkManager networkManager = new NetworkManager();
        randomAPIService = new RandomAPIService(networkManager.provideRandomAPI(RandomAPI.BASE_URL));
    }

    @Override
    public void fetchUsers(int count) {
        randomAPIService.fetchRandomUsers(100, new RandomAPIService.RandomUsersCallback() {
            @Override
            public void OnUsersLoaded(List<Result> results) {
                homeView.showUsers(results);
            }

            @Override
            public void OnAPIError(NetworkError error) {
                homeView.showError(error);
            }
        });
    }

    @Override
    public void tearDown() {
        homeView = null;
    }

}
