package com.rzm.imageloader.utils;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * 用于获取imageView宽高信息
 */
public class ImageViewHelper {

    private static final int DEFAULT = 200;

    public static int getImageViewWidth(ImageView imageView) {
        if (imageView != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int width = 0;

            //宽度在布局文件中指定了具体的
            if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = imageView.getWidth();
            }

            //如果没有在布局文件中指定，可能时在代码中通过LayoutParams指定的
            if (width <= 0 && params != null) {
                width = params.width;
            }

            //如果仍然获取不到，那么可能时对这个ImageView设置了maxWidth值
            if (width <= 0) {
                width = getImageViewFieldValue(imageView, "mMaxWidth");
            }
            return width;
        }
        return DEFAULT;
    }

    public static int getImageViewHeight(ImageView imageView) {
        if (imageView != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int height = 0;

            //宽度在布局文件中指定了具体的
            if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                height = imageView.getHeight();
            }

            //如果没有在布局文件中指定，可能时在代码中通过LayoutParams指定的
            if (height <= 0 && params != null) {
                height = params.height;
            }

            //如果仍然获取不到，那么可能时对这个ImageView设置了maxWidth值
            if (height <= 0) {
                height = getImageViewFieldValue(imageView, "mMaxHeight");
            }
            return height;
        }
        return DEFAULT;
    }

    /**
     * 通过反射获取ImageView的最大宽度
     *
     * @param imageView
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(ImageView imageView, String fieldName) {
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (int) field.get(imageView);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                return fieldValue;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
