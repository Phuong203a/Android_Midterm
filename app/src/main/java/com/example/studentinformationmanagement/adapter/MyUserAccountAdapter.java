package com.example.studentinformationmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.holder.MyUserAccountViewHolder;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.DataUtil;

import java.util.List;

public class MyUserAccountAdapter extends RecyclerView.Adapter<MyUserAccountViewHolder> {
    Context context;
    List<User> userList;

    public MyUserAccountAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyUserAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyUserAccountViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserAccountViewHolder holder, int position) {
        holder.txtEmail.setText(userList.get(position).getEmail());
        holder.txtUserName.setText(userList.get(position).getUserName());
        DataUtil.setAvatar(userList.get(position).getAvatar(),holder.imageView,R.drawable.default_avatar);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
