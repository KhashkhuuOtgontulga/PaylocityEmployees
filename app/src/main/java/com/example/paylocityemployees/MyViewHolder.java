package com.example.paylocityemployees;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView spouse;
    public TextView children;

    public MyViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.textView_name);
        spouse = itemView.findViewById(R.id.textView_spouse);
        children = itemView.findViewById(R.id.textView_children);
    }
}
