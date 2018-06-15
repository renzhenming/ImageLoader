package com.rzm.imageloader.request;

import android.widget.ImageView;

import com.rzm.imageloader.config.DisplayConfig;
import com.rzm.imageloader.loader.SimpleImageLoader;
import com.rzm.imageloader.policy.LoadPolicy;

import java.lang.ref.SoftReference;
import java.util.Comparator;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:23
 * Description:This is BitmapRequest
 */
public class BitmapRequest implements Comparator<BitmapRequest>{

    /**
     * 展示配置
     */
    private DisplayConfig disPlayConfig;
    /**
     * 加载策略
     */
    private LoadPolicy loadPolicy = SimpleImageLoader.getInstance().getConfig().getLoadPolicy();

    /**
     * 序列号，用于顺序比较
     */
    private int serialNum;

    /**
     * 持有ImageView的软引用
     */
    private SoftReference<ImageView> imageViewSoftReference;

    /**
     * 图片路径
     */
    private String imageUrl;

    /**
     * 图片路径的md5值
     */
    private String imageUrlMd5;

    /**
     * 下载完成的监听
     */
    private SimpleImageLoader.ImageListener imageListener;

    public BitmapRequest() {

    }

    public BitmapRequest(ImageView imageView, String imageUrl) {
        this(imageView,imageUrl,null,null);
    }

    public BitmapRequest(ImageView imageView,String imageUrl,DisplayConfig displayConfig) {
        this(imageView,imageUrl,displayConfig,null);
    }

    public BitmapRequest(ImageView imageView,String imageUrl,SimpleImageLoader.ImageListener imageListener) {
        this(imageView,imageUrl,null,imageListener);
    }

    public BitmapRequest(ImageView imageView, String imageUrl, DisplayConfig displayConfig,
                         SimpleImageLoader.ImageListener imageListener){
        this.imageViewSoftReference = new SoftReference<ImageView>(imageView);
        imageView.setTag(imageUrl);
        this.imageUrl = imageUrl;
        if (displayConfig != null){
            this.disPlayConfig = displayConfig;
        }
        if (imageListener != null) {
            this.imageListener = imageListener;
        }
    }

    /**
     * 请求的先后顺序是根据加载的策略进行的，不同的策略比较的条件也不同，所以
     * 这里要把比较的逻辑交割具体的策略去做，策略有多种，所以通过接口调用，增强扩展性
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(BitmapRequest o1, BitmapRequest o2) {

        return loadPolicy.compareTo(o1,o2);
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * 获取这个请求对应的ImageView
     * @return
     */
    public ImageView getImageView(){
        if (imageViewSoftReference == null)
            return null;
        return imageViewSoftReference.get();
    }

    /**
     * BitmapRequest会被加入请求队列中，在队列中有需要做判断当前请求是否存在
     * 那么就涉及到这个对象的比较，所以需要重写hashCode和equals方法
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitmapRequest request = (BitmapRequest) o;
        return serialNum == request.serialNum &&
                loadPolicy.equals(request.loadPolicy);
    }

    @Override
    public int hashCode() {
        int result = loadPolicy != null ? loadPolicy.hashCode():0;
        result = 66*result+serialNum;
        return result;
    }
}
