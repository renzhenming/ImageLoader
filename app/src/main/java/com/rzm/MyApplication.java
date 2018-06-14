package com.rzm;

import android.app.Application;

import com.rzm.imageloader.cache.MemoryCache;
import com.rzm.imageloader.config.ImageLoaderConfig;
import com.rzm.imageloader.loader.SimpleImageLoader;
import com.rzm.imageloader.policy.SerialPolicy;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setCachePolicy(new MemoryCache())
                .setLoadPolicy(new SerialPolicy())
                .build();
        SimpleImageLoader imageLoader = SimpleImageLoader.getInstance(config);
    }
}
