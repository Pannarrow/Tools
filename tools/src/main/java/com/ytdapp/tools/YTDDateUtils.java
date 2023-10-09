package com.ytdapp.tools;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class YTDDateUtils {
    private static final String DATE_PATTERN_0 = "yyyy/MM/dd HH:mm";
    public static final String DATE_PATTERN_1 = "yyyy/MM/dd";
    private static final String DATE_PATTERN_2 = "yyyy/MM/dd HH:mm:ss";
    private static final String DATE_PATTERN_3 = "HH:mm";
    private static final String DATE_PATTERN_4 = "MM/dd HH:mm";
    public static final String DATE_PATTERN_5 = "yyyyMMdd";

    /**
     * 时间戳转日期显示
     * @param time 时间戳
     * @return 显示的字符串
     */
    public  static String timeChangeToShowString(String time){
       if (Long.getLong(time) <= 0){
           return "";
       }

       return "";
    }

    /**
     * 获取当前时间戳
     */
    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间 - Calendar方式
     */
    public static String getCurrentTimeYMDHMS() {
        //获取当前时间
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        return year + "-" + month+1 + "-" + date + " " + hour  + ":" + minute + ":" + second;
    }

    /**
     * 获取当前时间 - Calendar方式
     */
    public static String getCurrentTimeYMD() {
        //获取当前时间
        return new SimpleDateFormat(DATE_PATTERN_5).format(Calendar.getInstance().getTime());
    }

    /**
     * 获取上个月
     * @return
     */
    public static String getPreMonthYMD(int amount) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_5);
        Calendar c = Calendar.getInstance();
        //指定日期月份减去一
        c.add(Calendar.MONTH, - amount);
        //如果指定日期大于等于28，就把上一个月的日期设定为最大值
        if(Calendar.DATE>=28){
            c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));}
        //获取上一个月的时间
        Date lastDateOfPrevMonth = c.getTime();
        return sdf.format(lastDateOfPrevMonth);
    }

    /**
     * 年月日格式化为yyMMdd
     * @param year
     * @param month
     * @param day
     * @param pattern
     * @return
     */
    public static String dateFormat(int year, int month, int day, String pattern) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        return new SimpleDateFormat(pattern).format(c.getTime());
    }


    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        long lt = new Long(s);
        Date date = new Date(lt);

        //当前时间的
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        //传入时间戳的日期
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        int dateYear = calendar1.get(Calendar.YEAR);
        int dateMonth = calendar1.get(Calendar.MONTH);
        int dateDay = calendar1.get(Calendar.DATE);

        String res;
        if(dateYear == year){
            if(dateMonth == month){
                if(dateDay == day){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_3, Locale.getDefault());
                    res = (YTDLanguageManager.getInstance().isChinese()?"今天":"Today")+" "+ simpleDateFormat.format(date);
                    return res;

                }else if(dateDay == day - 1){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_3, Locale.getDefault());
                    res = (YTDLanguageManager.getInstance().isChinese()?"昨天":"Yesterday")+" "+ simpleDateFormat.format(date);
                    return res;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_4, Locale.getDefault());
            res = simpleDateFormat.format(date);
        }else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
            res = simpleDateFormat.format(date);
        }
        return res;
    }

    /**
     * 毫秒转为分秒
     * @param duration
     * @return
     */
    public static String timeToMmSs(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }

    /**
     * 时间对象转字符串
     */
    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取现在时间 - Date方式
     *
     * @return 返回时间类型 yyyy年MM月dd日 HH:mm
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间格式 yyyy年MM月dd日
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy年MM月dd日 HH:mm
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy年MM月dd日
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm
     *
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_3, Locale.getDefault());
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy年MM月dd日 HH:mm
     *
     * @param strDate
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        Date started = formatter.parse(strDate, pos);
        return started;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy年MM月dd日 HH:mm
     *
     * @param dateDate
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy年MM月dd日
     *
     * @param dateDate
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy年MM月dd日
     *
     * @param strDate
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    /**
     * 得到现在时间
     *
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     *
     * @param day
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_2, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat,Locale.getDefault());
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk;
        String[] jj;
        kk = st1.split(":");
        jj = st2.split(":");
        if (YTDParseUtil.parseInt(kk[0]) < YTDParseUtil.parseInt(jj[0]))
            return "0";
        else {
            double y = YTDParseUtil.parseDouble(kk[0]) + YTDParseUtil.parseDouble(kk[1]) / 60;
            double u = YTDParseUtil.parseDouble(jj[0]) + YTDParseUtil.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        long day;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN_0, Locale.getDefault());
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + YTDParseUtil.parseInt(jj) * 60L;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
            String mdate;
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + YTDParseUtil.parseInt(delay) * 24L * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断是否润年
     *
     * @param dateStr
     */
    public static boolean isLeapYear(String dateStr) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(dateStr);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat
     */
    public static String getEndDateOfMonth(String dat) {// yyyy年MM月dd日
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = YTDParseUtil.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 判断二个时间是否在同一个周
     *
     * @param date1
     * @param date2
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.getDefault());
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param dateStr
     * @param num
     */
    public static String getWeek(String dateStr, String num) {
        // 再转换为时间
        Date dd = strToDate(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault()).format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param dateStr
     */
    public static String getWeek(String dateStr) {
        // 再转换为时间
        Date date = strToDate(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE",Locale.getDefault()).format(c.getTime());
    }

    public static String getWeekStr(String dateStr) {
        String str;
        str = getWeek(dateStr);
        if ("1".equals(str)) {
            str = "星期日";
        } else if ("2".equals(str)) {
            str = "星期一";
        } else if ("3".equals(str)) {
            str = "星期二";
        } else if ("4".equals(str)) {
            str = "星期三";
        } else if ("5".equals(str)) {
            str = "星期四";
        } else if ("6".equals(str)) {
            str = "星期五";
        } else if ("7".equals(str)) {
            str = "星期六";
        }
        return str;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(DATE_PATTERN_1, Locale.getDefault());
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        return (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期
     *
     * @param dateStr 日期字符串
     */
    public static String getNowMonth(String dateStr) {
        // 取该时间所在月的一号
        dateStr = dateStr.substring(0, 8) + "01";

        // 得到这个月的1号是星期几
        Date date = strToDate(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        return getNextDay(dateStr, (1 - u) + "");
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */
    public static String getNo(int k) {
        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     *
     * @param i 随机
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }
}
