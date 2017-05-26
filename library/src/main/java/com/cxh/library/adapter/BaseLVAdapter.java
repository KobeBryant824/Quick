package com.cxh.library.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ListView 通用适配器
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public abstract class BaseLVAdapter<T> extends BaseAdapter {

    private Context mContext;
    protected List<T> mDataList = new ArrayList<>();

    public BaseLVAdapter(Context context) {
        mContext = context;

    }

    public BaseLVAdapter(Context context, List<T> list) {
        mContext = context;
        mDataList = list;
    }

    @Override
    public int getCount() {
        return mDataList.size() == 0 ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(mContext, getLayoutId(), parent);

        ViewHolder holder = getHolder(convertView);

        setData(position, holder);

        return convertView;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 设置新的数据
     *
     * @param list
     */
    public void setDataList(Collection<T> list) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 增加新的数据
     *
     * @param list
     */
    public void addAll(Collection<T> list) {
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDataList.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        return holder;
    }

    private static class ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;

        public ViewHolder(View view) {
            mViews = new SparseArray<>();
            mConvertView = view;
        }

        /**
         * 通过 viewId 获取控件
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(@IdRes int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void setData(int position, ViewHolder holder);

}
