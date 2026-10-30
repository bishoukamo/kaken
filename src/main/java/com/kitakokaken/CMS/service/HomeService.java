package com.kitakokaken.CMS.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kitakokaken.CMS.dto.ExpiryNoticeRow;
import com.kitakokaken.CMS.dto.LowStockNoticeRow;
import com.kitakokaken.CMS.repository.CapaitiranRepository;

@Service
public class HomeService {

    private final CapaitiranRepository capaitiranRepository;

    public HomeService(CapaitiranRepository capaitiranRepository) {
        this.capaitiranRepository = capaitiranRepository;
    }

    public List<String> getNotices() {

        List<String> notices = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (ExpiryNoticeRow row
                : capaitiranRepository.findExpiryNotices()) {

            if (row.getExpiryDate().isBefore(today)) {
                notices.add(
                    row.getChemicalName()
                    + "（容器ID: " + row.getMaterialId()
                    + "）は使用期限切れです。"
                );
            } else {
                notices.add(
                    row.getChemicalName()
                    + "（容器ID: " + row.getMaterialId()
                    + "）の使用期限は "
                    + row.getExpiryDate() + " です。"
                );
            }
        }

        for (LowStockNoticeRow row
                : capaitiranRepository.findLowStockNotices()) {

            notices.add(
                row.getChemicalName()
                + "（容器ID: " + row.getMaterialId()
                + "）の残量が少なくなっています。現在 "
                + row.getRemainingWeight() + " g"
            );
        }

        return notices;
    }
}