package edu.zcs.com.educationsys.util.tools;

import edu.zcs.com.educationsys.R;

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
                course=R.drawable.chinese;
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
}
