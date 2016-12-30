package com.pei.cleancachememory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 裴亮 on 16/12/29.
 */
public class CleanCacheActivity extends AppCompatActivity {

    private TextView tv;
//    private String num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        Intent intent = getIntent();
        tv = (TextView) findViewById(R.id.tv_cache);
        tv.setText(intent.getStringExtra("myCache"));
        findViewById(R.id.clear_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleanMyCache.clearAllCache(CleanCacheActivity.this);
                Toast.makeText(CleanCacheActivity.this, "已清除缓存", Toast.LENGTH_SHORT).show();
                try {
//                    num = CleanMyCache.getTotalCacheSize(CleanCacheActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tv.setText("");
            }
        });

    }
}
