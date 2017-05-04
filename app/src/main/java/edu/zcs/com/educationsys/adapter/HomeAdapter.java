package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.Options;

/**
 * Created by Administrator on 2017/3/19.
 */

public class HomeAdapter extends BaseAdapter {

    final int TYPE_ADVERTISEMENT= 0;
    final int TYPE_COURES= 1;
    final int TYPE_RECOMMEND= 2;
    final int TYPE_ORDER= 3;
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context context;
    private List<Map<String,Object>> list;
    private LayoutInflater inflater;


    public HomeAdapter(Context context, List<Map<String, Object>> list) {
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
                    convertView=inflater.inflate(R.layout.home_advertisement_item,parent,false);
                    advertisement=new ViewHolder_advertisement();
                    convertView.setTag(advertisement);
                break;
                case TYPE_COURES:
                    convertView=inflater.inflate(R.layout.home_course_item,parent,false);
                    coures=new ViewHolder_coures();
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
                    order = (ViewHolder_order) convertView.getTag();
                    break;
                case TYPE_ORDER:
                    recommend = (ViewHolder_recommend) convertView.getTag();
                    break;
            }
        }
        return convertView;
    }

    class ViewHolder_advertisement{

    }
    class ViewHolder_coures{
        public ImageView home_courses_chinese;
        public ImageView home_courses_english;
        public ImageView home_courses_math;
        public ImageView home_courses_chemistry;
        public ImageView home_courses_geography;
        public ImageView home_courses_history;
        public ImageView home_courses_physics;
        public ImageView home_courses_poloitics;
        public ImageView home_courses_biology;
        public ImageView home_courses_all;

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
    }

}
