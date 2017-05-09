package edu.zcs.com.educationsys.activity;

import android.content.SharedPreferences;
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
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String URL = HttpUtils.HOST2+ "/TestSsh/Account/login";
    private Button sigin;
    private EditText accountNum,password;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case R.id.sigin:
                    if(msg.obj.toString().equals("")||msg.obj!=null){
                        editor.putString("AID",msg.obj.toString());
                        editor.putBoolean("ISLOGIN",true);
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
//        editor=sp.edit();
//        sp = this.getSharedPreferences("account", Context.MODE_WORLD_READABLE);
        sigin =(Button)findViewById(R.id.sigin);
        accountNum =(EditText)findViewById(R.id.accountNum);
        password=(EditText)findViewById(R.id.password);
        keepNum =(CheckBox)findViewById(R.id.keep_num);
//        keepNum.setChecked(sp.getBoolean("ISCHECKED",false));
        sigin.setOnClickListener(this);
        if(keepNum.isChecked()){
//            accountNum.setText(sp.getString("ACCOUNT",""));
//            password.setText(sp.getString("PASSWORD",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sigin:
                if(keepNum.isChecked()) {
                    editor.putString("ACCOUNT",accountNum.getText().toString());
                    editor.putString("PASSWORD",password.getText().toString());
                }
                editor.putBoolean("ISCHECKED",keepNum.isChecked());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = HttpUtils.getJsonObject(URL + "?account=" + accountNum.getText() + "&password=" + password.getText());
                        if(jsonObject==null){
                            return;
                        }
                        String result =jsonObject.getString("result");
                        Message message = new Message();
                        message.what = R.id.sigin;
                        message.obj = result;
                        mhandler.sendMessage(message);
                    }
                }).start();
                break;
        }

    }
}
