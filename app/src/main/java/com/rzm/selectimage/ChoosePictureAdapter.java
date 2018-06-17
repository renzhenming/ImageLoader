package com.rzm.selectimage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rzm.imageloader.R;
import com.rzm.imageloader.loader.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ChoosePictureAdapter extends CommonRecyclerAdpater<String> {
    // 选择图片的集合
    private ArrayList<String> mResultImageList;
    private int mMaxCount;
    public ChoosePictureAdapter(Context context, List<String> data, ArrayList<String> imageList, int maxCount) {
        super(context, data, R.layout.media_chooser_item);
        this.mResultImageList = imageList;
        this.mMaxCount = maxCount;
    }


    @Override
    public void bindHolder(ViewHolder holder, final String item, int position) {
        if(TextUtils.isEmpty(item)){
            // 显示拍照
            holder.setVisibility(R.id.camera_ll, View.VISIBLE);
            holder.setVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setVisibility(R.id.image, View.INVISIBLE);

            holder.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onStartCamara();
                    }
                }
            });
        }else{
            // 显示图片
            holder.setVisibility(R.id.camera_ll, View.INVISIBLE);
            holder.setVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setVisibility(R.id.image, View.VISIBLE);

            // 显示图片利用Glide
            ImageView imageView = holder.getView(R.id.image);
            /*Glide.with(mContext).load(item)
                    .centerCrop().into(imageView);*/
            String path ="file://"+item;
            SimpleImageLoader.getInstance().display(imageView,item);
            ImageView selectIndicatorIv = holder.getView(R.id.media_selected_indicator);

            if(mResultImageList.contains(item)){
                // 点亮选择勾住图片
                selectIndicatorIv.setSelected(true);
            }else{
                selectIndicatorIv.setSelected(false);
            }

            // 给条目增加点击事件
            holder.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    // 没有就加入集合，有就移除集合
                    if (!mResultImageList.contains(item)) {

                        // 不能大于最大的张数，自己改动的打个1
                        if(mResultImageList.size()>=mMaxCount){
                            // 自自定义Toast  文字写在string里面
                            Toast.makeText(mContext, "最多只能选取" + mMaxCount + "张图片"
                                    , Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mResultImageList.add(item);
                    } else {
                        mResultImageList.remove(item);
                    }

                    notifyDataSetChanged();

                    // 通知显示布局
                    if(mListener != null){
                        mListener.onSelect();
                    }
                }
            });
        }
    }

    // 设置选择图片监听
    private ChoosePictureListener mListener;
    public void setOnSelectImageListener(ChoosePictureListener listener){
        this.mListener = listener;
    }

}
