package com.aman.AirBnb.AirBnb.Strategy;

import com.aman.AirBnb.AirBnb.Entities.InventoryEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        java.time.LocalDate date = inventory.getDate();
        boolean isTodayHoliday = isHoliday(date);
        if (isTodayHoliday) {
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }

    private boolean isHoliday(java.time.LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        // New Year's Day (Jan 1), Independence Day (Jul 4), Halloween (Oct 31), Christmas (Dec 25), NYE (Dec 31)
        return (month == 12 && (day == 25 || day == 31)) ||
               (month == 1 && day == 1) ||
               (month == 7 && day == 4) ||
               (month == 10 && day == 31);
    }
}
