package com.cxh.library.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;


/**
 * 本应用数据清除管理器(主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录)
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class DataCleanUtils {

	/** 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/** 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
	}

	/** 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)  */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
	}

	/** 按名字清除本应用数据库  */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/** 清除/data/data/com.xxx.xxx/files下的内容  */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/** 清除SDCard外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/** 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除  */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/** 清除本应用所有的数据  */
	public static void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/** 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理  */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}
}
