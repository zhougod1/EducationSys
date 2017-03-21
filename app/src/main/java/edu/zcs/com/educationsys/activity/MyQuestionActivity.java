package edu.zcs.com.educationsys.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.QuestionAdapter;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class MyQuestionActivity extends AppCompatActivity {
    protected static final String URL = HttpUtils.HOST2+ "/Edu/Question/queryByAid";
    private List<Map<String,Object>> date;
    private QuestionAdapter myAdapter;
    private RecyclerView question_recycleview;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myAdapter.setList(date);
            myAdapter.notifyDataSetChanged();
        }
    };
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        date =new ArrayList<Map<String, Object>>();
        myAdapter = new QuestionAdapter(this,date);
        sp = this.getSharedPreferences("account", Context.MODE_WORLD_READABLE);
        init();
        question_recycleview =(RecyclerView)findViewById(R.id.my_question_recyclerview);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        question_recycleview.setLayoutManager(manager);
        question_recycleview.setItemAnimator(new DefaultItemAnimator());
        question_recycleview.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new QuestionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {

            }
        });
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?aid="+sp.getString("AID",""));
                if (jsonObject == null)
                    return;
                date=(List<Map<String,Object>>) JSONObject.parseObject(jsonObject.getString("result"),java.util.List.class);
//                date =JSONObject.parseObject(jsonObject.getString("news"),ArrayList<Map>);
                Message message = new Message();
                mhandler.sendMessage(message);
            }
        }).start();
    }
}
