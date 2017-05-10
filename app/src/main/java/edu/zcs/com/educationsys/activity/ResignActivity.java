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

import com.alibaba.fastjson.JSONObject;

import edu.zcs.com.educationsys.R;
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
    private Button resign_ensure;
    private Button resign_cancel;
    private boolean num_result;
    private boolean pw_result;

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    break;
                case 2:
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
        resign_edit_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = HttpUtils.getJsonObject(URL2+"?num="+resign_edit_num.getText().toString().trim());
                            if (jsonObject == null) {
                                return;
                            }
                            num_result = JSONObject.parseObject(jsonObject.getString("result"),Boolean.class);
                            Message message = new Message();
                            message.what = 1;
                            mhandler.sendMessage(message);
                        }
                    }).start();
                }
            }
        });
        resign_edit_account = (EditText) findViewById(R.id.resign_edit_account);
        resign_edit_password = (EditText) findViewById(R.id.resign_edit_password);
        resign_edit_password.setOnFocusChangeListener(this);
        resign_edit_again = (EditText) findViewById(R.id.resign_edit_again);
        resign_edit_again.setOnFocusChangeListener(this);
        resign_again_hint = (TextView) findViewById(R.id.resign_again_hint);
        resign_ensure = (Button) findViewById(R.id.resign_ensure);
        resign_cancel = (Button) findViewById(R.id.resign_cancel);
        resign_ensure.setOnClickListener(this);
        resign_cancel.setOnClickListener(this);

    }

    void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL);
                if (jsonObject == null) {
                    return;
                }
                String result = jsonObject.getString("result");
                Message message = new Message();
                message.what = R.id.sigin;
                message.obj = result;
                mhandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resign_ensure:
                if(num_result && pw_result){

                }else{

                }
                break;
            case R.id.resign_cancel:
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
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        return false;
    }
}
