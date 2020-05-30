package com.rzm.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.rzm.imageloader.request.BitmapRequest;
import com.rzm.imageloader.utils.BitmapDecoder;
import com.rzm.imageloader.utils.ImageViewHelper;

import java.io.File;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:22
 * Description:This is LocalLoader
 * 本地图片加载器
 */
public class LocalLoader extends AbstractLoader {

    @Override
    public Bitmap onLoad(BitmapRequest request) {
        //得到本地图片的路径
        final String path = Uri.parse(request.getImageUrl()).getPath();
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path, options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                ImageViewHelper.getImageViewHeight(request.getImageView()));
    }
}
