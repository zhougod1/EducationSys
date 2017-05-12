package edu.zcs.com.educationsys.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.EditUtils;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class ResignActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener,View.OnTouchListener {

    protected static final String URL = HttpUtils.HOST2 + "/Edu/Account/add";
    protected static final String URL2 = HttpUtils.HOST2 + "/Edu/Account/queryByNum";
    private RelativeLayout resign_layout;
    private EditText resign_edit_num;
    private EditText resign_edit_account;
    private EditText resign_edit_password;
    private EditText resign_edit_again;
    private TextView resign_again_hint;
    private TextView resign_account_hint;
    private Button resign_ensure;
    private Button resign_cancel;
    private boolean num_result;
    private boolean pw_result;
    private String last_num_input="";

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if((boolean)msg.obj){
                        Toast.makeText(ResignActivity.this,"恭喜你注册成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(ResignActivity.this,"注册失败，请稍后重试",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    last_num_input=resign_edit_num.getText().toString().trim();
                    if(!num_result){
                        resign_account_hint.setVisibility(View.VISIBLE);
                    }else{
                        resign_account_hint.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    Toast.makeText(ResignActivity.this,"当前网络不佳无法提交",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(ResignActivity.this,"当前网络不佳无法检测你的账号",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resign);
        resign_layout= (RelativeLayout) findViewById(R.id.resign_layout);
        resign_layout.setOnTouchListener(this);
        resign_edit_num = (EditText) findViewById(R.id.resign_edit_num);
        EditUtils.set(resign_edit_num,EditUtils.ACCOUNT,"账号能由数字和字母组成");
        resign_edit_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!last_num_input.equals(resign_edit_num.getText().toString().trim()) && !resign_edit_num.getText().toString().trim().equals("")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = HttpUtils.getJsonObject(URL2 + "?num=" + resign_edit_num.getText().toString().trim());
                                Message message = new Message();
                                if (jsonObject == null) {
                                    num_result = false;
                                    message.what = 4;
                                    mhandler.sendMessage(message);
                                    return;
                                }
                                num_result = JSONObject.parseObject(jsonObject.getString("result"), Boolean.class);
                                message.what = 2;
                                mhandler.sendMessage(message);
                            }
                        }).start();
                    }
                }

            }
        });
        resign_edit_account = (EditText) findViewById(R.id.resign_edit_account);
        EditUtils.set(resign_edit_account,EditUtils.NAME,"昵称不能包含特殊字符");
        resign_edit_password = (EditText) findViewById(R.id.resign_edit_password);
        EditUtils.set(resign_edit_password,EditUtils.PASSWORD,"密码设置无法输入特殊字符");
        resign_edit_password.setOnFocusChangeListener(this);
        resign_edit_again = (EditText) findViewById(R.id.resign_edit_again);
        EditUtils.set(resign_edit_again,EditUtils.PASSWORD,"密码设置无法输入特殊字符");
        resign_edit_again.setOnFocusChangeListener(this);
        resign_again_hint = (TextView) findViewById(R.id.resign_again_hint);
        resign_account_hint= (TextView) findViewById(R.id.resign_account_hint);
        resign_ensure = (Button) findViewById(R.id.resign_ensure);
        resign_cancel = (Button) findViewById(R.id.resign_cancel);
        resign_ensure.setOnClickListener(this);
        resign_cancel.setOnClickListener(this);

    }

    void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> my=new HashMap<String, String>();
                my.put("account",resign_edit_account.getText().toString().trim());
                my.put("password",resign_edit_password.getText().toString().trim());
                my.put("num",resign_edit_num.getText().toString().trim());
                Message message = new Message();
                JSONObject jsonObject = HttpUtils.getJsonObject(URL,my);
                if (jsonObject == null) {
                    message.what = 3;
                    mhandler.sendMessage(message);
                    return;
                }
                String result = jsonObject.getString("result");
                message.what = 1;
                message.obj = result;
                mhandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resign_ensure:
                lostFocus(v);
                if(!resign_edit_account.getText().toString().trim().equals("")
                        && !resign_edit_password.getText().toString().trim().equals("")
                        && !resign_edit_again.getText().toString().trim().equals("")
                        && !resign_edit_num.getText().toString().trim().equals("")){
                    if(num_result && pw_result){
                        init();
                    }else{
                        Toast.makeText(this,"根据提示修改你的填写信息",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"请填写完整后再提交",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.resign_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (!hasFocus) {
            if (!resign_edit_again.getText().toString().trim().equals(resign_edit_password.getText().toString().trim())) {
                resign_again_hint.setVisibility(View.VISIBLE);
                pw_result =false;
            } else {
                resign_again_hint.setVisibility(View.GONE);
                pw_result =true;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        lostFocus(v);
        return false;
    }

    public void lostFocus(View v){
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
