package edu.zcs.com.educationsys.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.OrderAdapter;
import edu.zcs.com.educationsys.util.entity.Order;
import edu.zcs.com.educationsys.util.tools.OrderArray;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class RecyclerviewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    protected static final String URL = HttpUtils.HOST2+ "/Edu/Order/queryAll";

    private RecyclerView recyclerView;
    private OrderAdapter myAdapter;
    private ArrayList<Order> data;

    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            myAdapter.setList(data);
            myAdapter.notifyDataSetChanged();

        }
    };
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        data =new ArrayList<Order>();
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter = new OrderAdapter(this,data);
        myAdapter.setOnItemClickListener(new OrderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent=new Intent(RecyclerviewActivity.this,OrderInfoActivity.class);
                intent.putExtra("oid",data);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new MyItem());
        init();
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL);
                if (jsonObject == null)
                    return;
                data =JSONObject.parseObject(jsonObject.getString("order"), OrderArray.class);

                Message message = new Message();
                myhandler.sendMessage(message);

            }
        }).start();

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
                Toast.makeText(RecyclerviewActivity.this, "Refresh Finished!", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    public class MyItem extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != 0)
            outRect.top = 8;
            outRect.left = 8;
            outRect.right = 8;
        }
    }
}
