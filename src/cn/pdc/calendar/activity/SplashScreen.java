package cn.pdc.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import cn.pdc.calendar.R;
import cn.pdc.calendar.utils.Utils;

public class SplashScreen extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		// splash screen 2 seconds
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				SharedPreferences data = getSharedPreferences("sdata",
						0);
				Intent intent = null;
				String uname = data.getString("uname", null);
				String uid = data.getString("uid", null);
				if (uname == null || uid == null) {
					intent = new Intent(SplashScreen.this, LoginActivity.class);
				} else {
					Utils.uname = uname;
					Utils.uid = uid;
					intent = new Intent(SplashScreen.this, MainActivity.class);
				}
				SplashScreen.this.startActivity(intent);
				SplashScreen.this.finish();
				// overridePendingTransition(R.anim.my_scale_action,
				// R.anim.my_alpha_action);
			}
		}, 2000);
	}
}
