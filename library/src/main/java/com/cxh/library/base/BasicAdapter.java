package com.cxh.library.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * ListView通用适配器
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    protected List<T> mList;
    protected Context mContext;

    public BasicAdapter(List<T> list, Context context) {
        super();
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size() == 0 ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView();
    }

	/*private ViewHolder getHolder(View convertView){
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		if(viewHolder==null){
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		return viewHolder;
	}*/
	
	/*class ViewHolder{
		TextView index;
		public ViewHolder(View convertView){
			index = (TextView) convertView.findViewById(R.id.index);
		}
	}*/

    protected abstract View initView();

}
