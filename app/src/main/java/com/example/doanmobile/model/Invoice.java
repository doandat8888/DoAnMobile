package com.example.doanmobile.model;

public class Invoice {
    private int invoiceId;
    private String accountName;
    private String accountUsername;
    private String accountPhoneNumber;
    private int invoiceTotal;

    public Invoice(int invoiceId, String accountName, String accountUsername, String accountPhoneNumber, int invoiceTotal) {
        this.invoiceId = invoiceId;
        this.accountName = accountName;
        this.accountUsername = accountUsername;
        this.accountPhoneNumber = accountPhoneNumber;
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

    public int getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(int invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }
}
