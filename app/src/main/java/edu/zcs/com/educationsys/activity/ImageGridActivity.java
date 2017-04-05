package edu.zcs.com.educationsys.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.zcs.com.educationsys.R;
import edu.zcs.com.educationsys.adapter.ImageGridAdapter;
import edu.zcs.com.educationsys.adapter.ImageGridAdapter.TextCallback;
import edu.zcs.com.educationsys.util.tools.AlbumHelper;
import edu.zcs.com.educationsys.util.tools.BitmapUtils;
import edu.zcs.com.educationsys.util.tools.ImageItem;

public class ImageGridActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_LIST = "imagelist";

    // ArrayList<Entity> dataList;
    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(ImageGridActivity.this, "最多选择9张图片", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_grid);

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataList = (List<ImageItem>) getIntent().getSerializableExtra(EXTRA_IMAGE_LIST);

        initView();
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext();) {
                    list.add(it.next());
                }
                for (int i = 0; i < list.size(); i++) {
                    if (BitmapUtils.drr.size() < 9) {
                        BitmapUtils.drr.add(list.get(i));
                    }
                }
                if (BitmapUtils.act_bool) {
//                    Intent intent = new Intent(ImageGridActivity.this,
//                            ReleaseQuestionActivity.class);
//                    startActivity(intent);
                    BitmapUtils.act_bool = false;
                    finish();
                }

                finish();
            }

        });
    }

    //设置选中后图标，完成数+1
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // if(dataList.get(position).isSelected()){
                // dataList.get(position).setSelected(false);
                // }else{
                // dataList.get(position).setSelected(true);
                // }

                adapter.notifyDataSetChanged();
            }

        });

    }
}
