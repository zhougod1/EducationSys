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
import edu.zcs.com.educationsys.util.entity.Order;
import edu.zcs.com.educationsys.util.tools.CourseUtils;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> implements View.OnClickListener {


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context context;
    private ArrayList<Order> list;
    public OrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(ArrayList<Order> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_cardview_item,parent,false);
        MyViewHolder vh=new MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.worktitle.setText(list.get(position).getOtitle().toString());
        holder.workdate.setText(list.get(position).getOdate().toString());
//        holder.worktime.setText(list.get(position).getOtime().toString());
        holder.workadderss.setText(list.get(position).getOarea().toString());
        holder.workpay.setText("￥"+list.get(position).getPay());
        holder.workcourse.setText(list.get(position).getOcourse().toString());
        holder.worklevel.setText(list.get(position).getOlevel().toString());
        int img= CourseUtils.getCourseIcon(list.get(position).getOcourse());
        holder.workicon.setImageResource(img);
        holder.itemView.setTag(list.get(position).getOid().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
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

    class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView worktime,workadderss,workcourse,workpay,worklevel,worktitle,workdate,workcycle;
        private ImageView workicon;
         MyViewHolder(View itemView) {
            super(itemView);
             worktime=(TextView) itemView.findViewById(R.id.work_Time);
             workadderss=(TextView) itemView.findViewById(R.id.work_Address);
             workcourse=(TextView) itemView.findViewById(R.id.work_Course);
             workpay=(TextView) itemView.findViewById(R.id.work_Pay);
             worklevel=(TextView) itemView.findViewById(R.id.work_Level);
             worktitle=(TextView) itemView.findViewById(R.id.work_Titile);
             workcycle=(TextView) itemView.findViewById(R.id.work_Cycle);
             workdate=(TextView) itemView.findViewById(R.id.work_Date);
             workicon=(ImageView) itemView.findViewById(R.id.work_Icon);
        }
    }

}
