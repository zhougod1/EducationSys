package edu.zcs.com.educationsys.util.tools;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2017/5/6.
 */

public class BannerImageLoader extends ImageLoader {
    protected com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    private DisplayImageOptions options;
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageLoader= com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        options= Options.getListOptions();
        imageLoader.displayImage((String)path,imageView,options);
    }
}
