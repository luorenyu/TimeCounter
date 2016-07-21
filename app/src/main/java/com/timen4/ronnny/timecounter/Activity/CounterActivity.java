package com.timen4.ronnny.timecounter.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timen4.ronnny.timecounter.R;

import java.sql.Time;
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
    private TextView tv_result;


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

        tv_result = (TextView) findViewById(R.id.tv_result);


    }

    private void registEvents() {
        mBtn_add.setOnClickListener(this);
        mBtn_equal.setOnClickListener(this);
        mBtn_clear.setOnClickListener(this);
        mBtn_finished.setOnClickListener(this);
        List<Time> datas=new ArrayList<Time>();
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
                List<Time> datas = mAdapter.getmDatas();

                int h=0;
                int min=0;
                int s=0;
                for (Time time:datas) {
                    int tempMin = 0;
                    int tempH = 0;
                    s+=time.getSeconds();
                    if(s>59){
                        tempMin = s % 60;
                        s-=60;
                    }
                    min+=time.getMinutes()+tempMin;
                    if(min>59){
                        tempH=min%60;
                        min-=60;
                    }

                    h += time.getHours()+tempH;
                }
                Time resultTime =new Time(h,min,s);
                tv_result.setText(getResources().getString(R.string.preword)+resultTime.toString());
                break;
            case R.id.btn_clear:
                mAdapter.clearData();
                mAdapter.notifyDataSetChanged();
                tv_result.setText(R.string.preword);
                break;
            case R.id.finished:
                int hour=0,minites=0 ,seconds=0;
                if(!TextUtils.isEmpty(mEt_hour.getText().toString())){
                    hour = Integer.parseInt(mEt_hour.getText().toString());
                }
                if(!TextUtils.isEmpty(mEt_minites.getText().toString())){
                    minites = Integer.parseInt(mEt_minites.getText().toString());
                }
                if(!TextUtils.isEmpty(mEt_seconds.getText().toString())){
                    seconds = Integer.parseInt(mEt_seconds.getText().toString());
                }
                if(hour==0&minites==0&&seconds==0){
                    Toast.makeText(CounterActivity.this,"请输入时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                Time timeData=new Time(hour,minites,seconds);
                mAdapter.addData(timeData);
                mAdapter.notifyDataSetChanged();
                clearEditText();
                break;
        }
    }

    private void clearEditText() {
        mEt_hour.setText("");
        mEt_minites.setText("");
        mEt_seconds.setText("");
    }

    public class MyAdapter extends BaseAdapter{

        private List<Time> mDatas;


        public MyAdapter(List<Time> mDatas){
            this.mDatas = mDatas;
        }

        public void addData(Time data){
            if (data!=null){
                mDatas.add(data);
            }else{
                mDatas =new ArrayList<Time>();
                mDatas.add(data);
            }
        }

        public void clearData(){
            mDatas.clear();
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Time timeItem = mDatas.get(position);
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=View.inflate(CounterActivity.this,R.layout.lv_item,null);
                viewHolder.showTime= (TextView) convertView.findViewById(R.id.showTime);
                viewHolder.delete= (ImageButton) convertView.findViewById(R.id.delete);
                viewHolder.edit= (ImageButton) convertView.findViewById(R.id.edit);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.showTime.setText(timeItem.toString());
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CounterActivity.this,"开始编辑",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public List<Time> getmDatas() {
            return mDatas;
        }
        public class ViewHolder{
            TextView showTime;
            ImageButton delete;
            ImageButton edit;
        }
    }
}
