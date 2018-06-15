package com.rzm.imageloader.loader;

import com.rzm.imageloader.request.BitmapRequest;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:21
 * Description:This is Loader
 */
public interface Loader {

    /**
     * 加载图片
     * @param request
     */
    void load(BitmapRequest request);
}