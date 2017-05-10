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
import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.NewsInfoAdapter;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class NewsOrderActivity extends AppCompatActivity {

    protected static final String URL = HttpUtils.HOST2+ "/Edu/News/getByIdnTyple";
    private List<Map<String,Object>> date;
    private NewsInfoAdapter myAdapter;
    private RecyclerView recyclerView;
    private ACache cache;

    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            myAdapter.setList(date);
            myAdapter.notifyDataSetChanged();

        }
    };
    private String typle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_order);
        cache=ACache.get(this);
        date =new ArrayList<Map<String, Object>>();
        myAdapter =new NewsInfoAdapter(this, date);
        myAdapter.setOnItemClickListener(new NewsInfoAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
//                view.findViewById(R.id.point_img).setVisibility(View.GONE);
                Intent intent=new Intent(NewsOrderActivity.this,OrderInfoActivity.class);
                intent.putExtra("oid",data);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view_newsinfo);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new NewsOrderActivity.MyItem());

        typle =getIntent().getStringExtra("typle");
        init();
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?aid="+cache.getAsString("AID"));
                if (jsonObject == null)
                    return;
                date=(List<Map<String,Object>>) JSONObject.parseObject(jsonObject.getString("news"),java.util.List.class);
//                date =JSONObject.parseObject(jsonObject.getString("news"),ArrayList<Map>);
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
