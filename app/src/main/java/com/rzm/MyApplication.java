package com.rzm;

import android.app.Application;

import com.rzm.imageloader.R;
import com.rzm.imageloader.cache.DefaultDiskCache;
import com.rzm.imageloader.loader.LoaderConfig;
import com.rzm.imageloader.display.AbstractDisplay;
import com.rzm.imageloader.display.DefaultDisplayConfig;
import com.rzm.imageloader.loader.SimpleImageLoader;
import com.rzm.imageloader.policy.DefaultLoadPolicy;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }


    private void initImageLoader() {
        AbstractDisplay display = new DefaultDisplayConfig.Builder()
                .setLoadingImage(R.drawable.loading)
                .setErrorImage(R.drawable.not_found)
                .build();

        LoaderConfig config = new LoaderConfig.Builder()
                .setThreadCount(3) //线程数量
                .setLoadPolicy(new DefaultLoadPolicy()) //加载策略
                .setCachePolicy(new DefaultDiskCache(this)) //缓存策略
                .setDisplayConfig(display)
                .build();
        //初始化
        SimpleImageLoader.init(config);
    }
}
