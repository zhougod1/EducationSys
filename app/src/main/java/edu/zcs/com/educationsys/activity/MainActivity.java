package edu.zcs.com.educationsys.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.fragment.BillFragment;
import edu.zcs.com.educationsys.fragment.HomeFragment;
import edu.zcs.com.educationsys.fragment.QuestionFragment;
import edu.zcs.com.educationsys.util.entity.Account;
import edu.zcs.com.educationsys.util.tools.ACache;
import edu.zcs.com.educationsys.util.tools.HttpUtils;
import edu.zcs.com.educationsys.util.tools.Options;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    protected final static String URL=HttpUtils.HOST2+"/Edu/Account/getById";
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    private TextView account_head_name;
    private ACache cache;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentManager myfragmentManager;
    private BillFragment bill;
    private HomeFragment home;
    private ImageView account_head_icon;
    private QuestionFragment question;
    private ImageView img1, img2, img3;
    private TextView text_1, text_2, text_3;
    private LinearLayout tab1, tab2, tab3;
    private Account date;
    private boolean islogin=false;

    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            account_head_name.setText(date.getAccount().toString());
            imageLoader.displayImage(HttpUtils.ImageHOST+date.getAhead().toString(),account_head_icon,options);

        }
    };
    private View headerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cache=ACache.get(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        setTabSelection(0);
        if(Boolean.parseBoolean(cache.getAsString("ISLOGIN"))){
            account_head_name.setText(JSONObject.parseObject(cache.getAsString("account_list"),Account.class).getAccount().toString());
            account_head_icon.setImageBitmap(cache.getAsBitmap("HEADICON"));
        }
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
        navigationView.setItemIconTintList(null);



    }

    private void init() {

        imageLoader=ImageLoader.getInstance();
        options= Options.getListOptions();
        islogin=Boolean.parseBoolean(cache.getAsString("ISLOGIN"));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        myfragmentManager = getFragmentManager();
        account_head_icon= (ImageView) headerLayout.findViewById(R.id.account_head_icon);
        account_head_name= (TextView) headerLayout.findViewById(R.id.account_head_name);
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
        if(islogin) {
            loadDate();
        }
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
                    home = new HomeFragment();
                    transaction.add(R.id.fragment, home);
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
                if (bill == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    bill = new BillFragment();
                    transaction.add(R.id.fragment,bill);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(bill);
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
        if (bill != null) {
            transaction.hide(bill);
        }
        if (question!= null) {
            transaction.hide(question);
        }
        if (home!= null) {
            transaction.hide(home);
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
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
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
        if(!islogin){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else {
            if (id == R.id.personal) {
                Intent i = new Intent(MainActivity.this, PersonalActivity.class);
                startActivity(i);
            } else if (id == R.id.news) {
                Intent i = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(i);
            } else if (id == R.id.release_question) {
                Intent i = new Intent(MainActivity.this, ReleaseQuestionActivity.class);
                startActivity(i);

            } else if (id == R.id.release_order) {
                Intent i = new Intent(MainActivity.this, ReleaseActivity.class);
                startActivity(i);

            } else if (id == R.id.myorder) {

            } else if (id == R.id.myquestion) {

            } else if (id == R.id.log_off){
                setTabSelection(0);
                account_head_name.setText("暂未登录");
                account_head_icon.setImageResource(R.drawable.erro);
                islogin=false;
                cache.put("ISLOGIN","false");
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        boolean nowState=Boolean.parseBoolean(cache.getAsString("ISLOGIN"));
        if(islogin!=nowState && nowState){
            loadDate();
        }
        islogin=nowState;
        Log.e("onRestart: ",String.valueOf(islogin) );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                setTabSelection(0);

                break;
            case R.id.tab2:
                setTabSelection(1);

                break;
            case R.id.tab3:
                if(!islogin){
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    setTabSelection(2);
                }
                break;
        }

    }

    public void loadDate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?aid="+cache.getAsString("AID"));
                if (jsonObject == null)
                    return;
                cache=ACache.get(MainActivity.this);
                cache.put("account_list",jsonObject.getString("result"));
                date =JSONObject.parseObject(jsonObject.getString("result"),Account.class);
                cache.put("HEADICON",imageLoader.loadImageSync(HttpUtils.ImageHOST+date.getAhead().toString()));
                Message message = new Message();
                mhandler.sendMessage(message);
            }
        }).start();
    }
}
