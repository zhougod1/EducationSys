package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.Options;

import static edu.zcs.com.educationsys.R.id.comment_account_img;
import static edu.zcs.com.educationsys.R.id.comment_context;
import static edu.zcs.com.educationsys.R.id.comment_time;

/**
 * Created by Administrator on 2017/3/2.
 */

public class AnswerAdapter extends BaseAdapter{
    private LayoutInflater inflater; // 视图容器
    private Context context;
    private List<Map<String,Object>> list=null;
//    private List<Bitmap> images=null;
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;


    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notifyDataSetChanged();
        }
    };

    public AnswerAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
//        images=new ArrayList<Bitmap>();
        imageLoader=ImageLoader.getInstance();
        options= Options.getListOptions();

    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.question_info_comment_item,
                    parent, false);
            holder = new ViewHolder();
            holder.comment_account_accout= (TextView) convertView.findViewById(R.id.comment_account_account);
            holder.comment_context= (TextView) convertView.findViewById(comment_context);
            holder.comment_time = (TextView) convertView.findViewById(comment_time);
            holder.comment_account_img =(ImageView)convertView.findViewById(comment_account_img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.comment_account_accout.setText(list.get(position).get("account").toString());
        holder.comment_context.setText(list.get(position).get("ancontent").toString());
        holder.comment_time.setText(list.get(position).get("time").toString());
        if(list!=null&&list.size()>0){
            imageLoader.displayImage(HttpUtils.HOST2+"/Edu/img/"+list.get(position).get("ahead"),
                    holder.comment_account_img,options);
        }

        return convertView;

    }

//    public void init(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String URL= HttpUtils.HOST2+"/Edu/img/";
//                for(int i=0;i<list.size();i++) {
//                    Bitmap image = HttpUtils.getUrlImage(URL+list.get(i).get("ahead").toString());
//                    images.add(image);
//                }
//                Message message=new Message();
//                mhandler.sendMessage(message);
//            }
//        }).start();
//    }

    public class ViewHolder {
        public ImageView comment_account_img;
        public TextView comment_account_accout;
        public TextView comment_context;
        public  TextView comment_time;
    }

}
