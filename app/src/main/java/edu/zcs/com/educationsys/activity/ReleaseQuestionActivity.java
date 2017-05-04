package edu.zcs.com.educationsys.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.GridAdapter;
import edu.zcs.com.educationsys.util.tools.BitmapUtils;
import edu.zcs.com.educationsys.util.tools.FileUtils;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class ReleaseQuestionActivity extends AppCompatActivity{

    protected static final String URL = HttpUtils.HOST2+ "/Edu/Photo/upload";
    protected static final String URL1 = HttpUtils.HOST2+ "/Edu/Question/add";
    private GridView release_question_gridview;
    private EditText release_question_title;
    private EditText release_question_content;
    private Spinner release_question_course;
    private GridAdapter rqApapter;
    private Button submit;
    private String result;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    rqApapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(ReleaseQuestionActivity.this,result,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_question);
        release_question_title = (EditText)findViewById(R.id.release_question_title);
        release_question_content = (EditText)findViewById(R.id.release_question_content);
        release_question_course = (Spinner)findViewById(R.id.release_question_course);
        release_question_course.setAdapter(new ArrayAdapter<String>(this,
                R.layout.sprinner_textview_layout,R.id.sprinner_text,getResources().getStringArray(R.array.course)));
        submit= (Button) findViewById(R.id.question_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                String imgname="e4d4cad75ac7e5ea015ac7eddef70001"+System.currentTimeMillis();
                String images="";
                result= HttpUtils.uploadFile(BitmapUtils.drr,URL,imgname);
                        if(Boolean.parseBoolean(result)) {
                            for (int i = 0; i < BitmapUtils.drr.size(); i++) {
                                images += "MYQUESTIONIMG_" + imgname + i+".jpg";
                                if (i != BitmapUtils.drr.size() - 1) {
                                    images += ";";
                                }
                            }
                            Map<String, String> my = new HashMap<>();
                            my.put("aid", "e4d4cad75ac7e5ea015ac7eddef70001");
                            my.put("content", release_question_content.getText().toString());
                            my.put("title", release_question_title.getText().toString());
                            my.put("time", String.valueOf(System.currentTimeMillis()));
                            my.put("course", release_question_course.getSelectedItem().toString());
                            my.put("img", images);
//                UpdateLoadUtils uploadUtil = UpdateLoadUtils.getInstance();
//                uploadUtil.uploadFile(BitmapUtils.drr.get(0),"img",URL,my);

                            JSONObject jsonObject = HttpUtils.getJsonObject(URL1, my);
                            if(jsonObject==null)
                                return;
                            result="发布成功";
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                        }else{
                            result="发布失败";
                        }
                    }
                }).start();
            }
        });
        release_question_gridview =(GridView)findViewById(R.id.release_question_gridview);
        release_question_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        rqApapter = new GridAdapter(this);
        init();
        release_question_gridview.setAdapter(rqApapter);
        release_question_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == BitmapUtils.bmp.size()) {
                    new PopupWindows(ReleaseQuestionActivity.this, release_question_gridview);
                } else {
                    Intent intent = new Intent(ReleaseQuestionActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });

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
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(ReleaseQuestionActivity.this,
                            TestPicActivity.class);
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
    @Override
    protected void onRestart() {
        init();
        super.onRestart();

    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Environment.getExternalStorageDirectory()手机本地存储
        File file = new File(Environment.getExternalStorageDirectory()
                + "/myimage/", String.valueOf(System.currentTimeMillis())
                + ".jpg");
        path = file.getPath();
        //Environment.getExternalStorageState()   SD卡是否挂载
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (BitmapUtils.drr.size() < 9 && resultCode == -1) {
                    BitmapUtils.drr.add(path);
                }
                break;
        }
    }

    public void init() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (BitmapUtils.max == BitmapUtils.drr.size()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    } else {
                        try {
                            String path = BitmapUtils.drr.get(BitmapUtils.max);
                            System.out.println(path);
                            Bitmap bm = BitmapUtils.revitionImageSize(path);
                            BitmapUtils.bmp.add(bm);
                            String newStr = path.substring(
                                    path.lastIndexOf("/") + 1,
                                    path.lastIndexOf("."));
                            FileUtils.saveBitmap(bm, "" + newStr);
                            BitmapUtils.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }


}
