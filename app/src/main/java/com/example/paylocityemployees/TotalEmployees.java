package com.example.paylocityemployees;

import java.io.Serializable;

// holds the data for all employees
public class TotalEmployees implements Serializable {
    private int num_emp;
    private int num_spouse;
    private int num_children;
    private double total_price;
    private double discount_price;

    public TotalEmployees(int num_emp, int num_spouse, int num_children, double total_price, double discount_price) {
        this.num_emp = num_emp;
        this.num_spouse = num_spouse;
        this.num_children = num_children;
        this.total_price = total_price;
        this.discount_price = discount_price;
    }

    public int getNum_emp() {
        return num_emp;
    }

    public int getNum_spouse() {
        return num_spouse;
    }

    public int getNum_children() {
        return num_children;
    }

    public double getTotal_price() {
        return total_price;
    }

    public double getDiscount_price() {
        return discount_price;
    }
}
