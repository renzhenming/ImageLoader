package com.rzm.selectimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by renzhenming on 2018/3/15.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    //用来存放子View减少findViewById的次数
    private SparseArray<View> mViewArrays = new SparseArray<View>();

    public CommonViewHolder(View itemView) {
        super(itemView);
    }

    /**
     *  设置文本
     */
    public CommonViewHolder setText(int viewId,CharSequence text){
        TextView view = getView(viewId);
        if (view != null){
            view.setText(text);
        }
        return this;
    }

    /**
     * 设置显示
     */
    public CommonViewHolder setVisibility(int viewId,int visibility){
        View view = getView(viewId);
        if (view != null){
            view.setVisibility(visibility);
        }
        return this;
    }

    /**
     *  设置资源图片(imageview src)
     */
    public CommonViewHolder setImageViewSrc(int viewId,int resourceId){
        ImageView view = getView(viewId);
        if (view != null){
            view.setImageResource(resourceId);
        }
        return this;
    }

    /**
     *  设置资源图片background(view background)
     */
    public CommonViewHolder setBackgroundResource(int viewId,int resourceId){
        View view = getView(viewId);
        if (view != null){
            view.setBackgroundResource(resourceId);
        }
        return this;
    }

    /**
     * 设置网络图片
     */
    public CommonViewHolder setImageUrl(int viewId,ImageLoader loader){
        ImageView view = getView(viewId);
        if (view != null){
            if (loader == null){
                throw new NullPointerException("image loader is null");
            }
            loader.displayImage(view.getContext(),view,loader.getmImageUrl());
        }
        return this;
    }

    /**
     * 加载网络图片考虑到使用的第三方不同，做特殊处理
     */
    public static abstract class ImageLoader{
        private final String mImageUrl;

        public ImageLoader(String imageUrl) {
            this.mImageUrl = imageUrl;
        }

        public String getmImageUrl() {
            return mImageUrl;
        }

        public abstract void displayImage(Context context, ImageView imageView, String url);
    }

    /**
     * 条目点击事件
     * @param listener
     * @return
     */
    public CommonViewHolder setOnItemClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
        return this;
    }

    /**
     * 条目长按点击事件
     * @param listener
     * @return
     */
    public CommonViewHolder setOnItemLongClickListener(View.OnLongClickListener listener){
        itemView.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 设置itemView中子view的点击事件
     * @return
     */
    public CommonViewHolder setOnItemChildViewClickListener(int viewId, View.OnClickListener listener){
        View view = getView(viewId);
        if (view != null){
            view.setOnClickListener(listener);
        }
        return this;
    }

    //获取view
    public <R extends View> R getView(int viewId){
        View view = mViewArrays.get(viewId);
        if (view == null){
            view = itemView.findViewById(viewId);
            mViewArrays.put(viewId,view);
        }
        return (R)view;
    }
}
