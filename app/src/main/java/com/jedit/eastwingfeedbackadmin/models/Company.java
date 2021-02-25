package com.jedit.eastwingfeedbackadmin.models;

import java.util.List;

public class Company {

    private String uuid;
    private String name;
    private List<Department> departments;

    public Company() {
    }

    public Company(String uuid, String name, List<Department> departments) {
        this.uuid = uuid;
        this.name = name;
        this.departments = departments;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
