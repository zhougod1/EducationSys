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
import edu.zcs.com.educationsys.util.tools.DateUtils;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.Options;

public class BillAdapter extends BaseAdapter{

    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    private LayoutInflater inflater;
    private Context context;
    private List<Map<String,Object>> list;

    public BillAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
        imageLoader=ImageLoader.getInstance();
        options= Options.getListOptions();
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.bill_cardview_item,parent,false);
            holder=new ViewHolder();
            holder.bill_date= (TextView) convertView.findViewById(R.id.bill_date);
            holder.bill_pay= (TextView) convertView.findViewById(R.id.bill_pay);
            holder.bill_title= (TextView) convertView.findViewById(R.id.bill_title);
            holder.bill_week= (TextView) convertView.findViewById(R.id.bill_week);
            holder.bill_static= (TextView) convertView.findViewById(R.id.bill_static);
            holder.bill_head= (ImageView) convertView.findViewById(R.id.bill_head);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.bill_date.setText(list.get(position).get("date").toString().substring(4));
        holder.bill_pay.setText("￥"+list.get(position).get("pay"));
        holder.bill_title.setText(list.get(position).get("title").toString());
        holder.bill_static.setText(list.get(position).get("static").toString());
        holder.bill_week.setText(DateUtils.showDate(list.get(position).get("date").toString(),"yyyy-MM-dd"));
        if(list!=null&&list.size()>0){
            imageLoader.displayImage(HttpUtils.ImageHOST+list.get(position).get("head"),holder.bill_head,options);
        }

        return convertView;
    }

    public class ViewHolder{
        public TextView bill_week;
        public TextView bill_date;
        public TextView bill_pay;
        public TextView bill_title;
        public TextView bill_static;
        public ImageView bill_head;
    }
}
