package com.rzm.imageloader.loader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.rzm.imageloader.display.AbstractDisplay;
import com.rzm.imageloader.request.BitmapRequest;
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
    private LoaderConfig config;

    private static volatile SimpleImageLoader instance;

    /**
     * 请求队列
     */
    private RequestQueue requestQueue;

    private SimpleImageLoader(){}

    private SimpleImageLoader(LoaderConfig config){
        this.config = config;
        //初始化请求队列
        requestQueue = new RequestQueue(config.getThreadCount());
        //开启请求队列
        requestQueue.start();
    }

    /**
     * 初始化ImageLoader 设置配置信息
     * 必须进行初始化，否则调用空参getInstance()会抛异常
     * @param config
     * @return
     */
    public static SimpleImageLoader init(@NonNull LoaderConfig config){
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
            throw new UnsupportedOperationException("SimpleImageLoader haven't been getInstance with LoaderConfig,call getInstance(LoaderConfig config) in your application");
        }
        return instance;
    }

    /**
     * 显示图片
     * @param imageView
     * @param url
     */
    public void display(ImageView imageView,String url){
        display(imageView,url,null,null);
    }
    /**
     * 显示图片
     * @param imageView
     * @param url
     */
    public void display(ImageView imageView, String url, AbstractDisplay displayConfig){
        display(imageView,url,displayConfig,null);
    }

    /**
     * 显示图片
     * @param imageView
     * @param url
     */
    public void display(ImageView imageView,String url,ImageListener listener){
        display(imageView,url,null,listener);
    }
    /**
     * 显示图片，用于针对特殊图片配置特殊的配置信息
     * @param imageView
     * @param url
     * @param displayConfig
     * @param listener
     */
    public void display(ImageView imageView,String url,AbstractDisplay displayConfig,ImageListener listener){
        if (imageView  == null){
            throw new NullPointerException("ImageView cannot be null");
        }
        if (TextUtils.isEmpty(url)){
            throw new NullPointerException("Image url cannot be null");
        }
        BitmapRequest request= new BitmapRequest(imageView,url,displayConfig,listener);
        //加入请求队列
        requestQueue.addRequest(request);
    }

    /**
     * 监听图片，设置后期处理，仿Glide
     */
    public interface ImageListener{
        void onComplete(ImageView imageView, Bitmap bitmap,String url);
    }

    /**
     * 获取全局配置
     * @return
     */
    public LoaderConfig getConfig() {
        return config;
    }
}
