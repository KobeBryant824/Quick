package com.cxh.library.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 网络缓存
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class NetCacheUtil {
	public static final int SUCCESS = 0;
	public static final int FAILED = 1;
	private Handler mHandler;
	private ExecutorService threadPool;
	private LocalCacheUtil lCacheUtils;
	private MemoryCacheUtil mMemoryUtils;
	
	public NetCacheUtil(Handler handler, LocalCacheUtil lCacheUtils, MemoryCacheUtil mMemoryUtils) {
		this.mHandler = handler;
		this.lCacheUtils = lCacheUtils;
		this.mMemoryUtils = mMemoryUtils;
		// 创建一个线程池
		threadPool = Executors.newFixedThreadPool(10);
	}

	/** 使用子线程去请求网络, 把图片抓取回来, 再发送给主线程  */
	public void getBitmapFromNet(String imageUrl, int position) {
//		new Thread(new InternalRunnable(imageUrl, position)).start();
		threadPool.execute(new InternalRunnable(imageUrl, position));
	}

	class InternalRunnable implements Runnable {
		private String imageUrl;
		private int position;
		
		public InternalRunnable(String imageUrl, int position) {
			this.imageUrl = imageUrl;
			this.position = position;
		}

		@Override
		public void run() {
			HttpURLConnection conn = null ;
			try {
				conn = (HttpURLConnection) new URL(imageUrl).openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.connect();
				int responseCode = conn.getResponseCode();
				if(responseCode == 200) {
					InputStream is = conn.getInputStream();
					Bitmap bm = BitmapFactory.decodeStream(is);
					// 发送到主线程中显示
					Message msg = mHandler.obtainMessage();
					msg.obj = bm;
					msg.what = SUCCESS;
					msg.arg1 = position;
					mHandler.sendMessage(msg);
					// 向内存存一份
					mMemoryUtils.addBitmap2Memory(imageUrl, bm);
					// 向本地存一份
					lCacheUtils.addBitmap2Local(imageUrl, bm);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Message msg = mHandler.obtainMessage();
				msg.what = FAILED;
				msg.arg1 = position;
				mHandler.sendMessage(msg);
			}finally{
				if (conn != null) {
					conn.disconnect();
				}
			}
		}
	}
}
