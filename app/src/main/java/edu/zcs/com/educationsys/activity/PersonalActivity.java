package edu.zcs.com.educationsys.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.entity.Account;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.view.ObservableScrollView;

public class PersonalActivity extends AppCompatActivity
        implements ObservableScrollView.Callbacks {
    protected static final String URL = HttpUtils.HOST2+ "/Edu/Account/getById";
    private static final float GAP_FILL_DISTANCE_MULTIPLIER = 1.5f;
    private static final float HEADER_IMAGE_BACKGROUND_PARALLAX_EFFECT_MULTIPLIER = 0.5f;
    private static final int[] RES_IDS_ACTION_BAR_SIZE = {android.R.attr.actionBarSize};
    private static final float HEADER_IMAGE_ASPECT_RATIO = 1.7777777f;

    private ObservableScrollView scrollView;
    private View headerImageContainer;
    private ImageView headerImage;
    private Account account;
    private View bodyContainer;
    private View headerBarContainer;
    private View headerBarContents;
    private View headerBarBackground;
    private View headerBarShadow;
    private int headerBarTopClearance;
    private int headerImageHeightPixels;
    private int headerBarContentsHeightPixels;
    private boolean showHeaderImage;
    private boolean gapFillShown;
    private Bitmap image;
    private TextView personal_info_name;
    private TextView personal_info_sex;
    private TextView personal_info_phone;
    private TextView personal_info_addres;
    private TextView personal_info_typle;
    private TextView personal_info_account;
    private LinearLayout personal_typle;
    private LinearLayout personal_info_submit;
    private ImageView personal_info_header;
    private ACache cache;

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            personal_info_header.setImageBitmap(image);
            personal_info_name.setText(account.getAname());
            personal_info_addres.setText(account.getAaddress());
            personal_info_phone.setText(account.getAphonenumber());
            personal_info_account.setText(account.getAccount());
            personal_info_sex.setText(account.getSex());
        }
    };
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener
            = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            recomputeHeaderImageAndScrollingMetrics();
        }
    };


    public PersonalActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        cache=ACache.get(this);
        scrollView = (ObservableScrollView)findViewById(R.id.scroll_view);
        headerImageContainer =findViewById(R.id.header_image_container);
        headerImage = (ImageView) findViewById(R.id.header_image);
        bodyContainer =findViewById(R.id.body_container);
        headerBarContainer = findViewById(R.id.header_bar_container);
        headerBarContents = findViewById(R.id.header_bar_contents);
        headerBarBackground =findViewById(R.id.header_bar_background);
        headerBarShadow =findViewById(R.id.header_bar_shadow);
        personal_info_name=(TextView)findViewById(R.id.personal_info_name);
        personal_info_sex=(TextView)findViewById(R.id.personal_info_sex);
        personal_info_phone=(TextView)findViewById(R.id.personal_info_phone);
        personal_info_addres=(TextView)findViewById(R.id.personal_info_addres);
        personal_info_typle=(TextView)findViewById(R.id.personal_info_typle);
        personal_info_account=(TextView)findViewById(R.id.personal_info_account);
        personal_info_header= (ImageView) findViewById(R.id.personal_info_header);
        personal_info_submit= (LinearLayout)findViewById(R.id.personal_info_submit);
        personal_info_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PersonalActivity.this, PersonalAlterActivity.class);
                startActivity(i);
            }
        });
        personal_typle=(LinearLayout)findViewById(R.id.personal_typle);
        personal_typle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        init();
        setupCustomScrolling();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scrollView == null) {
            return;
        }

        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                vto.removeGlobalOnLayoutListener(globalLayoutListener); /* deprecated */
            } else {
                vto.removeOnGlobalLayoutListener(globalLayoutListener);
            }
        }
    }

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        final Activity activity = this;
        if (activity == null || activity.isFinishing()) {
            return;
        }

        // Reposition the header bar -- it's normally anchored to the top of the content,
        // but locks to the top of the screen on scroll
        int scrollY = scrollView.getScrollY();

        float newTop = Math.max(headerImageHeightPixels, scrollY + headerBarTopClearance);
        headerBarContainer.setTranslationY(newTop);
        headerBarBackground.setPivotY(headerBarContentsHeightPixels);

        int gapFillDistance = (int) (headerBarTopClearance * GAP_FILL_DISTANCE_MULTIPLIER);
        boolean showGapFill = !showHeaderImage || (scrollY > (headerImageHeightPixels - gapFillDistance));
        float desiredHeaderScaleY = showGapFill ?
                ((headerBarContentsHeightPixels + gapFillDistance + 1) * 1f / headerBarContentsHeightPixels)
                : 1f;
        if (!showHeaderImage) {
            headerBarBackground.setScaleY(desiredHeaderScaleY);
        } else if (gapFillShown != showGapFill) {
            headerBarBackground.animate()
                    .scaleY(desiredHeaderScaleY)
                    .setInterpolator(new DecelerateInterpolator(2f))
                    .setDuration(250)
                    .start();
        }
        gapFillShown = showGapFill;

        // Make a shadow. TODO: Do not need if running on AndroidL
        headerBarShadow.setVisibility(View.VISIBLE);

        if (headerBarTopClearance != 0) {
            // Fill the gap between status bar and header bar with color
            float gapFillProgress = Math.min(Math.max(getProgress(scrollY,
                    headerImageHeightPixels - headerBarTopClearance * 2,
                    headerImageHeightPixels - headerBarTopClearance), 0), 1);
            // TODO: Set elevation properties if running on AndroidL
            headerBarShadow.setAlpha(gapFillProgress);
//            headerBarBackground.setAlpha(gapFillProgress);
        }

        // Move background image (parallax effect)
        headerImageContainer.setTranslationY(scrollY * HEADER_IMAGE_BACKGROUND_PARALLAX_EFFECT_MULTIPLIER);
    }

    private void recomputeHeaderImageAndScrollingMetrics() {
        final int actionBarSize = calculateActionBarSize();
        headerBarTopClearance = actionBarSize - headerBarContents.getPaddingTop();
        headerBarContentsHeightPixels = headerBarContents.getHeight();

        headerImageHeightPixels = headerBarTopClearance;
        if (showHeaderImage) {
            headerImageHeightPixels = (int) (headerImage.getWidth() / HEADER_IMAGE_ASPECT_RATIO);
            headerImageHeightPixels = Math.min(headerImageHeightPixels, getWindowManager().getDefaultDisplay().getHeight() * 2 / 3);
        }

        ViewGroup.LayoutParams lp;
        lp = headerImageContainer.getLayoutParams();
        if (lp.height != headerImageHeightPixels) {
            lp.height = headerImageHeightPixels;
            headerImageContainer.setLayoutParams(lp);
        }

        lp = headerBarBackground.getLayoutParams();
        if (lp.height != headerBarContentsHeightPixels) {
            lp.height = headerBarContentsHeightPixels;
            headerBarBackground.setLayoutParams(lp);
        }

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)
                bodyContainer.getLayoutParams();
        if (mlp.topMargin != headerBarContentsHeightPixels + headerImageHeightPixels) {
            mlp.topMargin = headerBarContentsHeightPixels + headerImageHeightPixels;
            bodyContainer.setLayoutParams(mlp);
        }

        onScrollChanged(0, 0); // trigger scroll handling
    }

    private void setupCustomScrolling() {

        scrollView = (ObservableScrollView)findViewById(R.id.scroll_view);
        scrollView.addCallbacks(this);
        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(globalLayoutListener);
        }
    }

    @Override
    public void onResume() {
        // Temporary code. TODO: Implements the image drawing code.
        super.onResume();
        loadHeaderImage();
    }

    private void loadHeaderImage() {
        // Temporary code. TODO: Implements the image drawing code.
        showHeaderImage = true;
        headerImage.setImageDrawable(getResources().getDrawable(R.drawable.testimage));
//        boolean show = isShowHeaderImage();
//        if (show) {
//            showHeaderImage = true;
//            ImageLoader.loadImage("http://xxxxx/xx.png", headerImage, new RequestListener<String>() {
//                @Override
//                public void onException(Exception e, String url, Target target) {
//                    showHeaderImage = false;
//                    recomputeHeaderImageAndScrollingMetrics();
//                }
//                @Override
//                public void onImageReady(String url, Target target, boolean b, boolean b2) {
//                    // Trigger image transition
//                    recomputeHeaderImageAndScrollingMetrics();
//                }
//            });
//            recomputeHeaderImageAndScrollingMetrics();
//        } else {
//            showHeaderImage = false;
//            recomputeHeaderImageAndScrollingMetrics();
//        }
    }

    private int calculateActionBarSize() {
        Resources.Theme theme = this.getTheme();
        if (theme == null) {
            return 0;
        }

        TypedArray att = theme.obtainStyledAttributes(RES_IDS_ACTION_BAR_SIZE);
        if (att == null) {
            return 0;
        }

        float size = att.getDimension(0, 0);
        att.recycle();
        return (int) size;
    }

    private float getProgress(int value, int min, int max) {
        if (min == max) {
            throw new IllegalArgumentException("Max (" + max + ") cannot equal min (" + min + ")");
        }

        return (value - min) / (float) (max - min);
    }

    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?aid="+cache.getAsString("AID"));
                if (jsonObject == null)
                    return;
                    account=JSONObject.parseObject(jsonObject.getString("result"),Account.class);
                try {
                    String URL = HttpUtils.HOST2 + "/Edu/img/"+ account.getAhead();
                    image = HttpUtils.getUrlImage(URL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                mHandler.sendMessage(message);

            }
        }).start();
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows_selectphotos, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(false);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PersonalActivity.this,TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }
}
