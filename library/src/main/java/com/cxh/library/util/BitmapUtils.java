package com.cxh.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class BitmapUtils {

	/** 图片圆角处理  */
	public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
		Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(roundConcerImage);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null, rect, paint);
		return roundConcerImage;
	}

	/** 图片画圆处理  */
	public static Bitmap getCroppedBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		int halfWidth = bitmap.getWidth() / 2;
		int halfHeight = bitmap.getHeight() / 2;
		canvas.drawCircle(halfWidth, halfHeight, Math.max(halfWidth, halfHeight), paint); // 以最大值在中心点画圆切图，可以改为最小值
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/** 把bitmap转换成base64 */
	public static String getBase64FromBitmap(Bitmap bitmap, int bitmapQuality) {
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
		byte[] bytes = bStream.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	/** 把base64转换成bitmap */
	public static Bitmap getBitmapFromBase64(String string) {
		byte[] bitmapArray = null;
		try {
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
	}

	/** 获取本地图片 */
	public static Bitmap getBitmapFromLocal(Context context, String srcPath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDither = false; // 图片不抖动
		opts.inPurgeable = true; // 允许回收内存
		opts.inInputShareable = true; // 和inPurgeable配合使用，如果inPurgeable是false，那么该参数将被忽略，表示是否对bitmap的数组进行共享
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, opts);
		int srcH = opts.outHeight;
		int srcW = opts.outWidth;
		LogUtils.i("srcH:" + srcH + "---srcW: " + srcW);
		WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int screenH = display.getHeight();
		int screenW = display.getWidth();
		LogUtils.i("screenH:" + screenH + "---screenW: " + screenW);
		int sy = srcH / screenH;
		int sx = srcW / screenW;
		int scale = 1;
		if (sx >= sy && sx >= 1) {
			scale = sx;
		}
		if (sy >= sx && sy >= 1) {
			scale = sy;
		}
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(srcPath, opts);
		return bitmap;
	}

	/** 获取网络图片 */
	public static Bitmap getBitmapFromNet(Context context, String imgUrl, String imgPath) {
		Bitmap bitmap = null;
		FileOutputStream fos = null;
		try {
			URL picUrl = new URL(imgUrl);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inDither = false; // 图片不抖动
			opts.inPurgeable = true; // 允许回收内存
			opts.inInputShareable = true; // 和inPurgeable配合使用，如果inPurgeable是false，那么该参数将被忽略，表示是否对bitmap的数组进行共享
			opts.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeStream(picUrl.openStream(), null, opts);
			int srcH = opts.outHeight;
			int srcW = opts.outWidth;
			LogUtils.i("srcH:" + srcH + "---srcW: " + srcW);
			WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			int screenH = display.getHeight();
			int screenW = display.getWidth();
			LogUtils.i("screenH:" + screenH + "---screenW: " + screenW);
			int sy = srcH / screenH;
			int sx = srcW / screenW;
			int scale = 1;
			if (sx >= sy && sx >= 1) {
				scale = sx;
			}
			if (sy >= sx && sy >= 1) {
				scale = sy;
			}
			opts.inJustDecodeBounds = false;
			opts.inSampleSize = scale;
			bitmap = BitmapFactory.decodeStream(picUrl.openStream(), null, opts);
			fos = new FileOutputStream(imgPath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
}
