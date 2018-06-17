package com.rzm.imageloader.request;

import android.text.TextUtils;
import android.util.Log;

import com.rzm.imageloader.loader.Loader;
import com.rzm.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:24
 * Description:This is RequestDispatcher
 */
public class RequestDispatcher extends Thread{

    /**
     * 从队列中转发请求需要持有队列的引用
     */
    private BlockingQueue<BitmapRequest> blockingQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /**
     * 阻塞式队列，转发器开启，从队列中取请求队列，如果没有则会阻塞当前线程，所以这里
     * 是在子线程开启的
     */
    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                BitmapRequest request = blockingQueue.take();
                //处理请求对象，交给loader
                String schema = parseSchema(request.getImageUrl());
                //获取加载器
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                if (loader == null){
                    Log.d("TAG",request.getImageUrl() + "没有找到对应的加载器");
                    return;
                }
                loader.load(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据图片url判断加载类型
     * @param imageUrl
     * @return
     */
    private String parseSchema(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)){
            return null;
        }
        if (imageUrl.contains("://")){
            //形如 http://xxx 或者file://xxx，这样截取后
            //可以获得http file等前缀，根据这个前缀获取相应
            //的加载器
            return imageUrl.split("://")[0];
        }else{
            Log.d("TAG","不持支的图片类型");
        }
        return null;
    }
}
