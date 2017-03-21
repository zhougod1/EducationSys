package edu.zcs.com.educationsys.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.activity.QuestionInfoActivity;
import edu.zcs.com.educationsys.adapter.QuestionAdapter;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.view.EmptyView;
import edu.zcs.com.educationsys.util.view.LoadingView;
import edu.zcs.com.educationsys.util.view.RecyclerViewEmptySupport;

public class QuestionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected static final String URL = HttpUtils.HOST2+ "/Edu/Question/queryAll";
    private List<Map<String,Object>> date;
    private QuestionAdapter myAdapter;
    private RecyclerViewEmptySupport question_recycleview;
    private EmptyView empty;
    private LoadingView loading;


    private SwipeRefreshLayout swipeRefreshLayout;
    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    myAdapter.setList(date);
                    myAdapter.notifyDataSetChanged();
                    loading.hideLoading();
                    break;
            }

        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_question_fragment,container,false);
        question_recycleview = (RecyclerViewEmptySupport) view.findViewById(R.id.question_recycleview);
        empty =(EmptyView)view.findViewById(R.id.empty_view);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        loading=(LoadingView) view.findViewById(R.id.loading_view);
        if(!HttpUtils.isNetworkAvailable(getActivity())) {
            loading.hideLoading();
        }
        date = new ArrayList<Map<String, Object>>();
            myAdapter = new QuestionAdapter(getActivity(), date);
            init();
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            question_recycleview.setLayoutManager(manager);
            question_recycleview.setItemAnimator(new DefaultItemAnimator());
            question_recycleview.setAdapter(myAdapter);
            question_recycleview.setEmptyView(empty);
            question_recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
            swipeRefreshLayout =(SwipeRefreshLayout) view.findViewById(R.id.swipe_container_question);
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            swipeRefreshLayout.setOnRefreshListener(this);
            myAdapter.setOnItemClickListener(new QuestionAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, String data) {
                    Intent i = new Intent(getActivity(), QuestionInfoActivity.class);
                    i.putExtra("qid", data);
                    startActivity(i);
                }
            });
            return view;

    }

    public static QuestionFragment newInstance() {
        
        Bundle args = new Bundle();
        
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void init(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = HttpUtils.getJsonObject(URL);
                    if (jsonObject == null)
                        return;
                    date = (List<Map<String, Object>>) JSONObject.parseObject(jsonObject.getString("result"), java.util.List.class);
//                date =JSONObject.parseObject(jsonObject.getString("news"),ArrayList<Map>);
                    Message message = new Message();
                    message.what=1;
                    mhandler.sendMessage(message);
                }
            }).start();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
                if(HttpUtils.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "暂无网络", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
