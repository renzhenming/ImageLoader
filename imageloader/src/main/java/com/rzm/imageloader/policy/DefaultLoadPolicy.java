package com.rzm.imageloader.policy;

import com.rzm.imageloader.request.BitmapRequest;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:23
 * Description:This is ReversePolicy
 * 默认的加载策略，先进先加
 */
public class DefaultLoadPolicy implements IPolicy {

    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNum()-request1.getSerialNum();
    }
}
