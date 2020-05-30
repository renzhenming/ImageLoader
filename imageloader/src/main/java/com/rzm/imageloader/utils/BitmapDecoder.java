package com.rzm.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片解码器，基类作用只是获取相应的Option
 */
public abstract class BitmapDecoder {

    /**
     * 压缩图片
     * @param width imageView的宽度
     * @param height imageView的高度
     * @return
     */
    public Bitmap decodeBitmap(int width,int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true 只读取图片的宽高，不需要将整个图片都加载到内存
        options.inJustDecodeBounds = true;
        //decodeBitmapWithOptions(options);
        //经过上面一次操作，此时options中已经有了宽高信息
        calculateSampleSizeWithOptions(options,width,height);
        //第二次就可以得到缩放后的bitmap了
        return decodeBitmapWithOptions(options);
    }

    /**
     * 将图片宽高和控件宽高进行比较，得到缩放值，信息仍然存储在options中
     * @param options
     * @param viewWidth  imageView的宽度
     * @param viewHeight imageView的高度
     */
    private void calculateSampleSizeWithOptions(BitmapFactory.Options options,int viewWidth,int viewHeight) {
        //图片的原始宽高
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        //当图片的宽高大于控件的宽高时才需要压缩
        if (width > viewWidth || height > viewHeight){
            //计算出宽高的缩放比例
            int widthRatio = Math.round((float) width/(float)viewWidth);
            int heightRatio = Math.round((float)height/(float)viewHeight);
            //取宽高缩放比较大的值为图片的缩放比
            inSampleSize = Math.max(widthRatio,heightRatio);
        }else{
            //TODO 当图片小于imageView时，放大图片
        }
        //设置到options中，options保存的是配置信息
        //当inSampleSize为2，图片的宽高会缩放为原来的1/2
        options.inSampleSize = inSampleSize;
        //每个像素2个字节
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //宽高已经计算出来了，inJustDecodeBounds值可以复位了
        options.inJustDecodeBounds = false;
        //当系统内存不足时.可以回收bitmap
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

    /**
     * 将流的处理通过抽象方法暴露出来，降低解码器和外部的耦合
     * @param options
     */
    public abstract Bitmap decodeBitmapWithOptions(BitmapFactory.Options options);
}
