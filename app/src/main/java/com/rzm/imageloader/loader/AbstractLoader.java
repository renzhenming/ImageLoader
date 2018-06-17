package com.rzm.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.rzm.imageloader.cache.BitmapCache;
import com.rzm.imageloader.config.DisplayConfig;
import com.rzm.imageloader.request.BitmapRequest;
import com.rzm.imageloader.utils.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:22
 * Description:This is AbstractLoader
 * 加载器策略不同，则不同的加载器实现方式不同，但是他们有相同的操作，比如显示
 * 占位图等，所以这些相同操作在抽象一层出来
 */
public abstract class AbstractLoader implements Loader {

    private static final String TAG = "AbstractLoader";

    private AtomicInteger integer = new AtomicInteger(0);
    /**
     * 加载器加载图片的逻辑是先缓存后网络，所以需要持有缓存对象的引用
     */
    private BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();

    /**
     * 同样因为要处理显示时的逻辑，所以需要持有显示配置对象的引用
     */
    private DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();

    @Override
    public void load(BitmapRequest request) {
        //从缓存中获取Bitmap
        Bitmap bitmap= null;
        if (bitmapCache != null) {
            bitmap = bitmapCache.get(request);
        }
        if (bitmap == null){
            //显示加载中图片
            showLoadingImg(request);
            //开始加载网络图，加载的逻辑不同加载器有所不同，所以交给各自
            //加载器实现，抽象
            bitmap = onLoad(request);
            if (bitmap == null){
                while(integer.incrementAndGet() <=3){
                    LogUtils.d(TAG,"加载失败，重连第"+integer+"次");
                    bitmap = onLoad(request);
                    if (bitmap != null){
                        break;
                    }
                }
                integer.set(0);
            }
            if (bitmap == null){
                LogUtils.d(TAG,"重连3次失败，放弃加载");
            }
            //加入缓存
            if (bitmapCache != null && bitmap != null)
                cacheBitmap(request,bitmap);
        }else{
            //有缓存
        }
        deliveryToUIThread(request,bitmap);
    }

    public abstract Bitmap onLoad(BitmapRequest request);

    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if(imageView!=null)
        {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }

            });
        }

    }

    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常  防止图片错位
        if(bitmap != null && imageView.getTag().equals(request.getImageUrl())){
            imageView.setImageBitmap(bitmap);
        }
        //有可能加载失败
        if(bitmap == null && displayConfig!=null&&displayConfig.errorImage!=-1){
            imageView.setImageResource(displayConfig.errorImage);
        }
        //监听
        //回调 给圆角图片  特殊图片进行扩展
        if(request.getImageListener() != null){
            request.getImageListener().onComplete(imageView, bitmap, request.getImageUrl());
        }
    }
    /**
     * 缓存图片
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null){
            synchronized (AbstractLoader.class){
                bitmapCache.put(request,bitmap);
            }
        }
    }

    /**
     * 显示加载中占位图,需要判断用户有没有配置
     * @param request
     */
    private void showLoadingImg(BitmapRequest request) {
        if (hasLoadingPlaceHolder()){
            final ImageView imageView = request.getImageView();
            if (imageView != null){
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImage);
                    }
                });
            }
        }
    }

    /**
     * 是否设置了加载中图片
     * @return
     */
    private boolean hasLoadingPlaceHolder() {
        return displayConfig != null && displayConfig.loadingImage > 0;
    }
}
