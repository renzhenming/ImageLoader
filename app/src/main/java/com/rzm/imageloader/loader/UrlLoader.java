package com.rzm.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.rzm.imageloader.request.BitmapRequest;
import com.rzm.imageloader.utils.BitmapDecoder;
import com.rzm.imageloader.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:22
 * Description:This is UrlLoader
 * 网络图片加载器
 */
public class UrlLoader extends AbstractLoader {

    @Override
    public Bitmap onLoad(BitmapRequest request) {
        try {
            String imageUrl = request.getImageUrl();
            if (TextUtils.isEmpty(imageUrl)){
                return null;
            }
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200){
                return null;
            }
            InputStream inputStream = conn.getInputStream();
            //转化成BufferedInputStream
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            //标记一下，reset后会重置到这个位置
            bufferedInputStream.mark(inputStream.available());
            BitmapDecoder decoder = new BitmapDecoder() {
                @Override
                public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                    //第一次读取，因为设置了inJustDecodeBounds为true，所以，这里decodeStream之后，会将宽高
                    //信息存储在options中；第二次读取，因为设置了inJustDecodeBounds为false.所以会将流全部读取
                    Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream,null,options);
                   if (options.inJustDecodeBounds){
                       //表示时第一次执行，此时只是为了获取Bounds
                       try {
                           //第一次读取图片宽高信息，读完之后，要为第二次读取做准备，将流重置
                           bufferedInputStream.reset();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }else{
                       try {
                           bufferedInputStream.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
                   return bitmap;
                }
            };
            //传入控件的宽高，设置图片适应控件
            return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                    ImageViewHelper.getImageViewHeight(request.getImageView()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
