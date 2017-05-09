package edu.zcs.com.educationsys.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.activity.OrderActivity;
import edu.zcs.com.educationsys.util.entity.Order;
import edu.zcs.com.educationsys.util.tools.BannerImageLoader;
import edu.zcs.com.educationsys.util.tools.CourseUtils;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.Options;

/**
 * Created by Administrator on 2017/3/19.
 */

public class HomeAdapter extends BaseAdapter implements View.OnClickListener {

    final int TYPE_ADVERTISEMENT= 0;
    final int TYPE_COURES= 1;
    final int TYPE_RECOMMEND= 2;
    final int TYPE_ORDER= 3;
    private OnListViewItemClickListener onItemClickListener=null;
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context context;
    private List<Order> list;
    private LayoutInflater inflater;
    private List<String> path;


    public HomeAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
        imageLoader=ImageLoader.getInstance();
        options= Options.getListOptions();
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TYPE_ADVERTISEMENT;
        if(position==1)
            return TYPE_COURES;
        if(position==2)
            return TYPE_RECOMMEND;
        if(position>2)
            return  TYPE_ORDER;
        return TYPE_ADVERTISEMENT;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getCount() {
        return list==null?3:list.size()+3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder_advertisement advertisement=null;
        ViewHolder_coures coures=null;
        ViewHolder_order order=null;
        ViewHolder_recommend recommend=null;

        if(convertView==null){
            switch (getItemViewType(position)){
                case TYPE_ADVERTISEMENT:
                    path=new ArrayList<>();
                    path.add(HttpUtils.HOST2+"/Edu/img/2.jpg");
                    path.add(HttpUtils.HOST2+"/Edu/img/3.jpg");
                    convertView=inflater.inflate(R.layout.home_advertisement_item,parent,false);
                    advertisement=new ViewHolder_advertisement();
                    advertisement.home_banner= (Banner) convertView.findViewById(R.id.banner);
                    advertisement.home_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                    //设置图片加载器
                    advertisement.home_banner.setImageLoader(new BannerImageLoader());
                    //设置图片集合
                    advertisement.home_banner.setImages(path);
                    //设置banner动画效果
                    advertisement.home_banner.setBannerAnimation(Transformer.Default);
                    //设置标题集合（当banner样式有显示title时）
//                    advertisement.home_banner.setBannerTitles(titles);
                    //设置自动轮播，默认为true
//                    advertisement.home_banner.isAutoPlay(true);
                    //设置轮播时间
                    advertisement.home_banner.setDelayTime(3000);
                    //设置指示器位置（当banner模式中有指示器时）
                    advertisement.home_banner.setIndicatorGravity(BannerConfig.RIGHT);
                    //banner设置方法全部调用完毕时最后调用
                    advertisement.home_banner.start();
                    convertView.setTag(advertisement);
                break;
                case TYPE_COURES:
                    convertView=inflater.inflate(R.layout.home_course_item,parent,false);
                    coures=new ViewHolder_coures();
                    coures.home_courses_chinese=(LinearLayout)convertView.findViewById(R.id.chinese_bt);
                    coures.home_courses_english=(LinearLayout)convertView.findViewById(R.id.english_bt);
                    coures.home_courses_math=(LinearLayout)convertView.findViewById(R.id.math_bt);
                    coures.home_courses_chemistry=(LinearLayout)convertView.findViewById(R.id.chemistry_bt);
                    coures.home_courses_geography=(LinearLayout)convertView.findViewById(R.id.geography_bt);
                    coures.home_courses_history=(LinearLayout)convertView.findViewById(R.id.history_bt);
                    coures.home_courses_physics=(LinearLayout)convertView.findViewById(R.id.physics_bt);
                    coures.home_courses_politics=(LinearLayout)convertView.findViewById(R.id.politics_bt);
                    coures.home_courses_biology=(LinearLayout)convertView.findViewById(R.id.biology_bt);
                    coures.home_courses_all=(LinearLayout)convertView.findViewById(R.id.all_bt);
                    coures.home_courses_chinese.setOnClickListener(this);
                    coures.home_courses_english.setOnClickListener(this);
                    coures.home_courses_math.setOnClickListener(this);
                    coures.home_courses_chemistry.setOnClickListener(this);
                    coures.home_courses_geography.setOnClickListener(this);
                    coures.home_courses_history.setOnClickListener(this);
                    coures.home_courses_physics.setOnClickListener(this);
                    coures.home_courses_politics.setOnClickListener(this);
                    coures.home_courses_biology.setOnClickListener(this);
                    coures.home_courses_all.setOnClickListener(this);
                    convertView.setTag(coures);
                break;
                case TYPE_RECOMMEND:
                    convertView=inflater.inflate(R.layout.home_recommend_item,parent,false);
                    recommend=new ViewHolder_recommend();
                    convertView.setTag(coures);
                break;
                case TYPE_ORDER:
                    convertView=inflater.inflate(R.layout.order_cardview_item,parent,false);
                    order=new ViewHolder_order();
                    order.order_time=(TextView) convertView.findViewById(R.id.work_Time);
                    order.order_adderss=(TextView) convertView.findViewById(R.id.work_Address);
                    order.order_course=(TextView) convertView.findViewById(R.id.work_Course);
                    order.order_pay=(TextView) convertView.findViewById(R.id.work_Pay);
                    order.order_level=(TextView) convertView.findViewById(R.id.work_Level);
                    order.order_title=(TextView) convertView.findViewById(R.id.work_Titile);
                    order.order_cycle=(TextView) convertView.findViewById(R.id.work_Cycle);
                    order.order_date=(TextView) convertView.findViewById(R.id.work_Date);
                    order.order_icon=(ImageView) convertView.findViewById(R.id.work_Icon);

                    order.order_title.setText(list.get(position).getOtitle().toString());
                    order.order_date.setText(list.get(position).getOdate().toString());
//          holder.worktime.setText(list.get(position).getOtime().toString());
                    order.order_adderss.setText(list.get(position).getOarea().toString());
                    order.order_pay.setText("￥"+list.get(position).getPay());
                    order.order_course.setText(list.get(position).getOcourse().toString());
                    order.order_level.setText(list.get(position).getOlevel().toString());
                    int img= CourseUtils.getCourseIcon(list.get(position).getOcourse());
                    order.order_icon.setImageResource(img);
                    order.itemid=list.get(position).getOid().toString();
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onItemClickListener!=null){
                                onItemClickListener.onItemClick(v,((ViewHolder_order)v.getTag()).itemid);
                            }
                        }
                    });
                    convertView.setTag(order);
                break;

            }
        }else {
            switch (getItemViewType(position)) {
                case TYPE_ADVERTISEMENT:
                    advertisement = (ViewHolder_advertisement) convertView.getTag();
                    break;
                case TYPE_COURES:
                    coures = (ViewHolder_coures) convertView.getTag();
                    break;
                case TYPE_RECOMMEND:
                    recommend = (ViewHolder_recommend) convertView.getTag();
                    break;
                case TYPE_ORDER:
                    order = (ViewHolder_order) convertView.getTag();

                    break;
            }
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Intent i=new Intent((Activity)context, OrderActivity.class);
        switch (v.getId()){
            case R.id.chinese_bt:
                i.putExtra("course","chinese");
                break;
            case R.id.english_bt:
                i.putExtra("course","english");
                break;
            case R.id.math_bt:
                i.putExtra("course","math");
                break;
            case R.id.physics_bt:
                i.putExtra("course","physics");
                break;
            case R.id.politics_bt:
                i.putExtra("course","politics");
                break;
            case R.id.biology_bt:
                i.putExtra("course","biology");
                break;
            case R.id.geography_bt:
                i.putExtra("course","geography");
                break;
            case R.id.history_bt:
                i.putExtra("course","history");
                break;
            case R.id.chemistry_bt:
                i.putExtra("course","chemistry");
                break;
            case R.id.all_bt:
                i.putExtra("course","");
                break;
        }
        context.startActivity(i);
    }

    public static interface OnListViewItemClickListener {
        void onItemClick(View view, String position);
    }

    class ViewHolder_advertisement{
        public Banner home_banner;
    }
    class ViewHolder_coures{
        public LinearLayout home_courses_chinese;
        public LinearLayout home_courses_english;
        public LinearLayout home_courses_math;
        public LinearLayout home_courses_chemistry;
        public LinearLayout home_courses_geography;
        public LinearLayout home_courses_history;
        public LinearLayout home_courses_physics;
        public LinearLayout home_courses_politics;
        public LinearLayout home_courses_biology;
        public LinearLayout home_courses_all;

    }
    class ViewHolder_recommend{
    }

    class ViewHolder_order{
        private TextView order_time;
        private TextView order_adderss;
        private TextView order_course;
        private TextView order_pay;
        private TextView order_level;
        private TextView order_title;
        private TextView order_date;
        private TextView order_cycle;
        private ImageView order_icon;
        public String itemid;
    }

    public void setOnItemClickListener(OnListViewItemClickListener onItemClickListener) {
        this.onItemClickListener =onItemClickListener;
    }

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }
    
}
