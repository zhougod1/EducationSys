package edu.zcs.com.educationsys.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.zcs.com.educationsys.R;

public class ReleaseActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText accountName;
    private EditText accountPhone;
    private EditText area;
    private EditText address;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> course;
    Handler myhandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        init();
    }

    public void init(){
        course=new ArrayList<String>();
        spinner =(Spinner)findViewById(R.id.spinner);
        accountName =(EditText)findViewById(R.id.accountName);
        accountPhone =(EditText)findViewById(R.id.accountPhone);
        area =(EditText)findViewById(R.id.area);
        address =(EditText)findViewById(R.id.address);
        spinnerAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,course);
        spinner.setAdapter(spinnerAdapter);


    }
}
