package com.example.paylocityemployees;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final String TAG = "EmployeesAdapter";
    private List<Employee> employeeList;
    private MainActivity mainActivity;

    public EmployeeAdapter(List<Employee> employeeList, MainActivity mainActivity) {
        this.employeeList = employeeList;
        this.mainActivity = mainActivity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.employee_list_row, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Employee oneItem = employeeList.get(position);
        holder.name.setText(oneItem.getName());
        holder.spouse.setText(Boolean.toString(oneItem.isSpouse()));
        holder.children.setText(Integer.toString(oneItem.getChildren()));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }
}
