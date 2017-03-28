package edu.zcs.com.educationsys.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.fragment.BillFragment;
import edu.zcs.com.educationsys.fragment.OrderFragment;
import edu.zcs.com.educationsys.fragment.QuestionFragment;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    protected final static String URL=HttpUtils.HOST2+"/Edu/Bill/queryByAid";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentManager myfragmentManager;
    private BillFragment home;
    private OrderFragment order;
    private QuestionFragment question;
    private ImageView img1, img2, img3;
    private TextView text_1, text_2, text_3;
    private LinearLayout tab1, tab2, tab3;
    private SharedPreferences sp;
    private List<Map<String,Object>> date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = this.getSharedPreferences("account", Context.MODE_WORLD_READABLE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        setTabSelection(0);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void init() {
        date = new ArrayList<Map<String, Object>>();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        myfragmentManager = getFragmentManager();
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        text_1 = (TextView) findViewById(R.id.text_1);
        text_2 = (TextView) findViewById(R.id.text_2);
        text_3 = (TextView) findViewById(R.id.text_3);
        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab3 = (LinearLayout) findViewById(R.id.tab3);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);

    }

    private void setTabSelection(int index) {
        //图片初始化
        imageInit();
        //开启fragment事务
        FragmentTransaction transaction = myfragmentManager.beginTransaction();
        //判断当前页面
        hideFragments(transaction);
        switch (index) {
            case 0:
                img1.setImageResource(R.drawable.p3_1);
                text_1.setTextColor(Color.rgb(65, 186, 255));
                if (home == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    home = new BillFragment();
                    transaction.add(R.id.fragment,home);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(home);
                }
                break;
            case 1:
                img2.setImageResource(R.drawable.p88_1);
                text_2.setTextColor(Color.rgb(65, 186, 255));
                // 改变控件的图片和文字颜色
                if (question == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    question = new QuestionFragment();
                    transaction.add(R.id.fragment, question);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(question);
                }
                break;
            case 2:
                img3.setImageResource(R.drawable.p99_1);
                text_3.setTextColor(Color.rgb(65, 186, 255));
                // 改变控件的图片和文字颜色
                if (order == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    order = new OrderFragment();
                    transaction.add(R.id.fragment, order);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(order);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void imageInit() {
        img1.setImageResource(R.drawable.p3);
        img2.setImageResource(R.drawable.p88);
        img3.setImageResource(R.drawable.p99);
        text_1.setTextColor(Color.BLACK);
        text_2.setTextColor(Color.BLACK);
        text_3.setTextColor(Color.BLACK);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (home != null) {
            transaction.hide(home);
        }
        if (question!= null) {
            transaction.hide(question);
        }
        if (order!= null) {
            transaction.hide(order);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.personal) {
            Intent i=new Intent(MainActivity.this,PersonalActivity.class);
            startActivity(i);

        } else if (id == R.id.news) {
            Intent i=new Intent(MainActivity.this,NewsActivity.class);
            startActivity(i);
        } else if (id == R.id.myquestion) {
            if(sp.getBoolean("ISLOGIN",false)) {
                Intent i = new Intent(MainActivity.this, MyQuestionActivity.class);
                startActivity(i);
            }else{
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }

        } else if (id == R.id.myorder) {
            if(sp.getBoolean("ISLOGIN",false)) {
                Intent i = new Intent(MainActivity.this, MyOrderActivity.class);
                startActivity(i);
            }else{
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                setTabSelection(0);

                break;
            case R.id.tab2:
//                if(cid==0){
//                    Intent intent=new Intent(this,Login.class);
//                    startActivity(intent);
//                }else {
                setTabSelection(1);

                break;
            case R.id.tab3:
//                if(cid==0){
//                    Intent intent=new Intent(this,Login.class);
//                    startActivity(intent);
//                }else {
                setTabSelection(2);

                break;
        }

    }

    public void loadDate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?aid="+"e4d4c8ff5a74670e015a7467b2360000");
                if (jsonObject == null)
                    return;
                ACache cache=ACache.get(MainActivity.this);
                cache.put("bill_list",jsonObject.getString("result"));
                date =(List<Map<String,Object>>)JSONObject.parseObject(jsonObject.getString("result"),java.util.List.class);
                Message message = new Message();
            }
        }).start();
    }
}
