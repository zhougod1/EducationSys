package edu.zcs.com.educationsys.util.tools;

import edu.zcs.com.educationsys.R;

import static edu.zcs.com.educationsys.R.drawable.chinese;

/**
 * Created by Administrator on 2017/3/19.
 */

public class CourseUtils {

    public static int getCourseIcon(String kemu){
        int course=0;
        switch (kemu){
            case "数学":
                course= R.drawable.math;
                break;
            case "语文":
                course= chinese;
                break;
            case "英语":
                course=R.drawable.english;
                break;
            case "历史":
                course=R.drawable.history;
                break;
            case "地理":
                course=R.drawable.geography;
                break;
            case "政治":
                course=R.drawable.politics;
                break;
            case "生物":
                course=R.drawable.biology;
                break;
            case "物理":
                course=R.drawable.physics;
                break;
            case "化学":
                course=R.drawable.chemistry;
            break;
        }
        return  course;
    }

    public static String getCourseToC(String kemu){
        String course=null;
        switch (kemu){
            case "chinese":
                course="语文";
                break;
            case "english":
                course="英语";
                break;
            case "math":
                course="数学";
                break;
            case "physics":
                course="物理";
                break;
            case "politics":
                course="政治";
                break;
            case "biology":
                course="生物";
                break;
            case "geography":
                course="地理";
                break;
            case "history":
                course="历史";
                break;
            case "chemistry":
                course="化学";
                break;
        }
        return  course;
    }
}
