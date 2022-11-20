package com.inovex.main.json.response;

public class SecondaryProductReport {
    private String distributorId;
    private String productId;
    private String productCategory;
    private String startDate;
    private String endDate;

    public String getDistributorId() { return distributorId; }

    public void setDistributorId(String distributorId) { this.distributorId = distributorId; }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getStartDate() {return startDate;}

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
