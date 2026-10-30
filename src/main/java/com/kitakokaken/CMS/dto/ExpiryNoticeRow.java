package com.kitakokaken.CMS.dto;

import java.time.LocalDate;

public interface ExpiryNoticeRow {

    String getChemicalName();

    Long getMaterialId();

    LocalDate getExpiryDate();
}