/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.utils.crypt;

/**
 * @author : 吴中勤
 * @date : 2019-05-14
 * description : TODO
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public final static String DEFALUT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param date 原日期
     * @return 一月中的第几天
     */
    public static int dayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return day;
    }

    public static String toString(Date d) {
        return new SimpleDateFormat(DEFALUT_TIMESTAMP_FORMAT).format(d);
    }

    public static Date fromString(String d) {
        DateFormat fmt = new SimpleDateFormat(DEFALUT_TIMESTAMP_FORMAT);
        Date date = null;
        try {
            date = fmt.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * @param srcDate 原日期
     * @param daysAdd 加上几天
     * @return 新日期
     */
    public static Date dateAdd(Date srcDate, int daysAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(srcDate);

        calendar.add(Calendar.DATE, daysAdd);
        return calendar.getTime();
    }

    public static Date dateAddSecond(Date srcDate, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(srcDate);

        calendar.add(Calendar.SECOND, sec);
        return calendar.getTime();
    }

    private static final long ONE_DAY_IN_MIL_SECONDS = 1000 * 60 * 60 * 24;

    /**
     * @param startDate 开始日期
     * @param endDate   对比日期
     * @return 相差几天
     */
    public static long diffDays(Date startDate, Date endDate) {
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long diffTime = endTime - startTime;
        return diffTime / ONE_DAY_IN_MIL_SECONDS;
    }

    public static String currentTs() {
        return new SimpleDateFormat(DEFALUT_TIMESTAMP_FORMAT).format(new Date());
    }
}
