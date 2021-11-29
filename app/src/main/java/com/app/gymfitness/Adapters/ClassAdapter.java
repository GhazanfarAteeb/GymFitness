package com.app.gymfitness.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gymfitness.Activities.ActivityClassDetail;
import com.app.gymfitness.Models.Class;
import com.app.gymfitness.Models.ClassType;
import com.app.gymfitness.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyHolder> {
    Context context;
    List<Class> classList;
    public ClassAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ClassAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.row_class,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.MyHolder holder, int position) {
        Class myClass = classList.get(position);
        holder.tvInstructorName.setText("fetch instructor of this class");
        holder.tvClassTypeName.setText(myClass.getName());
        holder.cvClass.setOnClickListener(view -> {
            Intent intent = new Intent(context, ActivityClassDetail.class);
            intent.putExtra("class",myClass);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CardView cvClass;
        public TextView tvClassTypeName;
        public TextView tvInstructorName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvClassTypeName = itemView.findViewById(R.id.tv_class_type_name);
            tvInstructorName = itemView.findViewById(R.id.tv_instructor);
            cvClass = itemView.findViewById(R.id.cv_class_type);
        }
    }

    public void setData(List<Class> classList) {
        this.classList = classList;
        notifyDataSetChanged();
    }
}
