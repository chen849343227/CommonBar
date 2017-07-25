package com.chen.commonbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.chen.topbar.CommonBar;
import com.chen.topbar.CommonBarListener;
import com.chen.topbar.CommonBarListenerAdapter;

public class MainActivity extends AppCompatActivity {

    CommonBar commonBar, commonBar1, commonBar2, commonBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        commonBar = (CommonBar) findViewById(R.id.commonBar);
        commonBar.setOnCommonBarClickListener(new CommonBarListener.OnClickListener() {
            @Override
            public void onLeftClick() {
                Toast.makeText(MainActivity.this, "left is onClicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCenterClick() {
                Toast.makeText(MainActivity.this, "center is onClicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightClick() {
                Toast.makeText(MainActivity.this, "right is onClicked", Toast.LENGTH_SHORT).show();
            }
        });

        commonBar1 = (CommonBar) findViewById(R.id.commonBar1);
        commonBar1.setOnCommonBarClickListener(new CommonBarListenerAdapter() {
            @Override
            public void onLeftClick() {
                Toast.makeText(MainActivity.this, "left is onClicked", Toast.LENGTH_SHORT).show();
            }
        });

        commonBar2 = (CommonBar) findViewById(R.id.commonBar2);
        commonBar2.setOnCommonBarClickListener(new CommonBarListenerAdapter() {
            @Override
            public void onCenterClick() {
                Toast.makeText(MainActivity.this, "center is onClicked", Toast.LENGTH_SHORT).show();
            }
        });

        commonBar3 = (CommonBar) findViewById(R.id.commonBar3);
        commonBar3.setOnCommonBarClickListener(new CommonBarListenerAdapter() {
            @Override
            public void onRightClick() {
                Toast.makeText(MainActivity.this, "right is onClicked", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
