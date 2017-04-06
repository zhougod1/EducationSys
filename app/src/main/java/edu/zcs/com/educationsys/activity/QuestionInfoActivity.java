package edu.zcs.com.educationsys.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.QuestionInfoAdapter;
import edu.zcs.com.educationsys.util.tools.DateUtils;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.view.EmptyView;
import edu.zcs.com.educationsys.util.view.LoadingView;

public class QuestionInfoActivity extends AppCompatActivity{

    protected static final String URL = HttpUtils.HOST2+ "/Edu/Question/queryByQid";
    protected static final String URL2 = HttpUtils.HOST2+ "/Edu/Answer";
    private QuestionInfoAdapter questionInfoAdapter;
    private EmptyView empty;
    private LoadingView loading;
    private List<Map<String,Object>> answerdate;
    private Map<String,Object> date;
    private String[] path;
    private TextView release_answer_bt;
    private ListView question_info_listview;
    private String qid;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                path = date.get("img").toString().split(";");
                questionInfoAdapter.setDate(date);
                questionInfoAdapter.setPath(path);
                break;
                case 2:
                questionInfoAdapter.setAnswerdate(answerdate);
                questionInfoAdapter.notifyDataSetChanged();
                loading.hideLoading();
                break;
                case 3:
                    if((Boolean)msg.obj){
                        initAnswer();
                        Toast.makeText(QuestionInfoActivity.this,"回答成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(QuestionInfoActivity.this,"回答失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_info);
        empty =(EmptyView)findViewById(R.id.empty_view);
        loading=(LoadingView)findViewById(R.id.loading_view);
        loading.showLoading();
        qid=getIntent().getExtras().get("qid").toString();
        release_answer_bt= (TextView) findViewById(R.id.release_answer);
        release_answer_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopupWindows(QuestionInfoActivity.this,release_answer_bt);
            }
        });
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        questionInfoAdapter=new QuestionInfoAdapter(path,answerdate,date,this);
        question_info_listview=(ListView)findViewById(R.id.question_info_listview);
        question_info_listview.setAdapter(questionInfoAdapter);
        question_info_listview.setEmptyView(empty);
        if(!HttpUtils.isNetworkAvailable(this)) {
            loading.hideLoading();
        }
        date=new HashMap<String,Object>();
        answerdate=new ArrayList<Map<String, Object>>();
        init();
    }
    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?qid="+qid);
                if (jsonObject == null)
                    return;
                date=(Map<String,Object>)JSONObject.parseObject(jsonObject.getString("result"),java.util.HashMap.class);
//                date =JSONObject.parseObject(jsonObject.getString("news"),ArrayList<Map>);
                Message message = new Message();
                message.what=1;
                mhandler.sendMessage(message);
            }
        }).start();
        initAnswer();
    }

    public void initAnswer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject1 = HttpUtils.getJsonObject(URL2+"/getByQid?qid="+qid);
                Message message1 = new Message();
                message1.what=2;
                if (jsonObject1 == null) {
                    mhandler.sendMessage(message1);
                    return;
                }
                answerdate=(List<Map<String,Object>>)JSONObject.parseObject(jsonObject1.getString("result"),java.util.ArrayList.class);
                mhandler.sendMessage(message1);
            }
        }).start();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        initAnswer();
    }
    public class PopupWindows extends PopupWindow {
        public PopupWindows(Context mContext, View parent) {

        View view = View.inflate(mContext,R.layout.item_popupwindows_release_answer, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        LinearLayout question_answer = (LinearLayout) view
                .findViewById(R.id.question_answer);
        question_answer.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_2));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();
        Button cancel = (Button) view.findViewById(R.id.release_cancel);
        Button ensure = (Button) view.findViewById(R.id.release_ensure);
        final EditText content=(EditText)view.findViewById(R.id.item_popupwindows_content);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        ensure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> answer=new HashMap<String, String>();
                        answer.put("qid",qid);
                        answer.put("content",content.getText().toString());
                        answer.put("aid","e4d4cad75ac7e3c9015ac7e436a30000");
                        answer.put("time",new DateUtils().getDate());
                        JSONObject jsonObject = HttpUtils.getJsonObject(URL2+"/add",answer);
                        if (jsonObject == null)
                            return;
                        Boolean result=JSONObject.parseObject(jsonObject.getString("result"),java.lang.Boolean.class);
                        Message message = new Message();
                        message.what=3;
                        message.obj=result;
                        mhandler.sendMessage(message);
                    }
                }).start();
                dismiss();
            }
        });

    }
}
}
