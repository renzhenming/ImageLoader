package com.rzm.imageloader.request;

import com.rzm.imageloader.utils.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:24
 * Description:This is RequestQueue
 */
public class RequestQueue {

    /**
     * 阻塞式队列，线程共享
     * BitmapRequest实现了Comparator接口，而PriorityBlockingQueue有专门针对比较的处理，刚好可以交给它
     */
    private BlockingQueue<BitmapRequest> blockingQueue = new PriorityBlockingQueue<BitmapRequest>();

    /**
     * 转发器数量
     */
    private int threadCount;

    /**
     * 线程安全的int
     */
    private AtomicInteger i = new AtomicInteger(0);

    /**
     * 一组转发器
     */
    private RequestDispatcher[] dispatchers;

    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 请求对象加入队列
     * @param request
     */
    public void addRequest(BitmapRequest request){
        //不包含再加入
        if (!blockingQueue.contains(request)){
            //记录添加的顺序
            request.setSerialNum(i.incrementAndGet());
            blockingQueue.add(request);
        }else{
            //请求已存在
            LogUtils.d("TAG","request is already exist");
        }
    }

    /**
     * 开始请求
     * SimpleImageLoader初始化时开启
     */
    public void start(){
        //先停止再开启，考虑安全性，比如开启动画，设置adapter都是这样处理
        stop();
        startDispatchers();
    }

    /**
     * 开启转发器
     */
    private void startDispatchers() {
        dispatchers = new RequestDispatcher[threadCount];
        for (int i1 = 0; i1 < threadCount; i1++) {
            RequestDispatcher dispatcher = new RequestDispatcher(blockingQueue);
            dispatchers[i1] = dispatcher;
            //Thread run方法执行
            dispatchers[i1].start();
        }
    }

    /**
     * 停止请求
     */
    public void stop(){

    }

}
