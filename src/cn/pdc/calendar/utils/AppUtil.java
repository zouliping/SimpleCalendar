package cn.pdc.calendar.utils;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.view.Window;
import android.view.WindowManager;

public class AppUtil {

	/**
	 * set no title and no status bar
	 * 
	 * @param activity
	 */
	public static void setNoTitleAndNoStatusBarScreen(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * set no title
	 * 
	 * @param activity
	 */
	public static void setNotTitleScreen(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * open a app
	 * 
	 * @param packagename
	 */
	public static void openApp(Context mContext, String packagename) {
		try {
			PackageInfo pi = mContext.getPackageManager().getPackageInfo(
					packagename, 0);
			Intent intent = new Intent(Intent.ACTION_MAIN, null);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setPackage(pi.packageName);

			List<ResolveInfo> riList = mContext.getPackageManager()
					.queryIntentActivities(intent, 0);
			ResolveInfo ri = riList.iterator().next();

			if (ri != null) {
				String pn = ri.activityInfo.packageName;
				String cn = ri.activityInfo.name;

				Intent i = new Intent(Intent.ACTION_MAIN);
				i.addCategory(Intent.CATEGORY_LAUNCHER);

				ComponentName componentName = new ComponentName(pn, cn);

				i.setComponent(componentName);
				mContext.startActivity(i);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			ToastUtil.showShortToast(mContext, "You have not installed Taobao");
		}
	}
}
