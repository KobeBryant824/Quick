package com.cxh.library.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 原生http请求
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class HttpRequestUtil {
	private static final int TIMEOUT_IN_MILLIONS = 5000;

	public interface CallBack {
		void onRequestComplete(String result);
	}

	/** 异步的Get请求 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					String result = doGet2Str(urlStr);
					if (callBack != null)
						callBack.onRequestComplete(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/** 异步的Post请求 */
	public static void doPostAsyn(final String urlStr, final String params, final CallBack callBack) {
		new Thread() {
			public void run() {
				try {
					String result = doPost2Str(urlStr, params);
					if (callBack != null)
						callBack.onRequestComplete(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 *            "?name=" + URLEncoder.encode(name, "utf-8")
	 * @return
	 */
	public static InputStream doGet2Is(String urlStr) {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			int code = conn.getResponseCode();
			if (code == 200) {
				in = conn.getInputStream();
				return in;
			} else {
				LogUtil.e("访问失败，状态码是" + code);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(in);
			if (null != conn) {
				conn.disconnect();
			}
		}
		return null;
	}

	public static String doGet2Str(String urlStr) {
		try {
			return IOUtil.input2Str(doGet2Is(urlStr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 向指定 URL发送POST方法的请求
	 * 
	 * @param urlStr
	 * @param param
	 *            "name=" + URLEncoder.encode(name, "utf-8")
	 * @return
	 */
	public static InputStream doPost2Is(String urlStr, String param) {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Length", param.length() + "");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStream out = conn.getOutputStream();
			out.write(param.getBytes());
			out.close();
			int code = conn.getResponseCode();
			if (code == 200) {
				in = conn.getInputStream();
				return in;
			} else {
				LogUtil.e("访问失败，状态码是" + code);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(in);
			if (null != conn) {
				conn.disconnect();
			}
		}
		return null;
	}

	public static String doPost2Str(String urlStr, String param) {
		try {
			return IOUtil.input2Str(doPost2Is(urlStr, param));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
