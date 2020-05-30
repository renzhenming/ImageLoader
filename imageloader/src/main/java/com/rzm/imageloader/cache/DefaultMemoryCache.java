package com.rzm.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.rzm.imageloader.request.BitmapRequest;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:20
 * Description:This is DefaultMemoryCache
 */
public class DefaultMemoryCache implements ICache {

    private LruCache<String,Bitmap> mLruCache;

    public DefaultMemoryCache(){
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/1024/8);
        mLruCache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }
    @Override
    public Bitmap get(BitmapRequest request) {
        if (mLruCache == null) return null;
        return mLruCache.get(request.getImageUrlMd5());
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (mLruCache == null) return;
        mLruCache.put(request.getImageUrlMd5(),bitmap);
    }

    @Override
    public void remove(BitmapRequest request) {
        if (mLruCache == null) return;
        mLruCache.remove(request.getImageUrlMd5());
    }
}
