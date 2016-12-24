package com.timen4.ronnny.timecounter.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.timen4.ronnny.timecounter.R;
import com.timen4.ronnny.timecounter.module.CustomTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.timen4.ronnny.timecounter.R.id.minites;
import static com.timen4.ronnny.timecounter.R.id.seconds;

public class MyDialog extends Dialog {

    private final CustomTime item;
    @BindView(R.id.edit_hour)
    EditText editHour;
    @BindView(R.id.edit_minites)
    EditText editMinites;
    @BindView(R.id.edit_seconds)
    EditText editSeconds;
    @BindView(R.id.edit_item)
    LinearLayout editItem;
    @BindView(R.id.dialog_button_cancel)
    Button dialogButtonCancel;
    @BindView(R.id.dialog_button_ok)
    Button dialogButtonOk;
    private OnClickListener onClickLinster;



    public MyDialog(Context context, CustomTime item) {
        super(context);
        this.item=item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        editHour.setText(item.getHours()+"");
        editMinites.setText(item.getMinutes()+"");
        editSeconds.setText(item.getSeconds()+"");
    }

    @OnClick({R.id.dialog_button_cancel, R.id.dialog_button_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_button_cancel:
                onClickLinster.onFailed();
                break;
            case R.id.dialog_button_ok:
                onClickLinster.onSave();
                break;
        }
    }


    public void setOnClickLinster(OnClickListener onClickLinster){
        this.onClickLinster=onClickLinster;
    }

    public int getHours() {
        return Integer.parseInt(String.valueOf(editHour.getText()));
    }

    public int getMinites() {
        return Integer.parseInt(String.valueOf(editMinites.getText()));
    }

    public int getSeconds() {
        return Integer.parseInt(String.valueOf(editSeconds.getText()));
    }
//
    public interface OnClickListener {
        void onSave();
        void onFailed();
    }
}