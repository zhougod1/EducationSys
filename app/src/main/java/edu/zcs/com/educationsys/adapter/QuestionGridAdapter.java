package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.Options;


public class QuestionGridAdapter extends BaseAdapter {

    private Context context;
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    private String[] path;

    public QuestionGridAdapter(Context context,String[] path) {
        this.context = context;
        this.path = path;
        imageLoader=ImageLoader.getInstance();
        options= Options.getListOptions();
    }

    @Override
    public int getCount() {
        return path==null?0:path.length;
    }

    @Override
    public Object getItem(int position) {return path[position];}
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(context, R.layout.question_photo_item, null);
            ImageView myimage = (ImageView) view.findViewById(R.id.question_image);
            if(path!=null&&path.length>0) {
                imageLoader.displayImage(HttpUtils.HOST2+"/Edu/img/"+path[position], myimage, options);
            }
        }
        return view;
    }

    public void setPath(String[] path) {
        this.path = path;
    }
}
