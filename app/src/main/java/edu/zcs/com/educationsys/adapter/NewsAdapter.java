package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.entity.News;
import edu.zcs.com.educationsys.util.tools.TypleUtils;


public  class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> implements View.OnClickListener{

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private ArrayList<News> list;
    private Context context;

    public NewsAdapter(Context context, ArrayList<News> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_cardview,parent,false);
        MyViewHolder vh=new MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.news_title.setText(TypleUtils.getNewsTyple(list.get(position).getN_typle().toString()));
        holder.news_centent_first.setText(list.get(position).getN_title().toString());
        holder.itemView.setTag(list.get(position).getN_typle().toString());
        if(list.get(position).getN_static()!=null&&!list.get(position).getN_static().equals("")){
            holder.point_img.setVisibility(View.GONE);
        }

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

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView news_title,news_centent_first;
        private ImageView news_img,point_img;
        public MyViewHolder(View itemView) {
            super(itemView);
            news_title=(TextView) itemView.findViewById(R.id.news_title);
            news_centent_first=(TextView) itemView.findViewById(R.id.news_content_first);
            news_img=(ImageView)itemView.findViewById(R.id.news_img);
            point_img=(ImageView)itemView.findViewById(R.id.point_img);

        }
    }

    public void setList(ArrayList<News> list) {
        this.list = list;
    }
}
