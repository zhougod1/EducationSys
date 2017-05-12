package edu.zcs.com.educationsys.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.wheelview.library.dialog.MyWheelDialog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.entity.Account;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class PersonalAlterActivity extends AppCompatActivity  implements View.OnClickListener{

    protected static final String URL = HttpUtils.HOST2 + "/Edu/Account/getById";
    protected static final String URL1 = HttpUtils.HOST2 + "/Edu/Account/update";
    private EditText personal_alter_account;
    private EditText personal_alter_name;
    private EditText personal_alter_phone;
    private EditText personal_alter_addres;
    private TextView personal_alter_sex;
    private TextView personal_alter_area;
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
                    personal_alter_area.setText(account.getArea());
                    break;
                case 2:
                    if((Boolean) msg.obj){
                        Toast.makeText(PersonalAlterActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(PersonalAlterActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    Toast.makeText(PersonalAlterActivity.this,"保存失败，请检查网络后重试",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_alter);
        cache=ACache.get(this);
        personal_alter_account = (EditText) findViewById(R.id.personal_alter_account);
        personal_alter_name = (EditText) findViewById(R.id.personal_alter_name);
        personal_alter_addres = (EditText) findViewById(R.id.personal_alter_addres);
        personal_alter_phone = (EditText) findViewById(R.id.personal_alter_phone);
        personal_alter_sex = (TextView) findViewById(R.id.personal_alter_sex);
        personal_alter_area = (TextView) findViewById(R.id.personal_alter_area);
        personal_alter_sex.setOnClickListener(this);
        personal_alter_area.setOnClickListener(this);
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
                        info.put("aid",cache.getAsString("AID"));
                        info.put("account",personal_alter_account.getText().toString().trim());
                        info.put("name",personal_alter_name.getText().toString().trim());
                        info.put("sex",personal_alter_sex.getText().toString().trim());
                        info.put("phone",personal_alter_phone.getText().toString().trim());
                        info.put("address",personal_alter_addres.getText().toString().trim());
                        info.put("area",personal_alter_area.getText().toString().trim());
                        Message message = new Message();
                        JSONObject jsonObject = HttpUtils.getJsonObject(URL1,info);
                        if (jsonObject == null) {
                            message.what = 3;
                            handler.sendMessage(message);
                            return;
                        }
                        boolean result = JSONObject.parseObject(jsonObject.getString("result"),java.lang.Boolean.class);
                        message.obj=result;
                        message.what=2;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.personal_alter_sex:
                DialogUIUtils.showBottomSheetAndCancel(this, Arrays.asList(getResources().getStringArray(R.array.sex)), "取消", new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        personal_alter_sex.setText(text);
                    }
                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
                break;
            case R.id.personal_alter_area:
                MyWheelDialog mDialog=new MyWheelDialog(this, new MyWheelDialog.OnWheelClickLitener() {
                    @Override
                    public void onOKClick(String s, String s1, String s2, String s3, String s4, String s5) {
                        personal_alter_area.setText(s2+" "+s4);
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
