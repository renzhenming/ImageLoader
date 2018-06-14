package com.rzm.imageloader.request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:24
 * Description:This is RequestQueue
 */
public class RequestQueue {

    /**
     * BitmapRequest实现了Comparator接口，而PriorityBlockingQueue有专门针对比较的处理，刚好可以交给它
     */
    private BlockingQueue<BitmapRequest> blockingQueue = new PriorityBlockingQueue<BitmapRequest>();
}
