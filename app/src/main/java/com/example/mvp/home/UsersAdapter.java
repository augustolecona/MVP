package com.example.mvp.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mvp.R;
import com.example.mvp.data.remote.randomapi.to.Result;

import java.util.List;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public List<Result> getUsers() {
        return users;
    }

    private List<Result> users;

    public UsersAdapter() {

    }

    public UsersAdapter(List<Result> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Result result = users.get(position);
        viewHolder.firstName.setText(result.getName().getFirst());
        viewHolder.lastName.setText(result.getName().getLast());
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void updateUserDataSet(List<Result> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView firstName;
        TextView lastName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
        }

    }



}
