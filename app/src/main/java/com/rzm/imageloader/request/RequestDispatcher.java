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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
