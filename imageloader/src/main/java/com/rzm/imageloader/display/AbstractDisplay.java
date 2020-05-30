package com.rzm.imageloader.display;

import android.support.annotation.DrawableRes;

/**
 * Author:renzhenming
 * Time:2020/5/30 14:37
 * Description:This is DisplayConfig
 * 图片显示配置类，单独拿出来作为一个单独类有利于扩展
 */
public abstract class AbstractDisplay {
    /**
     * 获取加载中的占位图
     * @return
     */
    public abstract @DrawableRes int getLoadingImage();

    /**
     * 获取加载失败的占位图
     * @return
     */
    public abstract @DrawableRes int getErrorImage();
}
