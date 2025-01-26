package com.huawei.productionplanning.validation;

import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.exception.DateException;

public class DateValidation {

    public static void compareDateIsBefore(Months projectMonth,Months endDateMonth){
        if (projectMonth.ordinal() > endDateMonth.ordinal()) {
            throw new DateException("Project month cannot be after end date month.");
        }
    }

    public static void checkStartAndEndDate(Months startDateMonth,Months endDateMonth){
        if (startDateMonth.ordinal() > endDateMonth.ordinal()) {
            throw new DateException("Start date cannot be after end date.");
        }
    }
}
