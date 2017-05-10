package edu.zcs.com.educationsys.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String URL = HttpUtils.HOST2+ "/Edu/Account/login";
    private Button sigin;
    private EditText accountNum,password;
    private ACache cache;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case R.id.sigin:
                    if(!msg.obj.toString().equals("") && msg.obj!=null){
                        cache.put("AID",msg.obj.toString());
                        cache.put("ISLOGIN","true");
                        if (keepNum.isChecked()) {
                            cache.put("ACCOUNT", accountNum.getText().toString().trim());
                            cache.put("PASSWORD", password.getText().toString().trim());
                        }
                        Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(LoginActivity.this,"登入失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private CheckBox keepNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }
    public void init() {
        cache=ACache.get(this);
        sigin =(Button)findViewById(R.id.sigin);
        accountNum =(EditText)findViewById(R.id.accountNum);
        password=(EditText)findViewById(R.id.password);
        keepNum =(CheckBox)findViewById(R.id.keep_num);
        keepNum.setChecked(Boolean.parseBoolean(cache.getAsString("ISCHECKED")));
        sigin.setOnClickListener(this);
        if(keepNum.isChecked()){
            accountNum.setText(cache.getAsString("ACCOUNT"));
            password.setText(cache.getAsString("PASSWORD"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sigin:
                if(accountNum.getText().toString().trim().equals("")||password.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                }else {
                    cache.put("ISCHECKED", String.valueOf(keepNum.isChecked()));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = HttpUtils.getJsonObject(URL +"?account=" + accountNum.getText() + "&password=" + password.getText());
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
                break;
        }

    }
}
