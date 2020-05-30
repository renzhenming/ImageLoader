package com.rzm.imageloader.loader;

import com.rzm.imageloader.cache.ICache;
import com.rzm.imageloader.cache.DefaultMemoryCache;
import com.rzm.imageloader.display.AbstractDisplay;
import com.rzm.imageloader.display.DefaultDisplayConfig;
import com.rzm.imageloader.policy.DefaultLoadPolicy;
import com.rzm.imageloader.policy.IPolicy;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:21
 * Description:This is LoaderConfig
 * 以建造者模式实现，管理ImageLoader配置信息
 */
public class LoaderConfig {

    /**
     * 缓存策略
     */
    private ICache bitmapCache;

    /**
     * 加载策略
     */
    private IPolicy loadPolicy;

    /**
     * 默认线程数
     */
    private int threadCount;

    /**
     * 图片显示配置
     */
    private AbstractDisplay displayConfig;

    private LoaderConfig() {
        loadPolicy = new DefaultLoadPolicy();
        bitmapCache = new DefaultMemoryCache();
        displayConfig = new DefaultDisplayConfig();
        threadCount = Runtime.getRuntime().availableProcessors();
    }

    /**
     * builder模式构建
     */
    public static class Builder {

        private LoaderConfig config;

        public Builder() {
            config = new LoaderConfig();
        }

        /**
         * 设置缓存策略
         *
         * @param bitmapCache
         * @return
         */
        public Builder setCachePolicy(ICache bitmapCache) {
            config.bitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         *
         * @param loadPolicy
         * @return
         */
        public Builder setLoadPolicy(IPolicy loadPolicy) {
            config.loadPolicy = loadPolicy;
            return this;
        }

        /**
         * 设置线程数
         *
         * @param threadCount
         * @return
         */
        public Builder setThreadCount(int threadCount) {
            config.threadCount = threadCount;
            return this;
        }

        /**
         * 设置图片加载显示配置
         *
         * @param displayConfig
         * @return
         */
        public Builder setDisplayConfig(AbstractDisplay displayConfig) {
            config.displayConfig = displayConfig;
            return this;
        }

        /**
         * 构建
         *
         * @return
         */
        public LoaderConfig build() {
            return config;
        }
    }

    public AbstractDisplay getDisplayConfig() {
        return displayConfig;
    }

    public ICache getBitmapCache() {
        return bitmapCache;
    }

    public IPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }
}
