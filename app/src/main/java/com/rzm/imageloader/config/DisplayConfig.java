package com.rzm.imageloader.config;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:24
 * Description:This is DisplayConfig
 * 图片显示配置类，单独拿出来作为一个单独类有利于扩展，仿Glide
 */
public class DisplayConfig {

    /**
     * 加载过程中的占位图片
     */
    public int loadingImage = -1;

    /**
     * 加载失败显示的图片
     */
    public int errorImage = -1;
}
