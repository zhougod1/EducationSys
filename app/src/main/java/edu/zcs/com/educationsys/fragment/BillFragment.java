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

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.BillAdapter;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.view.EmptyView;
import edu.zcs.com.educationsys.util.view.LoadingView;

public class BillFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected final static String URL=HttpUtils.HOST2+"/Bill/queryByAid";
    private List<Map<String,Object>> date;
    private LoadingView loading;
    private EmptyView empty;
    private ListView bill_listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BillAdapter myAdapter;
    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myAdapter.setList(date);
            myAdapter.notifyDataSetChanged();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_bill_fragment, container, false);
        empty = (EmptyView) view.findViewById(R.id.empty_view);
        loading = (LoadingView) view.findViewById(R.id.loading_view);
        myAdapter =new BillAdapter(date,getActivity());
        if(!HttpUtils.isNetworkAvailable(getActivity())) {
            loading.hideLoading();
        }
        date = new ArrayList<Map<String, Object>>();
        bill_listview =(ListView)view.findViewById(R.id.bill_listview);
        bill_listview.setEmptyView(empty);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.bill_swipe_container);
        bill_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    void init(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL);
                if (jsonObject == null)
                    return;
                date =(List<Map<String,Object>>)JSONObject.parseObject(jsonObject.getString("result"),java.util.List.class);

                Message message = new Message();
                mhandler.sendMessage(message);

            }
        }).start();
    }

    @Override
    public void onRefresh() {
        init();
    }
}
