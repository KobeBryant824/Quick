package com.cxh.library.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class DBUtils {
	
	public static SQLiteDatabase openDatabase(Context context , int ID) {
		try {
			// 获得文件的绝对路径
			String databaseFilename = context.getFilesDir() + "/" + "sqlite.db";
			File file = new File(databaseFilename);
			if (!file.exists()) {
				file.mkdir();
			}
			if (!(new File(databaseFilename)).exists()) {
				InputStream is = context.getResources().openRawResource(ID);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
			return database;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
