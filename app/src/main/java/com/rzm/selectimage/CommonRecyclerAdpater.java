package com.rzm.selectimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by renzhenming on 2018/3/15.
 */

public abstract class CommonRecyclerAdpater<R> extends RecyclerView.Adapter<CommonRecyclerAdpater.ViewHolder> {

    protected final Context mContext;
    private final LayoutInflater mInflater;
    private final List<R> mList;
    private int mResourceId=-1;
    private MultiTypeSupport<R> mMultiTypeSupport=null;
    //存放当个item中不同child view的点击事件
    private SparseArray<OnItemChildClickListener> mListenerArray = new SparseArray<OnItemChildClickListener>();

    public CommonRecyclerAdpater(Context context, List<R> mDataList, int resourseId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = mDataList;
        this.mResourceId = resourseId;
    }

    public CommonRecyclerAdpater(Context context, List<R> mDataList, MultiTypeSupport<R> multiTypeSupport) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = mDataList;
        this.mMultiTypeSupport = multiTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiTypeSupport != null){
            //将布局id作为type类型返回
            return mMultiTypeSupport.getLayoutId(mList.get(position),position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //如果设置了多布局类型，在这里回调的viewType就是布局的layoutId
        if (mMultiTypeSupport != null){
            mResourceId = viewType;
        }

        View view = mInflater.inflate(mResourceId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonRecyclerAdpater.ViewHolder holder, final int position) {
        if (mItemClickListener != null){
            holder.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(mList.get(position),position);
                }
            });
        }
        if (mLongClickListener != null){
            holder.setOnItemLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(mList.get(position),position);
                    return false;
                }
            });
        }

        if (mListenerArray.size() > 0) {
            for (int i = 0; i < mListenerArray.size(); i++) {
                final int finalI = i;
                holder.setOnItemChildViewClickListener(mListenerArray.keyAt(i),new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        mListenerArray.valueAt(finalI).onItemChildClick(mList.get(position),position);
                    }
                });
            }
        }

        bindHolder(holder,mList.get(position),position);
    }

    //交给子类实现
    public abstract void bindHolder(ViewHolder holder, R list, int position);

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends CommonViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 设置条目点击和长按事件
     */
    public OnItemClickListener mItemClickListener;
    public OnLongClickListener mLongClickListener;

    /**
     * 设置item点击事件
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    /**
     * 设置item长按点击事件
     * @param longClickListener
     */
    public void setOnLongClickListener(OnLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
    }

    /**
     * 设置item中的child view的点击事件,可设置多个
     * @param viewId
     * @param listener
     */
    public void setOnItemChildClickListener(int viewId , OnItemChildClickListener listener) {
        mListenerArray.put(viewId,listener);
    }

    public interface OnItemClickListener<R>{
        void onItemClick(R r, int position);
    }

    public interface OnLongClickListener<R>{
        void onItemLongClick(R r, int position);
    }

    public interface OnItemChildClickListener<R>{
        void onItemChildClick(R r, int position);
    }
}
