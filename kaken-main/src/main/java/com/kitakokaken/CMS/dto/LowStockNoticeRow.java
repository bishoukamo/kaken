package com.kitakokaken.CMS.dto;

public interface LowStockNoticeRow {

    String getChemicalName();

    Long getMaterialId();

    Double getRemainingWeight();

    Double getPurchaseWeight();
}
