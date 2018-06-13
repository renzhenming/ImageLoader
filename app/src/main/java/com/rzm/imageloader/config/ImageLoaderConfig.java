package com.rzm.imageloader.config;

import com.rzm.imageloader.cache.BitmapCache;
import com.rzm.imageloader.policy.LoadPolicy;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:21
 * Description:This is ImageLoaderConfig
 * 以建造者模式实现，管理ImageLoader配置信息
 */
public class ImageLoaderConfig {

    /**
     * 图片显示配置 TODO 初始化
     */
    public DisplayConfig displayConfig;

    /**
     * 缓存策略
     */
    public BitmapCache bitmapCache;

    /**
     * 加载策略
     */
    public LoadPolicy loadPolicy;

    /**
     * 默认线程数
     */
    public int threadCount = Runtime.getRuntime().availableProcessors();

    private ImageLoaderConfig(){}

    /**
     * 建造者模式
     */
    public static class Builder{

        /**
         * Builder持有外部类的引用，在new的时候创建出来
         */
        private ImageLoaderConfig config;

        public Builder(){
            config = new ImageLoaderConfig();
        }

        /**
         * 设置缓存策略
         * @param bitmapCache
         * @return
         */
        public Builder setCachePolicy(BitmapCache bitmapCache){
            config.bitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         * @param loadPolicy
         * @return
         */
        public Builder setLoadPolicy(LoadPolicy loadPolicy){
            config.loadPolicy = loadPolicy;
            return this;
        }

        /**
         * 设置线程数
         * @param threadCount
         * @return
         */
        public Builder setThreadCount(int threadCount){
            config.threadCount = threadCount;
            return this;
        }

        /**
         * 设置加载过程中的图片
         * @param resId
         * @return
         */
        public Builder setLoadingImage(int resId){
            if (config.displayConfig == null){
                throw new NullPointerException("you have not set DisplayConfig,DisplayConfig is null");
            }
            config.displayConfig.loadingImage = resId;
            return this;
        }

        /**
         * 设置加载失败显示的图片
         * @param resId
         * @return
         */
        public Builder setErrorImage(int resId){
            if (config.displayConfig == null){
                throw new NullPointerException("you have not set DisplayConfig,DisplayConfig is null");
            }
            config.displayConfig.errorImage = resId;
            return this;
        }



        /**
         * 构建
         * @return
         */
        public ImageLoaderConfig build(){
            return config;
        }
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }
}
