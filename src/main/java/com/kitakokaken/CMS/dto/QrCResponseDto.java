package com.kitakokaken.CMS.dto;

import java.time.LocalDate;

public class QrCResponseDto {

    private Long materialId;
    private Long chemicalId;
    private String chemicalName;

    private LocalDate prd;
    private double capacity;
    private double purchaseWeight;
    private LocalDate expiryDate;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getChemicalId() {
        return chemicalId;
    }

    public void setChemicalId(Long chemicalId) {
        this.chemicalId = chemicalId;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public LocalDate getPrd() {
        return prd;
    }

    public void setPrd(LocalDate prd) {
        this.prd = prd;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getPurchaseWeight() {
        return purchaseWeight;
    }

    public void setPurchaseWeight(double purchaseWeight) {
        this.purchaseWeight = purchaseWeight;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}