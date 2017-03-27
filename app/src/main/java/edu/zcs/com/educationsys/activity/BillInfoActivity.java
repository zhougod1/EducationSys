package edu.zcs.com.educationsys.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class BillInfoActivity extends AppCompatActivity  implements View.OnClickListener{

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
    private Map<String,Object> list;
    private ACache cache;
    private int position;
    private boolean result=false;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(result){
                Toast.makeText(BillInfoActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(BillInfoActivity.this,"回复失败",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        position = Integer.parseInt(getIntent().getExtras().get("position").toString());
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
        cache = ACache.get(this);
        list=((List<Map<String,Object>>) JSONObject.parseObject(cache.getAsString("bill_list"),java.util.List.class)).get(position);
        bill_info_title.setText(list.get("otitle").toString());
        bill_info_pay.setText("￥"+list.get("pay"));
        bill_info_cycle.setText(list.get("ocycle").toString());
        bill_info_static.setText(list.get("static").toString());
        bill_info_course.setText(list.get("ocourse").toString());
        bill_info_name.setText(list.get("sname").toString());
        bill_info_area.setText(list.get("oarea").toString());
        bill_info_address.setText(list.get("oaddress").toString());
        bill_info_phone.setText(list.get("aphone").toString());
        bill_info_account.setText(list.get("pname").toString());
        bill_info_aphone.setText(list.get("pphone").toString());
        bill_info_message.setText(list.get("message").toString());
        bill_info_source.setOnClickListener(this);
        bill_info_controller=(LinearLayout)findViewById(R.id.bill_info_controller);
        bill_info_cancel.setOnClickListener(this);
        bill_info_ensure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bill_info_source:
                break;
            case R.id.bill_info_cancel:
                update("已拒绝");
                break;
            case R.id.bill_info_ensure:
                update("已同意");
                break;
        }

    }

    public void update(final String ostatic){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject=HttpUtils.getJsonObject(URL+"/update?bid="+list.get("b_id")+"&static="+ostatic);
                if(jsonObject==null)
                    return;
                result=JSONObject.parseObject(jsonObject.getString("result"),java.lang.Boolean.class);
                Message message=new Message();
                mhandler.sendMessage(message);
            }
        }).start();
    }
}
