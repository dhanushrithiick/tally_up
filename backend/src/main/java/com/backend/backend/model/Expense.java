package com.backend.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int payerId;
    private int payeeId;
    private double amount;
    private String description;

    public Expense() {}

    public Expense(int payerId, int payeeId, double amount, String description) {
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.amount = amount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public int getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(int payeeId) {
        this.payeeId = payeeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
