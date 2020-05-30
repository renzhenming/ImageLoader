package com.rzm.imageloader.cache;

import android.graphics.Bitmap;

import com.rzm.imageloader.request.BitmapRequest;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:20
 * Description:This is ICache
 * 缓存顶层接口
 */
public interface ICache {

    /**
     * 获取缓存
     * @param request
     * @return
     */
    Bitmap get(BitmapRequest request);

    /**
     * 加入缓存
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request,Bitmap bitmap);

    /**
     * 移除缓存
     * @param request
     */
    void remove(BitmapRequest request);
}