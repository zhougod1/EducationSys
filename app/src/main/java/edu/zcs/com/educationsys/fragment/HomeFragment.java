package edu.zcs.com.educationsys.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.activity.OrderInfoActivity;
import edu.zcs.com.educationsys.adapter.HomeAdapter;
import edu.zcs.com.educationsys.util.entity.Order;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.OrderArray;
import edu.zcs.com.educationsys.util.view.EmptyView;
import edu.zcs.com.educationsys.util.view.LoadingView;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    protected static final String URL = HttpUtils.HOST2 + "/Edu/Order/queryAll";
    private ListView home_listview;
    private List<Order> list;
    private EmptyView empty;
    private LoadingView loading;
    private HomeAdapter home_adapter;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            home_adapter.setList(list);
            home_adapter.notifyDataSetChanged();
            loading.hideLoading();
            swipeRefreshLayout.setRefreshing(false);
        }
    };
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home_fragment,container,false);
        empty = (EmptyView)view.findViewById(R.id.empty_view);
        loading = (LoadingView)view.findViewById(R.id.loading_view);
        list=new ArrayList<Order>();
        home_listview =(ListView)view.findViewById(R.id.home_listview);
        home_adapter =new HomeAdapter(getActivity(), list);
        home_listview.setAdapter(home_adapter);
        if (!HttpUtils.isNetworkAvailable(getActivity())) {
            loading.hideLoading();
        }
        home_listview.setEmptyView(empty);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_container);
        home_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (view == null || view.getChildCount() == 0) ? 0 : view.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }

        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        home_adapter.setOnItemClickListener(new HomeAdapter.OnListViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent(getActivity(), OrderInfoActivity.class);
                intent.putExtra("oid", data);
                startActivity(intent);
            }
        });
        init();
        return view;
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL);
                if (jsonObject == null)
                    return;
                list = JSONObject.parseObject(jsonObject.getString("order"), OrderArray.class);

                Message message = new Message();
                mhandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onRefresh() {
        if (HttpUtils.isNetworkAvailable(getActivity())) {
            init();
            Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "暂无网络", Toast.LENGTH_SHORT).show();
        }
    }
}
