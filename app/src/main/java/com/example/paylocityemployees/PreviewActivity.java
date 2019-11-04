package com.example.paylocityemployees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PreviewActivity extends AppCompatActivity {

    private Intent preview;
    private Employee employee;
    private TotalEmployees totalEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        preview = getIntent();
        // if it is one employee
        if (preview.hasExtra("Employee")) {
            employee = (Employee) preview.getSerializableExtra("Employee");
            countEmployees(1);
        }
        // it is more than 1 employee
        else if (preview.hasExtra("EmployeeList")){
            totalEmployees = (TotalEmployees) preview.getSerializableExtra("EmployeeList");{};
            countEmployees(totalEmployees.getNum_emp());
        }
    }

    public void countEmployees(int num_emp) {
        // create the view
        PieChartView pieChartView = findViewById(R.id.chart);

        // create the data
        List<SliceValue> pieData = new ArrayList<>();

        // add the data to the pieChart if 1 employee
        if (num_emp == 1) {
            pieData.add(new SliceValue(1000, Color.BLUE)
                    .setLabel("Employee: $1000")
            );
            // if they have a spouse, add 500
            if(employee.isSpouse()) {
                pieData.add(new SliceValue(500, Color.GRAY)
                        .setLabel("Spouse: $500")
                );
            }
            else {
                pieData.add(new SliceValue(0, Color.GRAY)
                        .setLabel("Spouse: $0")
                );
            }
            pieData.add(new SliceValue(employee.getChildren()*500, Color.RED)
                    .setLabel("Children: $" +
                    employee.getChildren()*500)
            );
        }
        // add the data to the pieChart if more than 1 employee
        else {
            pieData.add(new SliceValue(totalEmployees.getNum_emp()*1000, Color.BLUE)
                    .setLabel("Employee: $"
                    + totalEmployees.getNum_emp()*1000)
            );
            pieData.add(new SliceValue(totalEmployees.getNum_spouse()*500, Color.GRAY)
                    .setLabel("Spouse: $"
                    + totalEmployees.getNum_spouse()*500)
            );
            pieData.add(new SliceValue(totalEmployees.getNum_children()*500, Color.RED)
                    .setLabel("Children: $" +
                    totalEmployees.getNum_children()*500)
            );
        }

        // wrap the data
        PieChartData pieChartData = new PieChartData(pieData);

        // display the total
        // either one employee
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        if (num_emp == 1) {
            // display the discount
            if(employee.getName().startsWith("A") || employee.getName().startsWith("a")) {
                // add 500
                if(employee.isSpouse()) {
                    pieChartData.setHasCenterCircle(true)
                            .setCenterText1("Total: $"
                                    + (1000 + 500 + employee.getChildren() * 500)
                                    + ("\n Total w/ Discount: $" + (1000 + 500 + employee.getChildren() * 500) * .9)
                            ).setCenterText1FontSize(20);
                }
                // add nothing
                else {
                    pieChartData.setHasCenterCircle(true)
                            .setCenterText1("Total: $"
                                    + (1000 + employee.getChildren() * 500)
                                    + ("\n Total w/ Discount: $" + (1000 + employee.getChildren() * 500) * .9)
                            ).setCenterText1FontSize(20);
                }
            }
            // no discount is applied
            else {
                // add 500
                if(employee.isSpouse()) {
                    pieChartData.setHasCenterCircle(true)
                            .setCenterText1("Total: $"
                                    + (1000 + 500 + employee.getChildren() * 500)
                                    + ("\n Total w/ Discount: $" + (1000 + 500 + employee.getChildren() * 500))
                            ).setCenterText1FontSize(20);
                }
                // add nothing
                else {
                    pieChartData.setHasCenterCircle(true)
                            .setCenterText1("Total: $"
                                    + (1000 + employee.getChildren() * 500)
                                    + ("\n Total w/ Discount: $" + (1000 + 500 + employee.getChildren() * 500))
                            ).setCenterText1FontSize(20);
                }
            }
        }
        // display the total of more than 1 employee
        else {
            pieChartData.setHasCenterCircle(true)
                    .setCenterText1("Total: $"
                            + (totalEmployees.getTotal_price()
                            + "\n Total w/ Discount: $" + (totalEmployees.getDiscount_price()))
            ).setCenterText1FontSize(20);
        }

        // connect the data to the view
        pieChartView.setPieChartData(pieChartData);
    }
}
