package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {


    public static String getNowData() {
        return getNowDataTime(true);
    }

    public static String getNowDataTime() {
        return getNowDataTime(false);
    }

    /**
     * 获取今天
     * @return
     */
    private static String getNowDataTime(boolean justDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(justDate ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        date = calendar.getTime();

        return simpleDateFormat.format(date);
    }

    public static String getFutureData(int dayCount) {
        return getFutureDataTime(dayCount, true);
    }

    public static String getFutureDataTime(int dayCount) {
        return getFutureDataTime(dayCount, false);
    }

    /**
     * 获取今天后多少天的时间
     * @param dayCount
     * @return
     */
    private static String getFutureDataTime(int dayCount, boolean justDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(justDate ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, dayCount);
        date = calendar.getTime();

        return simpleDateFormat.format(date);
    }


    public static String getPreviousData(int dayCount) {
        return getPreviousDataTime(dayCount, true);
    }

    public static String getPreviousDataTime(int dayCount) {
        return getPreviousDataTime(dayCount, false);
    }

    /**
     * 获取今天前多少天的时间
     * @param dayCount
     * @return
     */
    private static String getPreviousDataTime(int dayCount, boolean justDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(justDate ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, -dayCount);
        date = calendar.getTime();

        return simpleDateFormat.format(date);
    }

}