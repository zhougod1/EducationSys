package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.Options;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> implements View.OnClickListener {


    private QuestionAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context context;
    private List<Map<String,Object>> list;
    private String[] path;
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    private QuestionGridAdapter myadapter;
    private MyViewHolder myHolder;

    public void setList(List<Map<String, Object>> list) {this.list = list;}

    public void update(){
        notifyDataSetChanged();
    }

    public QuestionAdapter(Context context,List<Map<String,Object>> list) {
        this.context = context;
        this.list = list;
        imageLoader=ImageLoader.getInstance();
        options= Options.getListOptions();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.question_cardview_item,parent,false);
        MyViewHolder vh=new MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

        myHolder = holder;
        holder.question_context.setText(list.get(position).get("qcontent").toString());
        holder.account_name.setText(list.get(position).get("account").toString());
        if(list!=null&&list.size()>0) {
            imageLoader.displayImage(HttpUtils.HOST2 + "/Edu/img/" + list.get(position).get("ahead"), myHolder.account_img, options);
        }

        path = list.get(position).get("img").toString().split(";");
        if(path.length>1) {
            myadapter = new QuestionGridAdapter(context,path);
            holder.question_photos.setAdapter(myadapter);
        }else if(path.length==1){
            imageLoader.displayImage(HttpUtils.HOST2 + "/Edu/img/"+ path[0],holder.question_photo,options);
        }
        holder.question_time.setText(list.get(position).get("time").toString());
        holder.question_title.setText(list.get(position).get("qtitle").toString());
        holder.question_course.setText("["+list.get(position).get("course")+"]");
        holder.itemView.setTag(list.get(position).get("qid").toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }

    }

    public void setOnItemClickListener(QuestionAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    class MyViewHolder extends  RecyclerView.ViewHolder{
        private TextView question_title,account_name,question_time,question_context,question_info,question_course;
        private ImageView account_img,question_photo;
        private GridView question_photos;
        MyViewHolder(View itemView) {
            super(itemView);
             question_time=(TextView)itemView.findViewById(R.id.question_time);
             question_title=(TextView)itemView.findViewById(R.id.question_title);
             question_context=(TextView)itemView.findViewById(R.id.question_context);
             question_context.setSingleLine();
             question_context.setMaxEms(20);
             question_context.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
             question_info=(TextView)itemView.findViewById(R.id.question_info);
             question_course=(TextView)itemView.findViewById(R.id.question_course);
             account_name=(TextView)itemView.findViewById(R.id.account_name);
             account_img= (ImageView) itemView.findViewById(R.id.account_img);
             question_photo= (ImageView) itemView.findViewById(R.id.question_photo);
             question_photos= (GridView) itemView.findViewById(R.id.question_photos);

        }

    }
}
