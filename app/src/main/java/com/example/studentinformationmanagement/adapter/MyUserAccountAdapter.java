package com.example.studentinformationmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.EditUserActivity;
import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.holder.MyUserAccountViewHolder;
import com.example.studentinformationmanagement.model.User;
import com.example.studentinformationmanagement.util.Const;
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
        String email = userList.get(position).getEmail();
        holder.txtEmail.setText(email);
        holder.txtUserName.setText(userList.get(position).getUserName());
        DataUtil.setAvatar(userList.get(position).getAvatar(),holder.imageView,R.drawable.default_avatar);
        holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra(Const.FIELD.EMAIL,email);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}
