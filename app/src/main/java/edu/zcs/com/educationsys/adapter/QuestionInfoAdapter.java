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
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.Options;

/**
 * Created by Administrator on 2017/3/18.
 */

public class QuestionInfoAdapter extends BaseAdapter {

    private String[] path;
    private List<Map<String,Object>> answerdate;
    private Map<String,Object> date;
    private Context context;
    private LayoutInflater inflater; // 视图容器
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    final int TYPE_TITLE= 0;
    final int TYPE_PHOTOS= 1;
    final int TYPE_ANSWERS= 2;
    final int TYPE_COMMENT= 3;
    final int TYPE_NOANSWER = 4;

    public QuestionInfoAdapter(String[] path, List<Map<String, Object>> answerdate, Map<String, Object> date, Context context) {
        this.path = path;
        this.answerdate = answerdate;
        this.date = date;
        this.context = context;
        imageLoader=ImageLoader.getInstance();
        options= Options.getListOptions();
        inflater = LayoutInflater.from(context);
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public void setAnswerdate(List<Map<String, Object>> answerdate) {
        this.answerdate = answerdate;
    }


    public void setDate(Map<String, Object> date) {
        this.date = date;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
       if(path!=null && date !=null) {
           if (position == 0) {
               return TYPE_TITLE;
           } else if (position > 0 && position <= path.length) {
               return TYPE_PHOTOS;
           } else if (position == path.length + 1) {
               return TYPE_ANSWERS;
           } else if (position > path.length + 1) {
               if (answerdate == null && answerdate.size() == 0) {
                   return TYPE_NOANSWER;
               } else {
                   return TYPE_COMMENT;
               }
           }
       }
            return TYPE_TITLE;
    }

    @Override
    public int getCount() {
        return (answerdate==null?0:answerdate.size()+1)+
                (path==null?0:path.length)+(date==null?0:1);
    }

    @Override
    public Object getItem(int position) {
        if(position==0){
            return date;
        }else if(position>0&&position<=path.length){
            return path[position-1];
        }else if(position==path.length+1){
            return answerdate.size();
        }else if(position>path.length+1){
            return answerdate==null?null:answerdate.get(position-(path.length+2));
        }else{
            return  null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder_answers viewHolder_answers=null;
        ViewHolder_comment viewHolder_comment=null;
        ViewHolder_photos viewHolder_photos=null;
        ViewHolder_title viewHolder_title=null;
        ViewHolder_noanswer viewHolder_noanswer=null;
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case TYPE_TITLE:
                    convertView = inflater.inflate(R.layout.question_info_title_item,
                            parent, false);
                    viewHolder_title = new ViewHolder_title();
                    viewHolder_title.question_info_name = (TextView) convertView.findViewById(R.id.question_info_aname);
                    viewHolder_title.question_info_title = (TextView) convertView.findViewById(R.id.question_info_title);
                    viewHolder_title.question_info_course = (TextView) convertView.findViewById(R.id.question_info_course);
                    viewHolder_title.question_info_content = (TextView) convertView.findViewById(R.id.question_info_context);
                    viewHolder_title.question_info_time = (TextView) convertView.findViewById(R.id.question_info_time);
                    viewHolder_title.question_info_aimg = (ImageView) convertView.findViewById(R.id.question_info_aimg);
                    if(date!=null&&date.size()>0) {
                        viewHolder_title.question_info_name.setText(date.get("account").toString());
                        viewHolder_title.question_info_title.setText(date.get("qtitle").toString());
                        viewHolder_title.question_info_course.setText(date.get("course").toString());
                        viewHolder_title.question_info_content.setText(date.get("qcontent").toString());
                        viewHolder_title.question_info_time.setText(date.get("time").toString());
                        imageLoader.displayImage(HttpUtils.ImageHOST + date.get("ahead").toString(),
                                viewHolder_title.question_info_aimg, options);
                    }
                    convertView.setTag(viewHolder_title);
                    break;
                case TYPE_PHOTOS:
                    convertView = inflater.inflate(R.layout.question_info_photo_item,
                            parent, false);
                    viewHolder_photos = new ViewHolder_photos();
                    viewHolder_photos.question_info_photos= (ImageView) convertView.findViewById(R.id.question_image);
                    if (path != null && path.length > 0) {
                        imageLoader.displayImage(HttpUtils.ImageHOST + path[position - 1], viewHolder_photos.question_info_photos, options);
                    }
                    convertView.setTag(viewHolder_photos);
                    break;
                case TYPE_ANSWERS:
                    convertView = inflater.inflate(R.layout.question_info_answers_item,
                            parent, false);
                    viewHolder_answers = new ViewHolder_answers();
                    viewHolder_answers.question_info_commentcount = (TextView) convertView.findViewById(R.id.question_info_commentcount);
                    if(answerdate!=null&&answerdate.size()>0) {
                        viewHolder_answers.question_info_commentcount.setText("(" + answerdate.size() + ")");
                    }
                    convertView.setTag(viewHolder_answers);
                    break;
                case TYPE_COMMENT:
                    int p = position - path.length - 2;
                    convertView = inflater.inflate(R.layout.question_info_comment_item,
                            parent, false);
                    viewHolder_comment = new ViewHolder_comment();
                    viewHolder_comment.question_comment_aimg = (ImageView) convertView.findViewById(R.id.comment_account_img);
                    viewHolder_comment.question_comment_account = (TextView) convertView.findViewById(R.id.comment_account_account);
                    viewHolder_comment.question_comment_time = (TextView) convertView.findViewById(R.id.comment_time);
                    viewHolder_comment.question_comment_content = (TextView) convertView.findViewById(R.id.comment_context);
                    if(answerdate!=null && answerdate.size()>0) {
                        viewHolder_comment.question_comment_account.setText(answerdate.get(p).get("account").toString());
                        viewHolder_comment.question_comment_time.setText(answerdate.get(p).get("time").toString());
                        viewHolder_comment.question_comment_content.setText(answerdate.get(p).get("ancontent").toString());
                        imageLoader.displayImage(HttpUtils.ImageHOST + answerdate.get(p).get("ahead").toString(), viewHolder_comment.question_comment_aimg, options);
                    }
                    convertView.setTag(viewHolder_comment);
                    break;
                case TYPE_NOANSWER:
                    convertView = inflater.inflate(R.layout.question_info_noanswer_item,
                            parent, false);
                    viewHolder_noanswer = new ViewHolder_noanswer();
                    convertView.setTag(viewHolder_noanswer);
                    break;
            }
        }else {
            switch (getItemViewType(position)) {
                case TYPE_TITLE:
                    viewHolder_title = (ViewHolder_title) convertView.getTag();
                    break;
                case TYPE_PHOTOS:
                    viewHolder_photos = (ViewHolder_photos) convertView.getTag();
                    break;
                case TYPE_ANSWERS:
                    viewHolder_answers = (ViewHolder_answers) convertView.getTag();
                    break;
                case TYPE_COMMENT:
                    viewHolder_comment = (ViewHolder_comment) convertView.getTag();
                    break;
                case TYPE_NOANSWER:
                    viewHolder_noanswer = (ViewHolder_noanswer) convertView.getTag();
                    break;

            }

        }
        return  convertView;
    }

    class ViewHolder_title{
        public TextView question_info_name;
        public TextView question_info_title;
        public TextView question_info_course;
        public TextView question_info_content;
        public TextView question_info_time;
        public ImageView question_info_aimg;
    }

    class ViewHolder_photos{
        public ImageView question_info_photos;
    }

    class ViewHolder_answers{
        public TextView question_info_commentcount;
    }
    class ViewHolder_comment{
        public ImageView question_comment_aimg;
        public TextView question_comment_account;
        public TextView question_comment_time;
        public TextView question_comment_content;
    }

    class ViewHolder_noanswer{
    }
}
