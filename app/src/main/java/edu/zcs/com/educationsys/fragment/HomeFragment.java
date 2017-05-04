package edu.zcs.com.educationsys.fragment;

import android.app.Fragment;
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
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.HomeAdapter;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    protected final static String URL=HttpUtils.HOST2;
    private ListView home_listview;
    private List<Map<String, Object>> list;
    private HomeAdapter home_adapter;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home_fragment,container,false);
        list=new ArrayList<Map<String, Object>>();
        home_listview =(ListView)view.findViewById(R.id.home_listview);
        home_adapter =new HomeAdapter(getActivity(), list);
        home_listview.setAdapter(home_adapter);

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
        return view;
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?aid="+"e4d4c8ff5a74670e015a7467b2360000");
                if (jsonObject == null)
                    return;
//                cache.put("bill_list",jsonObject.getString("result"));result
                list =(List<Map<String,Object>>)JSONObject.parseObject(jsonObject.getString("result"),java.util.List.class);
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
