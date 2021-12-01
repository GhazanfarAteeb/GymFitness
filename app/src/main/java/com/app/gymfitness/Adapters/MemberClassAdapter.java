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
import com.app.gymfitness.Activities.ClassMemberEnrollmentDescription;
import com.app.gymfitness.Models.Class;
import com.app.gymfitness.R;

import java.util.List;

public class MemberClassAdapter extends RecyclerView.Adapter<MemberClassAdapter.ViewHolder> {
    List<Class> classList;
    Context context;
    public MemberClassAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_class,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Class myClass = classList.get(position);
        holder.className.setText(myClass.getClassName());
        holder.instructorName.setText(myClass.getInstructorName());
        holder.cvClassType.setOnClickListener(view -> {
            Intent intent = new Intent(context, ClassMemberEnrollmentDescription.class);
            intent.putExtra("class",myClass);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvClassType;
        TextView className;
        TextView instructorName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.tv_class_type_name);
            instructorName = itemView.findViewById(R.id.tv_instructor_name);
            cvClassType = itemView.findViewById(R.id.cv_class_type);
        }
    }
    public void setData(List<Class> classList) {
        this.classList = classList;
        notifyDataSetChanged();
    }
}