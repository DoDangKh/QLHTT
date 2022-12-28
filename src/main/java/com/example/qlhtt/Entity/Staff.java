package com.example.qlhtt.Entity;

public class Staff {
    private int staff_id;
    private int salary;
    private int status;

    public Staff(int staff_id, int salary, int status) {
        this.staff_id = staff_id;
        this.salary = salary;
        this.status = status;
    }

    public Staff() {
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
