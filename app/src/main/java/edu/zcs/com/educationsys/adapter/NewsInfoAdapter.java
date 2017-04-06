package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;


public class NewsInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private NewsInfoAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String,Object>> list;
    private Context context;


    public NewsInfoAdapter(Context context, List<Map<String,Object>> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.news_cardview_item, parent, false);
            ReleaseViewHolder vh = new ReleaseViewHolder(view);
            view.setOnClickListener(this);
            return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (list.get(position).get("n_title").toString().equals("对方回复兼职")) {
            ((ReleaseViewHolder) holder).news_change.setText("回复状态：");
            ((ReleaseViewHolder) holder).news_info_name.setText(list.get(position).get("static").toString());
        } else if (list.get(position).get("n_title").toString().equals("有人请求兼职")) {
            ((ReleaseViewHolder) holder).news_info_name.setText(list.get(position).get("name").toString());
        }
        ((ReleaseViewHolder) holder).news_info_title.setText(list.get(position).get("n_title").toString());
        ((ReleaseViewHolder) holder).news_info_time.setText(list.get(position).get("time").toString());
        ((ReleaseViewHolder) holder).news_info.setText(list.get(position).get("title") + "\n" + list.get(position).get("info").toString());
        ((ReleaseViewHolder) holder).itemView.setTag(list.get(position).get("n_sid"));
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(NewsInfoAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public  class ReleaseViewHolder extends RecyclerView.ViewHolder{

        private TextView news_info_title,news_info,news_info_time,news_info_name,news_change;
        private LinearLayout news_sourse;
        public ReleaseViewHolder(View itemView) {
            super(itemView);
            news_info_title=(TextView) itemView.findViewById(R.id.news_info_title);
            news_info=(TextView) itemView.findViewById(R.id.news_info);
            news_info_name=(TextView)itemView.findViewById(R.id.news_info_name);
            news_info_time=(TextView)itemView.findViewById(R.id.news_info_time);
            news_sourse=(LinearLayout)itemView.findViewById(R.id.news_sourse);
            news_change=(TextView)itemView.findViewById(R.id.news_change);

        }
    }
    public void setList(List<Map<String,Object>> list) {
        this.list = list;
    }
}
