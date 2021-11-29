package com.app.gymfitness.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gymfitness.Activities.ClassTypeDescriptionActivity;
import com.app.gymfitness.Models.ClassType;
import com.app.gymfitness.R;

import java.util.List;

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.MyHolder> {
    Context context;
    List<ClassType> classTypeList;
    public TypesAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public TypesAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.row_class_type,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TypesAdapter.MyHolder holder, int position) {
        ClassType classType = classTypeList.get(position);
        holder.tvClassTypeDescription.setText(classType.getDescription());
        holder.tvClassTypeName.setText(classType.getClassName());
        holder.cvClassType.setOnClickListener(view -> {
            Intent intent = new Intent(context, ClassTypeDescriptionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ClassTypeID", classType.getClassTypeID());
            bundle.putString("ClassTypeName", classType.getClassName());
            bundle.putString("ClassTypeDescription", classType.getDescription());
            intent.putExtra("ClassType",bundle);

           /* intent.putExtra("classtype",classType);*/



            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return classTypeList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CardView cvClassType;
        public TextView tvClassTypeName;
        public TextView tvClassTypeDescription;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvClassTypeName = itemView.findViewById(R.id.tv_class_type_name);
            tvClassTypeDescription = itemView.findViewById(R.id.tv_description);
            cvClassType = itemView.findViewById(R.id.cv_class_type);
        }
    }

    public void setData(List<ClassType> classTypeList) {
        this.classTypeList = classTypeList;
        notifyDataSetChanged();
    }
}
