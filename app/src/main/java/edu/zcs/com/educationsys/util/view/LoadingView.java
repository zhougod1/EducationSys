package edu.zcs.com.educationsys.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/3/16.
 */




/**
 * 数据加载中的界面 * Created by ccwant on 2016/9/8.
 */
public class LoadingView extends RelativeLayout {
    private ImageView mImgLoading;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 显示正在加载界面
     */
    public void showLoading() {
        setVisibility(View.VISIBLE);
//        ((AnimationDrawable) mImgLoading.getDrawable()).start();
    }

    /**
     * 隐藏正在加载界面
     */
    public void hideLoading() {
        setVisibility(View.GONE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        mImgLoading = (ImageView) findViewById(R.id.img_loading);
    }
}

