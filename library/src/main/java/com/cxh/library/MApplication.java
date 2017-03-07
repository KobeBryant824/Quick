package com.cxh.library;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;


public class MApplication extends Application implements  Thread.UncaughtExceptionHandler {
	/** 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了 */
	private static MApplication mInstance;
	/** 主线程ID */
	private static int mMainThreadId = -1;
	/** 主线程 */
	private static Thread mMainThread;
	/** 主线程Handler */
	private static Handler mMainThreadHandler;
	/** 主线程Looper */
	private static Looper mMainLooper;

	private List<Activity> mActivityList = new LinkedList<Activity>();

	public static MApplication getContext() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		// android.os.Process.myTid() 获取调用进程的id
		// android.os.Process.myUid() 获取该进程的用户id
		// android.os.Process.myPid() 获取进程的id
		mMainThreadId = android.os.Process.myTid();
		mMainThread = Thread.currentThread();
		mMainThreadHandler = new Handler();
		mMainLooper = getMainLooper();
		mInstance = this;
		super.onCreate();

		/**
		 * 给当前线程，设置一个，全局异常捕获
		 * 说明：线程中，没有try catch的地方，抛了异常，都由该方法捕获
		 */
		Thread.currentThread().setUncaughtExceptionHandler(this);
	}

	/**
	 * 当应用崩溃的时候，捕获异常
	 * 1、该用应程序，在此处，必死无异，不能原地复活，只能，留个遗言，即，记录一下，崩溃的log日志，以便开发人员处理
	 * 2、将自己彻底杀死，早死早超生。
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		try {
			PrintStream printStream = new PrintStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/error.log");

			Class clazz = Class.forName("android.os.Build");
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				printStream.println(field.getName()+" : "+field.get(null));
			}
			String currTime = DateFormat.getDateFormat(getApplicationContext()).format(System.currentTimeMillis());

			printStream.println("TIME:"+currTime);
			printStream.println("==================华丽丽的分隔线================");
			ex.printStackTrace(printStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2、将自己彻底杀死
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/** 获取主线程ID */
	public static int getMainThreadId() {
		return mMainThreadId;
	}

	/** 获取主线程 */
	public static Thread getMainThread() {
		return mMainThread;
	}

	/** 获取主线程的handler */
	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	/** 获取主线程的looper */
	public static Looper getMainThreadLooper() {
		return mMainLooper;
	}

	public void addActivity(Activity activity) {
		mActivityList.add(activity);
	}

	/** 关闭保存的Activity */
	public void exitApp() {
		if (mActivityList.size() == 0) return;
		Activity activity;
		for (int i = 0; i < mActivityList.size(); i++) {
			activity = mActivityList.get(i);
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
