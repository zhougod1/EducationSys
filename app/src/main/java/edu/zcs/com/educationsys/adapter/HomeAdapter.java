package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
    final int TYPE_RECOMMENDTEACHER= 2;
    final int TYPE_RECOMMENDORDER= 3;
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
        if(position>1&&position<5)
            return TYPE_RECOMMENDTEACHER;
        if(position>4)
            return  TYPE_RECOMMENDORDER;
        return TYPE_ADVERTISEMENT;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder_advertisement advertisement=null;
        ViewHolder_coures coures=null;
        ViewHolder_recommendorder recommendorder=null;
        ViewHolder_recommendteacher recommendteacher=null;
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
                case TYPE_RECOMMENDTEACHER:
                break;
                case TYPE_RECOMMENDORDER:
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
                case TYPE_RECOMMENDTEACHER:
                    recommendorder = (ViewHolder_recommendorder) convertView.getTag();
                    break;
                case TYPE_RECOMMENDORDER:
                    recommendteacher = (ViewHolder_recommendteacher) convertView.getTag();
                    break;
            }
        }
        return convertView;
    }

    class ViewHolder_advertisement{

    }
    class ViewHolder_coures{

    }
    class ViewHolder_recommendorder{

    }
    class ViewHolder_recommendteacher{

    }

}
