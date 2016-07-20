package com.timen4.ronnny.timecounter.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.timen4.ronnny.timecounter.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_go = (ImageButton) findViewById(R.id.btn_go);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_go:
                Intent intent=new Intent(MainActivity.this,CounterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
