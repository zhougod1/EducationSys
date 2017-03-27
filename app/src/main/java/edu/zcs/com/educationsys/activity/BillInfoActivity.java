package edu.zcs.com.educationsys.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class BillInfoActivity extends AppCompatActivity {

     protected final static String URL= HttpUtils.HOST2+"/Edu/Bill";
    private TextView bill_info_title;
    private TextView bill_info_time;
    private TextView bill_info_cycle;
    private TextView bill_info_static;
    private TextView bill_info_course;
    private TextView bill_info_name;
    private TextView bill_info_area;
    private TextView bill_info_address;
    private TextView bill_info_pay;
    private TextView bill_info_phone;
    private TextView bill_info_account;
    private TextView bill_info_aphone;
    private TextView bill_info_message;
    private TextView bill_info_source;
    private Button bill_info_cancel;
    private Button bill_info_ensure;
    private LinearLayout bill_info_controller;
    private List<Map<String,Object>> list;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        bill_info_title =(TextView)findViewById(R.id.bill_info_title);
        bill_info_pay =(TextView)findViewById(R.id.bill_info_pay);
        bill_info_time =(TextView)findViewById(R.id.bill_info_time);
        bill_info_cycle =(TextView)findViewById(R.id.bill_info_cycle);
        bill_info_static =(TextView)findViewById(R.id.bill_info_static);
        bill_info_course =(TextView)findViewById(R.id.bill_info_course);
        bill_info_name =(TextView)findViewById(R.id.bill_info_name);
        bill_info_area =(TextView)findViewById(R.id.bill_info_area);
        bill_info_address =(TextView)findViewById(R.id.bill_info_address);
        bill_info_phone =(TextView)findViewById(R.id.bill_info_phone);
        bill_info_account =(TextView)findViewById(R.id.bill_info_account);
        bill_info_aphone =(TextView)findViewById(R.id.bill_info_aphone);
        bill_info_message =(TextView)findViewById(R.id.bill_info_message);
        bill_info_source =(TextView)findViewById(R.id.bill_info_source);
        bill_info_controller=(LinearLayout)findViewById(R.id.bill_info_controller);
        bill_info_cancel =(Button)findViewById(R.id.bill_info_cancel);
        bill_info_ensure=(Button)findViewById(R.id.bill_info_ensure);

    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"/queryByBid");
                if (jsonObject == null)
                    return;
                list =(List<Map<String,Object>>)JSONObject.parseObject(jsonObject.getString("account"), java.util.ArrayList.class);
                Message message=new Message();
                mhandler.sendMessage(message);
            }
        }).start();
    }

}
