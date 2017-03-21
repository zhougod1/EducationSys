package edu.zcs.com.educationsys.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.entity.Account;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class PersonalAlterActivity extends AppCompatActivity {

    protected static final String URL = HttpUtils.HOST2 + "/Edu/Account/getById";
    private TextView personal_alter_account;
    private TextView personal_alter_name;
    private TextView personal_alter_phone;
    private TextView personal_alter_addres;
    private TextView personal_alter_sex;
    private Account account;
    private Bitmap image;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            personal_alter_name.setText(account.getAname());
            personal_alter_addres.setText(account.getAaddress());
            personal_alter_phone.setText(account.getAphonenumber());
            personal_alter_account.setText(account.getAccount());
            personal_alter_sex.setText(account.getSex());
        }
    };
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_alter);
        sp =this.getSharedPreferences("account", Context.MODE_WORLD_READABLE);
        personal_alter_account = (TextView) findViewById(R.id.personal_alter_account);
        personal_alter_name = (TextView) findViewById(R.id.personal_alter_name);
        personal_alter_addres = (TextView) findViewById(R.id.personal_alter_addres);
        personal_alter_phone = (TextView) findViewById(R.id.personal_alter_phone);
        personal_alter_sex = (TextView) findViewById(R.id.personal_alter_sex);
        init();

    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL + "?aid=" + "e4d4c8ff5a74670e015a7467b2360000");
                if (jsonObject == null)
                    return;
                account = JSONObject.parseObject(jsonObject.getString("result"), Account.class);
                Message message = new Message();
                handler.sendMessage(message);
            }
        }).start();
    }
}
