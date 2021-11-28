package com.app.gymfitness.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.gymfitness.Models.User;
import com.app.gymfitness.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MyHolder> {
    Context context;
    List<User> userList;
    PassData listener;
    public MembersAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.row_list_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        User user = userList.get(position);

        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvGender.setText(user.getGender());
        holder.btnDelete.setOnClickListener(view -> listener.sendData(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmail;
        public TextView tvGender;
        public Button btnDelete;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvGender = itemView.findViewById(R.id.tv_gender);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public void setData(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }
    public interface PassData {
        void sendData(User user);
    }

    public void setPassDataListener(PassData listener) {
        this.listener = listener;
    }
}
