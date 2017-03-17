package com.cxh.library.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


public class TextViewUtils {

	/**
	 * 设置部分文字颜色和大小
	 * 
	 * @param context
	 * @param textView
	 * @param str
	 *            设置的文字
	 * @param start
	 *            开始的位置
	 * @param end
	 *            结束的位置
	 * @param color
	 *            设置的颜色
	 * @param flag
	 *            是否需要设置文字的大小
	 * @param spSize
	 *            设置文字的大小
	 */
	public static void setTextColorAndSize(Context context, TextView textView,
                                           String str, int start, int end, int color, boolean flag, int spSize) {
		SpannableString ss = new SpannableString(str);
		if (flag) {
			ss.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(context, spSize)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		ss.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		textView.setText(ss);
	}

	/**
	 * 两个地方设置文字颜色
	 */
	public static void setTextColorAndSize(Context context, TextView textView,
                                           String str, int start, int end, int color, boolean flag,
                                           int spSize, int start1, int end1, int color1, boolean flag1,
                                           int spSize1) {
		SpannableString ss = new SpannableString(str);
		if (flag) {
			ss.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(context, spSize)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		if (flag1) {
			ss.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(context, spSize1)), start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		ss.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		ss.setSpan(new ForegroundColorSpan(color1), start1, end1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		textView.setText(ss);
	}
	/**
	 * 设置部分文字大小以及加粗
	 * 
	 * @param context
	 * @param textView
	 * @param str
	 *            设置的文字
	 * @param start
	 *            开始的位置
	 * @param end
	 *            结束的位置
	 * @param spSize
	 *            设置文字的大小
	 */
	public static void getSpanSpToPxColorAndSize(Context context, TextView textView,
                                                 String str, int start, int end, int color, int spSize) {
		SpannableString ss = new SpannableString(str);
		// 设置大小
		ss.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(context, spSize)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		//设置颜色
		ss.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		// 加粗
//		ss.setSpan(new MyStyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(ss);
	}

	/**
	 * 设置部分文字大小以及加粗
	 * 
	 * @param context
	 * @param textView
	 * @param str
	 *            设置的文字
	 * @param start
	 *            开始的位置
	 * @param end
	 *            结束的位置
	 * @param spSize
	 *            设置文字的大小
	 */
	public static void getSizeSpanSpToPx(Context context, TextView textView,
                                         String str, int start, int end, int spSize) {
		SpannableString ss = new SpannableString(str);
		// 设置大小
		ss.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(context, spSize)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 加粗
//		ss.setSpan(new MyStyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(ss);
	}

}
