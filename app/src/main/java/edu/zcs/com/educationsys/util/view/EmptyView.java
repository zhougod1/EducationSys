package edu.zcs.com.educationsys.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.zcs.com.educationsys.R;

//第二步：封装EmptyView.java

/**
 * 数据为空的界面 * Created by ccwant on 2016/9/8.
 */
public class EmptyView extends RelativeLayout{

    private TextView mTxtEmptyTip;
    private OnClickListener onClickListener=null;
    private Button onRefresh;

    public EmptyView(Context context) {
        super(context);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onRefresh=(Button)findViewById(R.id.view_onRefresh);
        onRefresh.setOnClickListener(onClickListener);
//        mTxtEmptyTip = (TextView) findViewById(R.id.txt_empty_tip);
    }

    /**
     * 设置提示信息     * @param tip
     */
    public void setEmptyTip(String tip) {
        mTxtEmptyTip.setText(tip);
    }

    /**
     * 设置提示信息     * @param resId
     */
    public void setEmptyTip(int resId) {
        mTxtEmptyTip.setText(resId);
    }
}

