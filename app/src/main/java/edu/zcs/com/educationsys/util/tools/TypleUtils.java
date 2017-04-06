package edu.zcs.com.educationsys.util.tools;

/**
 * Created by Administrator on 2017/4/5.
 */

public class TypleUtils {

    public static String getNewsTyple(String typle){
        switch(typle){
            case "1":
                return "订单助手";
            case "2":
                return "问答助手";
        }
        return "";
    }
}
