package com.backend.backend.model;

import java.util.List;

public class SplitRequest {
    private int payerId;
    private List<Integer> payeeIds;
    private double totalAmount;
    private String description;

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public List<Integer> getPayeeIds() {
        return payeeIds;
    }

    public void setPayeeIds(List<Integer> payeeIds) {
        this.payeeIds = payeeIds;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
