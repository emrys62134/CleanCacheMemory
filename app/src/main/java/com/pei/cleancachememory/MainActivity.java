package com.pei.cleancachememory;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banner banner;

    /**
     * 使用三方Banner:
     * 第一步: 导依赖 compile 'com.youth.banner:banner:1.4.4'
     * <p>
     * 第二步: 添加 网络权限
     * <!-- if you want to load images from the internet -->
     * <uses-permission android:name="android.permission.INTERNET" />
     * <p>
     * <!-- if you want to load images from a file OR from the internet -->
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     * <p>
     * 第三步: 在布局文件中添加Banner, 可以设置自定义属性
     * <com.youth.banner.Banner
     * xmlns:app="http://schemas.android.com/apk/res-auto"
     * android:id="@+id/banner"
     * android:layout_width="match_parent"
     * android:layout_height="高度自己设置" />
     * <p>
     * <p>
     * 第四步: 重写图片加载器
     * public class GlideImageLoader extends ImageLoader{
     * <p>
     * public void displayImage(Context context, Object path, ImageView imageView) {
     * //Glide 加载图片简单用法
     * Glide.with(context).load(path).into(imageView);
     * }
     */


    private String url = "http://app.api.autohome.com.cn/autov4.2.5/news/newslist-a2-pm1-v4.2.5-c0-nt0-p1-s30-l0.html";
    private Bean bean ;
    private List<Bean.ResultBean.FocusimgBeanDetail> details;
    private ArrayList<String> list;
    private String[]titles = {"1","2","3","4","5","6"};
    private ArrayList<String> arrayList;
    private String numCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 跳转设置界面
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CleanCacheActivity.class);
                try {
                    numCache = CleanMyCache.getTotalCacheSize(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent.putExtra("myCache", numCache);
                startActivity(intent);

            }
        });


        banner = (Banner) findViewById(R.id.banner);
        list = new ArrayList<>();
        arrayList = new ArrayList<>();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response,Bean.class);
                details = bean.getResult().getFocusimg();
                for (int i = 0; i < details.size() ; i++) {
                    // 将集合里的图片取出放到一个新的集合中
                    list.add(details.get(i).getImgurl());
                    arrayList.add(details.get(i).getTitle());
                }


                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                banner.setImageLoader(new GlideImageLoader());
                banner.setIndicatorGravity(BannerConfig.RIGHT);
                banner.setDelayTime(2000);
                banner.setImages(list);
//                banner.setBannerTitles(Arrays.asList(titles));
                banner.setBannerTitles(arrayList);
                banner.setBannerAnimation(Transformer.DepthPage);
                banner.start();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
