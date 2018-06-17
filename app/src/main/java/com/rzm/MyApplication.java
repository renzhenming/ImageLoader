package com.rzm;

import android.app.Application;

import com.rzm.imageloader.R;
import com.rzm.imageloader.cache.MemoryDiskCache;
import com.rzm.imageloader.config.ImageLoaderConfig;
import com.rzm.imageloader.loader.SimpleImageLoader;
import com.rzm.imageloader.policy.ReversePolicy;
import com.rzm.imageloader.policy.SerialPolicy;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        initExceptionHandler();
        initImageLoader();
    }


    private void initImageLoader() {
        //配置
        ImageLoaderConfig.Builder build = new ImageLoaderConfig.Builder();
        build.setThreadCount(3) //线程数量
                .setLoadPolicy(new SerialPolicy()) //加载策略
                .setCachePolicy(new MemoryDiskCache(this)) //缓存策略
                .setLoadingImage(R.drawable.loading)
                .setErrorImage(R.drawable.not_found);

        ImageLoaderConfig config = build.build();
        //初始化
        SimpleImageLoader imageLoader = SimpleImageLoader.getInstance(config);
    }


    private void initExceptionHandler() {

    }
}
