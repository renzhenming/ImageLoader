package com.rzm.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.rzm.imageloader.config.ImageLoaderConfig;
import com.rzm.imageloader.request.RequestQueue;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:22
 * Description:This is SimpleImageLoader
 */
public class SimpleImageLoader {

    /**
     * 持有配置信息对象的引用
     */
    public ImageLoaderConfig config;

    private static volatile SimpleImageLoader instance;

    /**
     * 请求队列
     */
    public RequestQueue requestQueue;

    private SimpleImageLoader(){}

    private SimpleImageLoader(ImageLoaderConfig config){
        this.config = config;
    }

    /**
     * 用于在Application中初始化ImageLoader 设置配置信息
     * 必须，否则调用空参getInstance()会抛异常
     * @param config
     * @return
     */
    public static SimpleImageLoader getInstance(ImageLoaderConfig config){
        if (instance == null){
            synchronized (SimpleImageLoader.class){
                if (instance == null){
                    instance = new SimpleImageLoader(config);
                }
            }
        }
        return instance;
    }

    /**
     * 用于在代码中获取单例对象
     * @return
     */
    public static SimpleImageLoader getInstance(){
        if (instance == null){
            throw new UnsupportedOperationException("SimpleImageLoader haven't been init with ImageLoaderConfig,call getInstance(ImageLoaderConfig config) in your application");
        }
        return instance;
    }

    /**
     * 显示图片
     * @param imageView
     * @param url
     */
    public void display(ImageView imageView,String url){

    }

    /**
     * 显示图片，用于针对特殊图片配置特殊的配置信息
     * @param imageView
     * @param url
     * @param config
     */
    public void display(ImageView imageView,String url,ImageLoaderConfig config){

    }

    /**
     * 监听图片，设置后期处理，仿Glide
     */
    public static interface ImageListener{
        void onComplete(ImageView imageView, Bitmap bitmap,String url);
    }
}