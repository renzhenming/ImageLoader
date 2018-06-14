package com.rzm.imageloader.request;

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

    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                BitmapRequest request = blockingQueue.take();
                //处理请求对象
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
