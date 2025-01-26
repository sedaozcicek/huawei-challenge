package com.huawei.productionplanning.utils;

import com.huawei.productionplanning.enums.Months;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.*;
import java.time.temporal.TemporalAdjusters;

@Component
@Slf4j
public class Utils {
    public static LocalDateTime startDate(String inputDate) {
        LocalDate localDateTime = patternDate(inputDate);
        return localDateTime.atStartOfDay();
    }

    public static LocalDateTime endDate(String inputDate) {
        LocalDate localDateTime = patternDate(inputDate);
        return localDateTime.atTime(23, 59, 59);
    }

    public static LocalDate patternDate(String inputDate) {
        //String formattedDate = checkInputDate(inputDate);
        String formattedDate = "dd/MM/yyyy";
        return LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(formattedDate));
    }

    public void checkInputDate(String inputDate) {
        String targetFormat = "dd/MM/yyyy";
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(targetFormat);

        List<DateTimeFormatter> possibleFormats = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("MM-dd-yyyy")
        );

        possibleFormats.stream()
                .map(formatter -> parseDate(inputDate, formatter))
                .filter(Objects::nonNull)
                .findFirst()
                .map(date -> date.format(targetFormatter))
                .orElseThrow(() -> new IllegalArgumentException("Tarih formatı geçersiz!"));
    }


    private LocalDate parseDate(String inputDate, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(inputDate, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static Months mapToMonths(Month month) {
        return Months.values()[month.ordinal()];
    }

    public static Months monthEnumMap(Integer month) {
        Map<Integer, Months> map = new HashMap<>();
        map.put(1, Months.JANUARY);
        map.put(2, Months.FEBRUARY);
        map.put(3, Months.MARCH);
        map.put(4, Months.APRIL);
        map.put(5, Months.MAY);
        map.put(6, Months.JUNE);
        map.put(7, Months.JULY);
        map.put(8, Months.AUGUST);
        map.put(9, Months.SEPTEMBER);
        map.put(10, Months.OCTOBER);
        map.put(11, Months.NOVEMBER);
        map.put(12, Months.DECEMBER);

        return map.getOrDefault(month, null);
    }

    public static List<Integer> getWeeksOfMonth(Months month) {
        int monthIndex = month.ordinal();
        int startWeek = monthIndex * 4 + 1;
        int endWeek = startWeek + 3;

        List<Integer> weeks = new ArrayList<>();
        for (int i = startWeek; i <= endWeek; i++) {
            weeks.add(i);
        }

        return weeks;
    }

    public static Map<Months, Integer> getRemainingDaysInMonthForWeek(int weekNumber, int year) {
        Map<Months, Integer> resultMap = new HashMap<>();

        LocalDate startDate = LocalDate.of(year, 1, 1)
                .with(WeekFields.ISO.weekOfYear(), weekNumber);

        LocalDate endDate = startDate.plusDays(6);

        Months startMonth = mapToMonths(startDate.getMonth());
        Months endMonth = mapToMonths(endDate.getMonth());

        if (startMonth.equals(endMonth)) {
            int remainingDays = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
            resultMap.put(startMonth, remainingDays);
        } else {
            LocalDate endOfStartMonth = startDate.with(TemporalAdjusters.lastDayOfMonth());
            int remainingDaysStartMonth = endOfStartMonth.getDayOfMonth() - startDate.getDayOfMonth() + 1;
            resultMap.put(startMonth, remainingDaysStartMonth);

        }
        return resultMap;
    }



    }