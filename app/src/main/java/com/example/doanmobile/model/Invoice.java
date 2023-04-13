package com.example.doanmobile.model;

public class Invoice {
    private int invoiceId;
    private String accountName;
    private String accountUsername;
    private String accountPhoneNumber;
    private String recipientName;
    private String shipToAddress;
    private String paymentMethod;
    private double invoiceTotal;

    public Invoice(int invoiceId, String accountName, String accountUsername, String accountPhoneNumber, String recipientName, String shipToAddress, String paymentMethod, double invoiceTotal) {
        this.invoiceId = invoiceId;
        this.accountName = accountName;
        this.accountUsername = accountUsername;
        this.accountPhoneNumber = accountPhoneNumber;
        this.recipientName = recipientName;
        this.shipToAddress = shipToAddress;
        this.paymentMethod = paymentMethod;
        this.invoiceTotal = invoiceTotal;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountPhoneNumber() {
        return accountPhoneNumber;
    }

    public void setAccountPhoneNumber(String accountPhoneNumber) {
        this.accountPhoneNumber = accountPhoneNumber;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }
}
