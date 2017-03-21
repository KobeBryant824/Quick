package com.cxh.library.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxh.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView通用适配器
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    private List<T> mDatas = new ArrayList<>();

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public List<T> getDataSource() {
        return mDatas;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void clearAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void insertData(T newItem) {
        mDatas.add(newItem);
        notifyItemRangeInserted(getItemCount(), 1);
    }

    public void insertData(List<T> newItems) {
        int size = getItemCount();
        mDatas.addAll(newItems);
        if (size == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(size, newItems.size());
        }
    }

    public void delDate(int i) {
        mDatas.remove(i);
        notifyItemRemoved(i);
        if (i != mDatas.size()) {
            notifyItemRangeChanged(i, mDatas.size() - i);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTv.setText(mDatas.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.tv)    // lib中的资源id不是final类型的 --> public static int tv=0x7f0a005b;
        TextView mTv;

        ViewHolder(View view) {
            super(view);
//            ButterKnife.bind(this, view);
            mTv = ((TextView) view.findViewById(R.id.tv));
        }
    }

}