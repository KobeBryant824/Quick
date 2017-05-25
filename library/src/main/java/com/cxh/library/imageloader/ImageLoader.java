package com.cxh.library.imageloader;

import android.graphics.Bitmap;
import android.os.Handler;


/**
 * 自定义图片加载
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class ImageLoader {
	private NetCacheUtil nCacheUtils;
	private LocalCacheUtil lCacheUtils;
	private MemoryCacheUtil mCacheUtils;

	public ImageLoader(Handler handler) {
		lCacheUtils = new LocalCacheUtil();
		mCacheUtils = new MemoryCacheUtil();
		nCacheUtils = new NetCacheUtil(handler, lCacheUtils, mCacheUtils);
	}

	/** 根据Url取图片  */
	public Bitmap getBitmapFromUrl(String imageUrl, int position) {
		// 1. 先根据Url去内存中取, 如果取到直接返回.
		Bitmap bm = mCacheUtils.getBitmapFromMemory(imageUrl);
		if (bm != null) {
			System.out.println("从内存中取到的");
			return bm;
		}
		// 2. 再根据Url去本地中取, 如果取到直接返回.
		bm = lCacheUtils.getBitmapFromLocal(imageUrl);
		if (bm != null) {
			System.out.println("从本地中取到的");
			return bm;
		}
		// 3. 最后去网络中取, 取到之后发送给主线程显示.
		System.out.println("从网络中取到的");
		nCacheUtils.getBitmapFromNet(imageUrl, position);
		return null;
	}

}