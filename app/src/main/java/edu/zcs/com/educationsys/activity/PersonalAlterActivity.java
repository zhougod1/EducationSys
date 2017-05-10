package edu.zcs.com.educationsys.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.entity.Account;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class PersonalAlterActivity extends AppCompatActivity  implements View.OnClickListener{

    protected static final String URL = HttpUtils.HOST2 + "/Edu/Account/getById";
    protected static final String URL1 = HttpUtils.HOST2 + "/Edu/Account/update";
    private TextView personal_alter_account;
    private TextView personal_alter_name;
    private TextView personal_alter_phone;
    private TextView personal_alter_addres;
    private TextView personal_alter_sex;
    private Account account;
    private TextView personal_update;
    private TextView back;
    private Bitmap image;
    private ACache cache;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    personal_alter_name.setText(account.getAname());
                    personal_alter_addres.setText(account.getAaddress());
                    personal_alter_phone.setText(account.getAphonenumber());
                    personal_alter_account.setText(account.getAccount());
                    personal_alter_sex.setText(account.getSex());
                    break;
                case 2:
                    if((Boolean) msg.obj){
                        Toast.makeText(PersonalAlterActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(PersonalAlterActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                    }

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_alter);
        cache=ACache.get(this);
        personal_alter_account = (TextView) findViewById(R.id.personal_alter_account);
        personal_alter_name = (TextView) findViewById(R.id.personal_alter_name);
        personal_alter_addres = (TextView) findViewById(R.id.personal_alter_addres);
        personal_alter_phone = (TextView) findViewById(R.id.personal_alter_phone);
        personal_alter_sex = (TextView) findViewById(R.id.personal_alter_sex);
        personal_update =(TextView) findViewById(R.id.personal_update);
        back =(TextView) findViewById(R.id.back);
        personal_update.setOnClickListener(this);
        back.setOnClickListener(this);
        init();

    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL + "?aid=" + cache.getAsString("AID"));
                if (jsonObject == null)
                    return;
                account = JSONObject.parseObject(jsonObject.getString("result"), Account.class);
                Message message = new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_update:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> info=new HashMap<String, String>();
//                       info.put("account",personal_alter_account.getText().toString());
                        info.put("aid",cache.getAsString("AID"));
                        info.put("name",personal_alter_name.getText().toString());
                        info.put("sex",personal_alter_sex.getText().toString());
                        info.put("phone",personal_alter_phone.getText().toString());
                        info.put("address",personal_alter_addres.getText().toString());
                        JSONObject jsonObject = HttpUtils.getJsonObject(URL1,info);
                        if (jsonObject == null)
                            return;
                        boolean result = JSONObject.parseObject(jsonObject.getString("result"),java.lang.Boolean.class);
                        Message message = new Message();
                        message.obj=result;
                        message.what=2;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
