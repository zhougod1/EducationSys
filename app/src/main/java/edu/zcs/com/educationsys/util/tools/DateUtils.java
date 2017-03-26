package edu.zcs.com.educationsys.util.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/14.
 */

public class DateUtils {
    public String getTime(){
        SimpleDateFormat formatter =new  SimpleDateFormat("HH:mm");
        Date curDate = new  Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getDate(){
        SimpleDateFormat formatter =new  SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new  Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getWeek(int i){
        String week="";
        switch(i){
            case 1:
                week="周日";
                break;
            case 2:
                week="周一";
                break;
            case 3:
                week="周二";
                break;
            case 4:
                week="周三";
                break;
            case 5:
                week="周四";
                break;
            case 6:
                week="周五";
                break;
            case 7:
                week="周六";
                break;
        }
        return week;
    }

    public Boolean isToday(String date) {
        Date now = null;
        Date my = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            now = formatter.parse(date);
            my = formatter.parse(getDate());
        } catch (Exception e) {
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(my);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(now);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

//    public static String getWhatDay(String  date){
//
//        Date my=null;
//        Date now = null;
//        String week="";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
////            now = formatter.parse(Date.getDate());
//            my = formatter.parse(date);
//        } catch (Exception e) {}
//        Calendar cal= Calendar.getInstance();
//        cal.setTime(my);
//
//        boolean isSameYear = cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR);
//        boolean isSameMonth = isSameYear && cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH);
//        if(isSameMonth && cal.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)){
//            week="今天";
//        }else if(isSameMonth && cal.get(Calendar.DAY_OF_MONTH)== cal.get(Calendar.DAY_OF_MONTH)){
//            week=getWeek(cal.get(Calendar.WEEK_OF_MONTH));
//
//        }
//        return "";
//    }



    /**
     * 2016-11-08 14:39:38
     * pattern yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String showDate(String date,String pattern){
        Date my=null;
        Date now=null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            my = format.parse(date);
            now = format.parse(getDate());
        } catch (Exception e) {}
        Calendar cal= Calendar.getInstance();
        cal.setTime(my);
        Calendar cal1= Calendar.getInstance();
        cal1.setTime(now);
        Long yearNum = Long.valueOf(cal.get(Calendar.YEAR));
        int month =cal.get(Calendar.MONTH);
        int day =cal.get(Calendar.DAY_OF_MONTH);
//        String hour = dateStr.substring(11,13);
//        String minute = dateStr.substring(14,16);

        long addtime =my.getTime();
        long today=System.currentTimeMillis();
        int  nowDay =cal1.get(Calendar.DAY_OF_MONTH);
        String result="";
        long l=today-addtime;//当前时间与给定时间差的毫秒数
        long days=l/(24*60*60*1000);//这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间
        long hours=(l/(60*60*1000)-days*24);//这个时间相差的减去天数的小时数
//        long min=((l/(60*1000))-days*24*60-hours*60);//
//        long s=(l/1000-days*24*60*60-hours*60*60-min*60);
        if(days > 0){
            if(days>0 && days<2){
                result ="前天";
            } else {
                result =getWeek(cal.get(Calendar.DAY_OF_WEEK));
            }
        }else if(hours > 0 ) {
            if(day!=nowDay){
                result = "昨天";
            }else{
                result="今天";
            }
        }

        return result;
    }
}
