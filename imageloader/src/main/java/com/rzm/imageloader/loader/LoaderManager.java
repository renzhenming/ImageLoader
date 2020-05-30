package com.rzm.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * 加载器管理类
 */
public class LoaderManager {

    /**
     * 缓存加载器
     */
    private Map<String,Loader> mLoaders;

    /**
     * 在实例化对象的时候就将所有的加载器注册起来，不对外提供添加加载器的
     * 方法，有新的加载器则直接在这里注册.
     *
     * 这种思想可以参考安卓源码的各种服务注册的形式
     */
    private LoaderManager(){
        mLoaders = new HashMap<>();
        //key值是定义好的根据图片url获取加载器的时候先截取出前缀
        //http https file等，然后从map集合中获取对应的加载器
        register("http",new UrlLoader());
        register("https",new UrlLoader());
        register("file",new LocalLoader());
    }

    private static class Holder{
        private static LoaderManager manager = new LoaderManager();
    }

    public static LoaderManager getInstance(){
        return Holder.manager;
    }

    private void register(String schema, Loader loader) {
        mLoaders.put(schema,loader);
    }

    public Loader getLoader(String schema){
        if (schema == null){
            return null;
        }
        return mLoaders.get(schema);
    }
}
