package com.timen4.ronnny.timecounter.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.timen4.ronnny.timecounter.R;
import com.timen4.ronnny.timecounter.bean.CustomTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luore on 2016/7/20.
 */
public class CounterActivity extends Activity implements View.OnClickListener {

    private ListView mContent_lv;
    private ImageButton mBtn_add;
    private ImageButton mBtn_equal;
    private ImageButton mBtn_clear;
    private LinearLayout mEdit_item;
    private MyAdapter mAdapter;
    private Button mBtn_finished;
    private EditText mEt_hour;
    private EditText mEt_minites;
    private EditText mEt_seconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_activity);
        initView();
        registEvents();
    }

    private void initView() {
        mContent_lv = (ListView) findViewById(R.id.content_lv);
        mEdit_item = (LinearLayout) findViewById(R.id.edit_item);
        /*Button*/
        mBtn_add = (ImageButton) findViewById(R.id.btn_add);
        mBtn_equal = (ImageButton) findViewById(R.id.btn_equal);
        mBtn_clear = (ImageButton) findViewById(R.id.btn_clear);
        mBtn_finished = (Button) findViewById(R.id.finished);
        /*EditText*/
        mEt_hour = (EditText) findViewById(R.id.hour);
        mEt_minites = (EditText) findViewById(R.id.minites);
        mEt_seconds = (EditText) findViewById(R.id.seconds);


    }

    private void registEvents() {
        mBtn_add.setOnClickListener(this);
        mBtn_equal.setOnClickListener(this);
        mBtn_clear.setOnClickListener(this);
        mBtn_finished.setOnClickListener(this);
        List<CustomTime> datas=new ArrayList<CustomTime>();
        mAdapter = new MyAdapter(datas);
        mContent_lv.setAdapter(mAdapter);
    }

    private boolean isShow=false;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_add:
                isShow=!isShow;
                if(isShow){
                    mEdit_item.setVisibility(View.VISIBLE);
                    mBtn_add.setBackgroundResource(R.drawable.add2);
                }else{
                    mEdit_item.setVisibility(View.GONE);
                    mBtn_add.setBackgroundResource(R.drawable.add);
                }
                break;
            case R.id.btn_equal:

                break;
            case R.id.btn_clear:

                break;
            case R.id.finished:
                clearEditText();
                int hour = Integer.parseInt(mEt_hour.getText().toString());
                int minites = Integer.parseInt(mEt_minites.getText().toString());
                int seconds = Integer.parseInt(mEt_seconds.getText().toString());
                if(hour==0&minites==0&&seconds==0){
                    Toast.makeText(CounterActivity.this,"请输入时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                CustomTime timeData=new CustomTime(hour,minites,seconds);
                mAdapter.addData(timeData);
                break;
        }
    }

    private void clearEditText() {
        mEt_hour.setText("");
        mEt_minites.setText("");
        mEt_seconds.setText("");
    }

    public class MyAdapter extends BaseAdapter{

        private List<CustomTime> datas;


        public MyAdapter(List<CustomTime> datas){
            if(datas==null){
                this.datas=new ArrayList<CustomTime>();
            }
            this.datas=datas;
        }

        public void addData(CustomTime data){
            if (data!=null){
                datas.add(data);
            }
        }

        public void clearData(){
            datas.clear();
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return null;
        }
    }
}
