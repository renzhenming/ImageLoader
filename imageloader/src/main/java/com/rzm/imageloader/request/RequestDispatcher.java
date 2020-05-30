package com.rzm.imageloader.request;

import android.text.TextUtils;
import com.rzm.imageloader.loader.Loader;
import com.rzm.imageloader.loader.LoaderManager;
import com.rzm.imageloader.utils.LogUtils;

import java.util.concurrent.BlockingQueue;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:24
 * Description:This is RequestDispatcher
 */
public class RequestDispatcher extends Thread{
    private static final String TAG = RequestDispatcher.class.getSimpleName();

    /**
     * 从队列中转发请求需要持有队列的引用
     */
    private BlockingQueue<BitmapRequest> blockingQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /**
     * 阻塞式队列，转发器开启，从队列中取请求队列，如果没有则会阻塞当前线程
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
                    LogUtils.d(TAG,request.getImageUrl() + " don't find relate loader");
                    return;
                }
                loader.load(request);
            } catch (InterruptedException e) {
                //发生中断异常时，中断标志位会被复位，重新将中断标志位设置为true，这样外界可以通过这个状态判断是否需要中断线程
                Thread.currentThread().interrupt();
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
            LogUtils.d(TAG," don't support this image url");
        }
        return null;
    }
}
