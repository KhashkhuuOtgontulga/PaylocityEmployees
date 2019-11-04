package com.example.paylocityemployees;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnLongClickListener, View.OnClickListener {

    private List<Employee> employeeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private Employee employee;
    private int MAKE_DATA_CODE = 1;
    private int EDIT_DATA_CODE = 2;
    private static final String TAG = "MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        employeeAdapter = new EmployeeAdapter(employeeList, this);

        recyclerView.setAdapter(employeeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTitle("Paylocity Employees " + "("+ employeeAdapter.getItemCount() + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onLongClick(View view) {
        final int pos = recyclerView.getChildLayoutPosition(view);
        Employee employee = employeeList.get(pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Employee " + "'" + employee.getName() + "'?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                employeeList.remove(pos);
                employeeAdapter.notifyDataSetChanged();
                setTitle("Paylocity Employees " + "("+ employeeAdapter.getItemCount() + ")");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    @Override
    public void onClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        employee = employeeList.get(pos);

        Intent addIntent = new Intent(this, AddActivity.class);
        addIntent.putExtra("Employee", employee);
        // save the position of the employee to edit it correctly
        // which is to delete it and then add it back to the list
        addIntent.putExtra("Position", pos);

        startActivityForResult(addIntent, EDIT_DATA_CODE);
    }

    public void startPreview(View view) {
        // algorithm
        int num_emp = 0;
        int num_spouse = 0;
        int num_children = 0;
        double total_price = 0;
        double discount_price = 0;
        for(Employee e : employeeList) {
            num_emp += 1;
            if(e.isSpouse()) {
                num_spouse += 1;
            }
            num_children += e.getChildren();
            // if the name starts with an A, either lower or uppercase,
            // apply the 10% discount
            if(e.getName().startsWith("A") || e.getName().startsWith("a")) {
                if(e.isSpouse()){
                    total_price += (1000 + 500 + e.getChildren()*500);
                    discount_price += (1000 + 500 + e.getChildren()*500)*.9;
                }
                else {
                    total_price += (1000 + e.getChildren()*500);
                    discount_price += (1000 + e.getChildren()*500)*.9;
                }
            }
            else {
                if(e.isSpouse()){
                    total_price += 1000 + 500 + e.getChildren()*500;
                    // must add this instead of
                    // discount_price += total_price;
                    // because it will do like 4000 + 2000
                    // instead of 2000 + 2000
                    // I don't want to add what I just updated
                    // but add the number from the current employee
                    discount_price += 1000 + 500 + e.getChildren()*500;
                }
                else {
                    total_price += 1000 + e.getChildren()*500;
                    discount_price += 1000 + e.getChildren()*500;
                }
            }
        }

        TotalEmployees totalEmployees = new TotalEmployees(num_emp, num_spouse, num_children, total_price, discount_price);

        Intent preview = new Intent(this, PreviewActivity.class);
        // get the first employee and pass it
        // so you don't get a null object reference
        // can't use employee because that only sets the reference
        // to an employee when you click on an employee
        // if you don't click on it and just preview
        // it will throw a null object reference
        // or the totalEmployees object
        if(num_emp == 1) {
            preview.putExtra("Employee", employeeList.get(0));
        }
        else {
            preview.putExtra("EmployeeList", totalEmployees);
        }
        startActivity(preview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutField:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.addField:
                Intent addIntent = new Intent(this, AddActivity.class);
                startActivityForResult(addIntent, MAKE_DATA_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if(requestCode == MAKE_DATA_CODE) {
            // Make sure the request was successful
            if(resultCode == Activity.RESULT_OK) {
                // Retrieve the data by the intent name
                Employee employee = (Employee) data.getSerializableExtra(AddActivity.extraName);

                // Add the employee to the recyclerview and update the title
                employeeList.add(employee);
                employeeAdapter.notifyDataSetChanged();
                Log.d(TAG, "onActivityResult: " + "DATA IS ADDED");
                setTitle("Paylocity Employees " + "("+ employeeAdapter.getItemCount() + ")");
            }
        }
        // Check which request we're responding to
        else if(requestCode == EDIT_DATA_CODE) {
            // Make sure the request was successful
            if(resultCode == Activity.RESULT_OK) {
                // Retrieve the data by the intent name
                Employee employee = (Employee) data.getSerializableExtra(AddActivity.extraName);
                int pos = data.getIntExtra("Position",0);

                // Add the employee to the recyclerview and update the title
                employeeList.remove(pos);
                employeeList.add(employee);
                employeeAdapter.notifyDataSetChanged();
                Log.d(TAG, "onActivityResult: " + "DATA IS ADDED");
                setTitle("Paylocity Employees " + "("+ employeeAdapter.getItemCount() + ")");
            }
        }
    }
}
