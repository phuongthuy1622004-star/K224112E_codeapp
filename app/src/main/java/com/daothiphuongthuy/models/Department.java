package com.daothiphuongthuy.models;

import androidx.annotation.NonNull;
import java.util.ArrayList;

public class Department {
    private String departmentId;
    private String departmentName;
    private ArrayList<Employee> listOfEmployee;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.listOfEmployee = new ArrayList<>();
    }

    public Department() {
        this.listOfEmployee = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return this.departmentName;
    }

    public void addEmployee(Employee emp) {
        this.listOfEmployee.add(emp);
    }

    public void addListEmployee(ArrayList<Employee> listEmp) {
        this.listOfEmployee.addAll(listEmp);
    }

    public ArrayList<Employee> getListOfEmployee() {
        return listOfEmployee;
    }
}