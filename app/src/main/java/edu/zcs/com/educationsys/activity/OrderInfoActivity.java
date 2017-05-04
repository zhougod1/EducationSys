package edu.zcs.com.educationsys.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.util.entity.Account;
import edu.zcs.com.educationsys.util.entity.Order;
import edu.zcs.com.educationsys.util.tools.CourseUtils;
import edu.zcs.com.educationsys.util.tools.DateUtils;
import edu.zcs.com.educationsys.util.tools.HttpUtils;

public class OrderInfoActivity extends AppCompatActivity {

    protected static final String URL = HttpUtils.HOST2+ "/Edu/Order/queryById";
    protected static final String URL2 = HttpUtils.HOST2+ "/Edu/Bill/add";
    private String oid;
    private TextView my_order_title,my_order_name,my_order_phone,my_order_typle,
            my_order_address,my_order_info,my_order_cycle,my_order_pay,my_order_time,my_order_area;
    private ImageView my_order_img;
    private Order order;
    private TextView order_release;
    private Account account;
    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    my_order_name.setText(account.getAname());
                    my_order_time.setText(order.getOdate());
                    my_order_typle.setText(order.getOlevel()+order.getOcourse());
                    my_order_title.setText(order.getOtitle());
                    my_order_pay.setText("￥"+order.getPay());
                    my_order_phone.setText(account.getAphonenumber());
                    my_order_area.setText(order.getOarea());
                    my_order_address.setText(order.getOaddress());
                    my_order_cycle.setText(order.getOcycle());
                    my_order_info.setText(order.getOinfo());
                    my_order_img.setImageResource(CourseUtils.getCourseIcon(order.getOcourse()));
                    break;
                case 2:
                    if((Boolean) msg.obj) {
                        Toast.makeText(OrderInfoActivity.this, "提交成功,请耐心等待对方的回复", Toast.LENGTH_SHORT).show();
//                        Intent i=new Intent(OrderInfoActivity.this, BillFragment.class);
//                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(OrderInfoActivity.this, "提交失败,请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        sp = this.getSharedPreferences("account", Context.MODE_WORLD_READABLE);
        oid=getIntent().getStringExtra("oid");
//        order = new ArrayList<Order>();
//        account = new ArrayList<Account>();
        my_order_address=(TextView)findViewById(R.id.my_order_address);
        my_order_cycle=(TextView)findViewById(R.id.my_order_cycle);
        my_order_info=(TextView)findViewById(R.id.my_order_info);
        my_order_name=(TextView)findViewById(R.id.my_order_name);
        my_order_phone=(TextView)findViewById(R.id.my_order_phone);
        my_order_title=(TextView)findViewById(R.id.my_order_title);
        my_order_typle=(TextView)findViewById(R.id.my_order_typle);
        my_order_pay=(TextView)findViewById(R.id.my_order_pay);
        my_order_time=(TextView)findViewById(R.id.my_order_time);
        my_order_area=(TextView)findViewById(R.id.my_order_area);
        my_order_img=(ImageView)findViewById(R.id.my_order_img);
        order_release= (TextView) findViewById(R.id.order_release);
        order_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new PopupWindows(OrderInfoActivity.this, order_release);
            }
        });
        init();
    }

     public void init(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 JSONObject jsonObject = HttpUtils.getJsonObject(URL+"?oid="+oid);
                 if (jsonObject == null)
                     return;
                 order =JSONObject.parseObject(jsonObject.getString("order"), Order.class);
                 account =JSONObject.parseObject(jsonObject.getString("account"), Account.class);
                 Message message = new Message();
                 myHandler.sendMessage(message);
             }
         }).start();
     }

    public class PopupWindows extends PopupWindow {
        public PopupWindows(Context mContext, View parent) {

            final View view = View.inflate(mContext,R.layout.item_popupwindows_release_order, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            CardView question_answer = (CardView) view
                    .findViewById(R.id.release_order_layout);
            question_answer.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();
            TextView order_release_title= (TextView) view.findViewById(R.id.order_release_title);
            TextView order_release_coures= (TextView) view.findViewById(R.id.order_release_coures);
            TextView order_release_area= (TextView) view.findViewById(R.id.order_release_area);
            TextView order_release_address= (TextView) view.findViewById(R.id.order_release_address);
            TextView order_release_phone= (TextView) view.findViewById(R.id.order_release_phone);
            TextView order_release_info= (TextView) view.findViewById(R.id.order_release_info);
            TextView order_release_pay= (TextView) view.findViewById(R.id.order_release_pay);
            TextView order_release_time= (TextView) view.findViewById(R.id.order_release_time);
            TextView order_release_cycle= (TextView) view.findViewById(R.id.order_release_cycle);
            TextView order_release_submit= (TextView) view.findViewById(R.id.order_release_submit);
            TextView order_release_cancel= (TextView) view.findViewById(R.id.order_release_canel);
            TextView order_release_name= (TextView) view.findViewById(R.id.order_release_name);
            final EditText order_release_message= (EditText) view.findViewById(R.id.order_release_message);
            order_release_name.setText(account.getAname());
//          order_release_time.setText(order.getOdate());
            order_release_coures.setText(order.getOlevel()+order.getOcourse());
            order_release_title.setText(order.getOtitle());
            order_release_pay.setText("￥"+order.getPay());
            order_release_phone.setText(account.getAphonenumber());
            order_release_area.setText(order.getOarea());
            order_release_address.setText(order.getOaddress());
            order_release_cycle.setText(order.getOcycle());
            order_release_info.setText(order.getOinfo());
            order_release_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(OrderInfoActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("即将为你提交请求");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Map<String,String> bill=new HashMap<String, String>();
                                    bill.put("oid",order.getOid());
                                    bill.put("message",order_release_message.getText().toString());
                                    bill.put("aid",account.getAid());
                                    bill.put("b_aid","e4d4c8ff5a74670e015a7467b2360000");//sp.getString("aid",""));
                                    bill.put("time",new DateUtils().getDate());
                                    JSONObject jsonObject = HttpUtils.getJsonObject(URL2,bill);
                                    if (jsonObject == null)
                                        return;
                                    Boolean result=JSONObject.parseObject(jsonObject.getString("result"),java.lang.Boolean.class);
                                    Message message = new Message();
                                    message.what=2;
                                    message.obj=result;
                                    myHandler.sendMessage(message);
                                }
                            }).start();
                            dismiss();

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });

            order_release_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(OrderInfoActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("是否放弃放弃请求");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });



        }
    }
}
