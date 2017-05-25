package com.cxh.library.imageloader;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.cxh.library.util.FileUtils;
import com.cxh.library.util.MD5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * 本地缓存
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class LocalCacheUtil {
	private final String CACHE_DIR = FileUtils.getCacheDir();
	
	/** 根据Url存储当前图片  */
	public void addBitmap2Local(String imgUrl, Bitmap bm) {
		if (getBitmapFromLocal(imgUrl) == null) {
			try {
				String fileName = MD5Utils.encrypt(imgUrl);
				File file = new File(CACHE_DIR, fileName);
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdir();
				}
				FileOutputStream out = new FileOutputStream(file);
				bm.compress(CompressFormat.JPEG, 100, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 根据Url从本地获取图片  */
	public Bitmap getBitmapFromLocal(String imgUrl) {
		try {
			String fileName = MD5Utils.encrypt(imgUrl);
			File file = new File(CACHE_DIR, fileName);
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				Bitmap bm = BitmapFactory.decodeStream(in);
				return bm;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
