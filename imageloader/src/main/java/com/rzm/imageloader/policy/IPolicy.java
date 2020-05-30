package com.rzm.imageloader.policy;

import com.rzm.imageloader.request.BitmapRequest;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:23
 * Description:This is IPolicy
 */
public interface IPolicy {

    /**
     * 两个请求的优先级比较
     * @param request1
     * @param request2
     * @return
     */
    int compareTo(BitmapRequest request1, BitmapRequest request2);
}