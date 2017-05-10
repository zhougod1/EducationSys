package edu.zcs.com.educationsys.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wheelview.library.dialog.MyWheelDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String URL = HttpUtils.HOST2+ "/Edu/Order/add";
    private Button release_order_cancel;
    private Button release_order_ensure;
    private EditText release_order_address;
    private EditText release_order_phone;
    private EditText release_order_title;
    private EditText release_order_name;
    private EditText release_order_info;
    private TextView release_order_cycle;
    private TextView release_order_pay;
    private TextView release_order_coures;
    private TextView release_order_level;
    private TextView release_order_area;
    private RelativeLayout area_layout;
    private RelativeLayout level_layout;
    private RelativeLayout coures_layout;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> course;
    private String pay_number;
    private String pay_time;
    private boolean result=false;
    private ACache cache;

    Handler myhandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(result){
                Toast.makeText(ReleaseActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(ReleaseActivity.this,"发布失败，稍后请重试！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        initView();
    }

    public void initView(){
        course=new ArrayList<String>();
        cache=ACache.get(this);
        release_order_address=(EditText)findViewById(R.id.release_order_address);
        release_order_phone= (EditText) findViewById(R.id.release_order_phone);
        release_order_title= (EditText) findViewById(R.id.release_order_title);
        release_order_name= (EditText) findViewById(R.id.release_order_name);
        release_order_info= (EditText) findViewById(R.id.release_order_info);
        release_order_cycle= (TextView) findViewById(R.id.release_order_cycle);
        release_order_pay= (TextView) findViewById(R.id.release_order_pay);
        release_order_coures= (TextView) findViewById(R.id.release_order_coures);
        release_order_level= (TextView) findViewById(R.id.release_order_level);
        release_order_area= (TextView) findViewById(R.id.release_order_area);
        area_layout= (RelativeLayout) findViewById(R.id.area_layout);
        level_layout= (RelativeLayout) findViewById(R.id.level_layout);
        coures_layout= (RelativeLayout) findViewById(R.id.coures_layout);
        release_order_cancel= (Button) findViewById(R.id.release_order_cancel);
        release_order_ensure= (Button) findViewById(R.id.release_order_ensure);
        area_layout.setOnClickListener(this);
        release_order_cancel.setOnClickListener(this);
        release_order_ensure.setOnClickListener(this);
        release_order_pay.setOnClickListener(this);
        release_order_cycle.setOnClickListener(this);
        level_layout.setOnClickListener(this);
        coures_layout.setOnClickListener(this);
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> release = new HashMap<>();
                release.put("aid", cache.getAsString("AID"));
                release.put("title", release_order_title.getText().toString());
                release.put("address", release_order_address.getText().toString());
                release.put("phone", release_order_phone.getText().toString());
                release.put("time", pay_time);
                release.put("pay", pay_number);
                release.put("info", release_order_info.getText().toString());
                release.put("cycle", release_order_cycle.getText().toString());
                release.put("level", release_order_level.getText().toString());
                release.put("area", release_order_area.getText().toString());
                release.put("course", release_order_coures.getText().toString());
                JSONObject jsonObject = HttpUtils.getJsonObject(URL,release);
                if(jsonObject==null){
                    Message message = new Message();
                    myhandle.sendMessage(message);
                    return;
                }
                result = JSONObject.parseObject(jsonObject.getString("result"),java.lang.Boolean.class);
                Message message = new Message();
                myhandle.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.release_order_pay:
                DialogUIUtils.showAlertInput(this, "请输入薪酬", "薪资", "计时单位（小时、日、周、月）", "确定", "取消",  new DialogUIListener() {
                    @Override
                    public void onPositive() {
                    }

                    @Override
                    public void onNegative() {
                    }

                    @Override
                    public void onGetInput(CharSequence input1, CharSequence input2) {
                        if(!input1.toString().trim().equals("") && !input2.toString().trim().equals("")) {
                            pay_number=input1.toString();
                            pay_time=input2.toString();
                            release_order_pay.setText("￥" + input1 + "/" + input2);
                        }
                    }
                }).show();
            break;
            case R.id.release_order_cycle:
                DialogUIUtils.showBottomSheetAndCancel(this, Arrays.asList(getResources().getStringArray(R.array.cycle)), "取消", new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        release_order_cycle.setText(text);
                    }
                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
                break;
            case R.id.level_layout:
                DialogUIUtils.showBottomSheetAndCancel(this, Arrays.asList(getResources().getStringArray(R.array.level)), "取消", new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        release_order_level.setText(text);
                    }
                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
              break;
            case R.id.coures_layout:
                DialogUIUtils.showBottomSheetAndCancel(this, Arrays.asList(getResources().getStringArray(R.array.course)), "取消", new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        release_order_coures.setText(text);
                    }
                    @Override
                    public void onBottomBtnClick() {
                    }
                }).show();
                break;
            case R.id.release_order_cancel:
                finish();
                break;
            case R.id.release_order_ensure:

                if (release_order_address.getText().toString().trim().equals("")||
                    release_order_phone.getText().toString().trim().equals("")||
                    release_order_title.getText().toString().trim().equals("")||
                    release_order_cycle.getText().toString().trim().equals("")||
                    release_order_pay.getText().toString().trim().equals("")||
                    release_order_coures.getText().toString().trim().equals("")||
                    release_order_level.getText().toString().trim().equals("")||
                    release_order_area.getText().toString().trim().equals("")) {
                    DialogUIUtils.showAlert(this, "注意", "（必填）的条目都需填写完整！", new DialogUIListener() {
                        @Override
                        public void onPositive() {
                        }

                        @Override
                        public void onNegative() {
                        }

                    }).show();
                }else{
                    init();
                }
                break;
            case R.id.area_layout:
                MyWheelDialog mDialog=new MyWheelDialog(this, new MyWheelDialog.OnWheelClickLitener() {
                    @Override
                    public void onOKClick(String s, String s1, String s2, String s3, String s4, String s5) {
                        release_order_area.setText(s2+" "+s4);
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
                mDialog.show();
                break;
        }
    }
}
