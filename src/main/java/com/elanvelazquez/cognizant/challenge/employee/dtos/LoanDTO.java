package com.elanvelazquez.cognizant.challenge.employee.dtos;



public class LoanDTO {
    private int id;
    private double amount;
    private boolean paid = Boolean.FALSE;
    private int employee;

    public LoanDTO() {
    }

    public LoanDTO(int id, double amount, boolean paid, int employee) {
        this.id = id;
        this.amount = amount;
        this.paid = paid;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }
}
