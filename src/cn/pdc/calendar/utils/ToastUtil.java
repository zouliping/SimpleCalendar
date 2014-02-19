package cn.pdc.calendar.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	/**
	 * 显示长时间Toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showLongToast(Context context, CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示短时间Toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showShortToast(Context context, CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
