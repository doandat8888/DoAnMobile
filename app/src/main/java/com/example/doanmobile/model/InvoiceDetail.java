package com.example.doanmobile.model;

public class InvoiceDetail {
    private int invoiceId;
    private String productName;
    private int productQuantity;
    private int unitPrice;
    private int productTotal;
    private int invoiceTotal;

    public InvoiceDetail(int invoiceId, String productName, int productQuantity, int unitPrice, int productTotal, int invoiceTotal) {
        this.invoiceId = invoiceId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.unitPrice = unitPrice;
        this.productTotal = productTotal;
        this.invoiceTotal = invoiceTotal;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(int productTotal) {
        this.productTotal = productTotal;
    }

    public int getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(int invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }
}
