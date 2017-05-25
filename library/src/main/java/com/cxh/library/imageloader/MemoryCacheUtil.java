package com.cxh.library.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;


/**
 * 内存缓存
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class MemoryCacheUtil {
	private LruCache<String, Bitmap> mLruCache;
	
	public MemoryCacheUtil() {
		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。 
		// LruCache通过构造函数传入缓存值，以KB为单位
		int maxMemory = (int) (Runtime.getRuntime().maxMemory());
		// 使用最大可用内存值的1/8作为缓存的大小
		int cacheSize  = maxMemory / 8;
		mLruCache =  new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量
				return bitmap.getByteCount();
			}
		};
	}
	
	/** 向内存中存一张图片  */
	public void addBitmap2Memory(String imgUrl, Bitmap bm) {
		if (getBitmapFromMemory(imgUrl) == null) {
			mLruCache.put(imgUrl, bm);
		}
	}
	
	/** 从内存中取一张图片  */
	public Bitmap getBitmapFromMemory(String imgUrl) {
		return mLruCache.get(imgUrl);
	}
}
