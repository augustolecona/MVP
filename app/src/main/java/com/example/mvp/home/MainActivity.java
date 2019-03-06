package com.example.mvp.home;
import  com.example.mvp.data.local.UsersDBProvider;
import  com.example.mvp.data.remote.randomapi.to.*;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mvp.R;

import com.example.mvp.data.remote.randomapi.RandomAPI;
import com.example.mvp.data.remote.randomapi.RandomAPIService;
import com.example.mvp.data.remote.randomapi.to.ApiResponse;
import com.example.mvp.data.remote.randomapi.to.Result;
import com.example.mvp.util.ErrorManager;
import com.example.mvp.util.NetworkError;
import com.example.mvp.util.NetworkManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements HomeContract.View {

    private HomeContract.Presenter homePresenter;
    private UsersAdapter usersAdapter;
    private  RecyclerView usersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homePresenter = new HomePresenter(this);
        initViews();
    }

    private void initViews() {
        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter();
        usersRecyclerView.setAdapter(usersAdapter);


    }

    @Override
    public void showUsers(List<Result> results) {
        usersAdapter.updateUserDataSet(results);

    }

    @Override
    public void showError(NetworkError error) {
        Toast.makeText(MainActivity.this, ErrorManager.handleError(error), Toast.LENGTH_SHORT).show();
    }

    public void doMagic(View view) {
        homePresenter.fetchUsers(100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.tearDown();
        homePresenter = null;
    }

    public void Save(View view) {

        if (usersAdapter.getItemCount()>0) {


            Result user = usersAdapter.getUsers().get(0);

            Name name =user.getName();
            String namee = name.getTitle()+" " +name.getFirst()+" " +" " +name.getLast();
            Location loc = user.getLocation();
            String location= loc.getStreet()+", "+loc.getCity()+", "+loc.getState()+ ", "+loc.getPostcode();
            String gender = user.getEmail();
            String email= user.getEmail();
            String phone = user.getPhone();


            ContentValues values = new ContentValues();
            values.put(UsersDBProvider.COL2, namee);
            values.put(UsersDBProvider.COL3, gender);
            values.put(UsersDBProvider.COL4, location);
            values.put(UsersDBProvider.COL5, email);
            values.put(UsersDBProvider.COL6, phone);

            Uri uri = getContentResolver().insert(UsersDBProvider.CONTENT_URL, values);
            Toast.makeText(getBaseContext(), "New Contact Added", Toast.LENGTH_SHORT).show();
        }

    }
}
