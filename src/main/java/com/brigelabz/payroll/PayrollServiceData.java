package com.brigelabz.payroll;

import java.util.Date;

public class PayrollServiceData {

    public int id;
    public String name;
    public String gender;
    public double salary;
    public Date start;

    public PayrollServiceData(int id, String name, String gender, double salary, Date start) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.start = start;
    }
}