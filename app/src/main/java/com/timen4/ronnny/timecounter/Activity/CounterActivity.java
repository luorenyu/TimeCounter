package com.timen4.ronnny.timecounter.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.timen4.ronnny.timecounter.R;
import com.timen4.ronnny.timecounter.module.CustomTime;
import com.timen4.ronnny.timecounter.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luore on 2016/7/20.
 */
public class CounterActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    @BindView(R.id.ib_add)
    ImageButton ibAdd;
    private ListView mContent_lv;
    private RelativeLayout mBtn_add;
    private RelativeLayout mBtn_clear;
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
        ButterKnife.bind(this);
        initView();
        registEvents();
    }

    private void initView() {
        mContent_lv = (ListView) findViewById(R.id.content_lv);
        mEdit_item = (LinearLayout) findViewById(R.id.edit_item);
        /*Button*/
        mBtn_add = (RelativeLayout) findViewById(R.id.btn_add);
//        mBtn_equal = (ImageButton) findViewById(R.id.btn_equal);
        mBtn_clear = (RelativeLayout) findViewById(R.id.btn_clear);
        mBtn_finished = (Button) findViewById(R.id.finished);
        /*EditText*/
        mEt_hour = (EditText) findViewById(R.id.hour);
        mEt_minites = (EditText) findViewById(R.id.minites);
        mEt_seconds = (EditText) findViewById(R.id.seconds);
        tv_result = (TextView) findViewById(R.id.tv_result);

    }

    private void registEvents() {
        /*regist ClickListener*/
        mBtn_add.setOnClickListener(this);
//        mBtn_equal.setOnClickListener(this);
        mBtn_clear.setOnClickListener(this);
        mBtn_finished.setOnClickListener(this);
        ibAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        /*regist EditorActionListener*/
        mEt_hour.setOnEditorActionListener(this);
        mEt_minites.setOnEditorActionListener(this);
        mEt_seconds.setOnEditorActionListener(this);

        List<CustomTime> datas = new ArrayList<>();
        mAdapter = new MyAdapter(datas);
        mContent_lv.setAdapter(mAdapter);

        mEt_hour.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mEt_hour.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mEt_minites.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mEt_minites.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mEt_seconds.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mEt_seconds.setInputType(EditorInfo.TYPE_CLASS_PHONE);




    }

    private boolean isShow = false;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add:
                isShow = !isShow;
                if (isShow) {
                    mEdit_item.setVisibility(View.VISIBLE);
                    ibAdd.setBackgroundResource(R.drawable.add2);
                } else {
                    mEdit_item.setVisibility(View.GONE);
                    ibAdd.setBackgroundResource(R.drawable.add);
                }

                break;
            case R.id.btn_clear:
                mAdapter.clearData();
                mAdapter.notifyDataSetChanged();
                tv_result.setText(R.string.preword);

                break;
            case R.id.finished:
                finished();
                compute();
                break;
        }
    }

    private void compute() {
        List<CustomTime> datas = mAdapter.getmDatas();

        int h = 0;
        int min = 0;
        int s = 0;
        for (CustomTime time : datas) {
            int tempMin = 0;
            int tempH = 0;
            s += time.getSeconds();
            if (s > 59) {
                tempMin = s / 60;
                s -= 60;
            }
            min += time.getMinutes() + tempMin;
            if (min > 59) {
                tempH = min / 60;
                min -= 60;
            }

            h += time.getHours() + tempH;
        }
        CustomTime resultTime = new CustomTime(h, min, s);
        tv_result.setText(getResources().getString(R.string.preword) + resultTime.toString());
    }

    public void finished() {
        int hour = 0, minites = 0, seconds = 0;
        if (!TextUtils.isEmpty(mEt_hour.getText().toString())) {
            hour = Integer.parseInt(mEt_hour.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(mEt_minites.getText().toString())) {
            minites = Integer.parseInt(mEt_minites.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(mEt_seconds.getText().toString())) {
            seconds = Integer.parseInt(mEt_seconds.getText().toString().trim());
        }
        if (hour == 0 & minites == 0 && seconds == 0) {
            Toast.makeText(CounterActivity.this, "请输入时间", Toast.LENGTH_SHORT).show();
            return;
        }
        CustomTime timeData = new CustomTime(hour, minites, seconds);
        mAdapter.addData(timeData);
        mAdapter.notifyDataSetChanged();
        clearEditText();
    }


    private void clearEditText() {
        mEt_hour.setText("");
        mEt_minites.setText("");
        mEt_seconds.setText("");
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        switch (actionId) {

            case EditorInfo.IME_ACTION_DONE:
                finished();
                handled = true;
                break;
            default:
                break;
        }
        return handled;
    }


    public class MyAdapter extends BaseAdapter {

        private List<CustomTime> mDatas;


        public MyAdapter(List<CustomTime> mDatas) {
            this.mDatas = mDatas;
        }

        public void addData(CustomTime data) {
            if (data != null) {
                mDatas.add(data);
            } else {
                mDatas = new ArrayList<CustomTime>();
                mDatas.add(data);
            }
        }

        public void clearData() {
            mDatas.clear();
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public CustomTime getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            CustomTime timeItem = mDatas.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(CounterActivity.this, R.layout.lv_item, null);
                viewHolder.showTime = (TextView) convertView.findViewById(R.id.showTime);
                viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete);
                viewHolder.edit = (ImageButton) convertView.findViewById(R.id.edit);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.showTime.setText(timeItem.toString());
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(CounterActivity.this,"开始编辑",Toast.LENGTH_SHORT).show();
                    //此处直接new一个Dialog对象出来，在实例化的时候传入主题
                    final MyDialog dialog = new MyDialog(CounterActivity.this, getItem(position));
                    CustomTime item = getItem(position);
                    dialog.setOnClickLinster(new MyDialog.OnClickListener() {
                        @Override
                        public void onSave() {
                            getItem(position).setHours(Integer.valueOf(dialog.getHours()));
                            getItem(position).setMinutes(Integer.valueOf(dialog.getMinites()));
                            getItem(position).setSeconds(Integer.valueOf(dialog.getSeconds()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                            compute();
                        }

                        @Override
                        public void onFailed() {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    notifyDataSetChanged();
                    compute();
                }
            });
            return convertView;
        }

        public List<CustomTime> getmDatas() {
            return mDatas;
        }

        public class ViewHolder {
            TextView showTime;
            ImageButton delete;
            ImageButton edit;
        }
    }


}
