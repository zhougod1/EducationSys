package edu.zcs.com.educationsys.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.BitmapUtils;


public class GridAdapter extends BaseAdapter {

    private LayoutInflater inflater; // 视图容器
    private int selectedPosition = -1;// 选中的位置
    private boolean shape;
    private Context context;

    public boolean isShape() {
        return shape;
    }
    public void setShape(boolean shape) {
        this.shape = shape;
    }
    public GridAdapter(Context context) {
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return (BitmapUtils.bmp.size() + 1);
    }
    public Object getItem(int arg0) {
        return null;
    }
    public long getItemId(int arg0) {
        return 0;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        final int coord = position;
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_published_grida,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == BitmapUtils.bmp.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(
                   context.getResources(), R.drawable.icon_addpic));
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setImageBitmap(BitmapUtils.bmp.get(position));
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }



}
