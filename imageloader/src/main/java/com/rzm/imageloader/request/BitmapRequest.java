package com.rzm.imageloader.request;

import android.widget.ImageView;
import com.rzm.imageloader.display.AbstractDisplay;
import com.rzm.imageloader.loader.SimpleImageLoader;
import com.rzm.imageloader.policy.IPolicy;
import com.rzm.imageloader.utils.Md5Util;
import java.lang.ref.SoftReference;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:23
 * Description:This is BitmapRequest
 */
public class BitmapRequest implements Comparable<BitmapRequest>{

    /**
     * 展示配置
     */
    private AbstractDisplay disPlayConfig;
    /**
     * 加载策略
     */
    private IPolicy loadPolicy = SimpleImageLoader.getInstance().getConfig().getLoadPolicy();

    /**
     * 序列号，用于顺序比较
     */
    private int serialNum;

    /**
     * 持有ImageView的软引用
     */
    private SoftReference<ImageView> imageReference;

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

    public BitmapRequest(ImageView imageView,String imageUrl,AbstractDisplay displayConfig) {
        this(imageView,imageUrl,displayConfig,null);
    }

    public BitmapRequest(ImageView imageView,String imageUrl,SimpleImageLoader.ImageListener imageListener) {
        this(imageView,imageUrl,null,imageListener);
    }

    public BitmapRequest(ImageView imageView, String imageUrl, AbstractDisplay displayConfig,
                         SimpleImageLoader.ImageListener imageListener){
        this.imageReference = new SoftReference<ImageView>(imageView);
        if (imageUrl != null) {
            imageView.setTag(imageUrl);
            imageUrlMd5 = Md5Util.toMD5(imageUrl);
        }
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
     * @return
     */
    @Override
    public int compareTo(BitmapRequest o) {
        return loadPolicy.compareTo(o,this);
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
        if (imageReference == null)
            return null;
        return imageReference.get();
    }

    public AbstractDisplay getDisPlayConfig() {
        return disPlayConfig;
    }

    public IPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public SoftReference<ImageView> getImageReference() {
        return imageReference;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageUrlMd5() {
        return imageUrlMd5;
    }

    public SimpleImageLoader.ImageListener getImageListener() {
        return imageListener;
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
