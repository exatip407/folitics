package com.ohmuk.folitics.util;

import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final Timestamp getSqlTimeStamp() {
        Calendar cal = Calendar.getInstance();
        java.sql.Date date = new java.sql.Date(cal.getTime().getTime());
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }

    public static final Timestamp getSqlTimeStamp(long minusTime) {
        Calendar cal = Calendar.getInstance();
        java.sql.Date date = new java.sql.Date(cal.getTime().getTime() - minusTime);
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }
}
