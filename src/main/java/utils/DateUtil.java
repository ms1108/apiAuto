package utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    /**
     * @param offset:1表示明天，-1表示昨天
     * @return
     */
    public LocalDateTime modifyDayDate(int offset) {
        return currentDate().plusDays(offset);
    }

    //调整时间
    public LocalDateTime modifyMonthDate(int offset) {
        return currentDate().plus(offset, ChronoUnit.MONTHS);
    }

    public LocalDateTime currentDate() {
        return LocalDateTime.now();
    }

    public String formatDate(LocalDateTime date) {
        return formatDate(date,"yyyy-MM-dd HH:mm:ss");
    }
    public String formatDate(LocalDateTime date, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(date);
    }

    //当前时间戳
    public String nowTimeLong() {
        return Long.toString(System.currentTimeMillis());
    }

    //指定时间戳
    public String timeLongByDate(String dateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateTime, fmt);
        long time = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return Long.toString(time);
    }

}
