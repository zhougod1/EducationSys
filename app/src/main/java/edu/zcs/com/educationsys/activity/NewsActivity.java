package edu.zcs.com.educationsys.activity;

import android.content.Intent;
import android.graphics.Rect;
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

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.NewsAdapter;
import edu.zcs.com.educationsys.util.entity.News;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.NewsArray;


public class NewsActivity extends AppCompatActivity {

    protected static final String URL = HttpUtils.HOST2+ "/Edu/News/queryByTyple";

    private ArrayList<News> date;
    private NewsAdapter myAdapter;
    private RecyclerView recyclerView;

    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            myAdapter.setList(date);
            myAdapter.notifyDataSetChanged();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        date =new ArrayList<News>();
        myAdapter =new NewsAdapter(this,date);
        myAdapter.setOnItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                view.findViewById(R.id.point_img).setVisibility(View.GONE);
                switch (data){
                    case "订单助手":
                        Intent intent=new Intent(NewsActivity.this,NewsOrderActivity.class);
                        intent.putExtra("typle",data);
                        startActivity(intent);
                        break;
                    case "问题助手":
                        break;
                }

            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view_news);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new MyItem());
        init();
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?aid="+"e4d4c8ff5a74670e015a7467b2360000");
                if (jsonObject == null)
                    return;
                date =JSONObject.parseObject(jsonObject.getString("news"), NewsArray.class);
                Message message = new Message();
                myhandler.sendMessage(message);

            }
        }).start();
    }

    public class MyItem extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != 0) {

            }
        }
    }
}
