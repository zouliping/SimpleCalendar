package cn.pdc.calendar.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import cn.pdc.calendar.R;
import cn.pdc.calendar.entity.MyActivity;
import cn.pdc.calendar.utils.HttpUtil;
import cn.pdc.calendar.utils.Utils;
import cn.pdc.calendar.view.ActivityAdapter;

public class MainActivity extends Activity {

	private Context mContext = MainActivity.this;

	private ListView lv_activity;
	private List<MyActivity> activities;
	private ActivityAdapter adapter;
	private ImageView btn_add;
	private ImageView btn_logout;

	private MyActivity activity;
	private String title;
	private String start;
	private String end;
	private String description;
	private String location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initData();
		initViews();
	}

	// private void initStaticData() {
	// activities = new ArrayList<MyActivity>();
	// for (int i = 0; i < 10; i++) {
	// MyActivity activity = new MyActivity("title" + i, "2013-12-0" + i,
	// "2013-12-1" + i, "description" + i + "...........",
	// "location" + i);
	// activities.add(activity);
	// }
	// }

	private void initData() {
		activities = new ArrayList<MyActivity>();
		new getDataTask().execute("");
	}

	private void initViews() {
		lv_activity = (ListView) findViewById(R.id.activity_list);
		adapter = new ActivityAdapter(mContext, activities);
		lv_activity.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		btn_add = (ImageView) findViewById(R.id.add_btn);
		btn_add.setOnClickListener(listener);
		btn_logout = (ImageView) findViewById(R.id.logout_btn);
		btn_logout.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_btn:
				Intent intent = new Intent(mContext, AddActivityActivity.class);
				startActivity(intent);
				break;
			case R.id.logout_btn:
				SharedPreferences data = mContext.getSharedPreferences("data",
						0);
				Editor editor = data.edit();
				editor.clear();
				editor.commit();

				Intent i = new Intent(mContext, LoginActivity.class);
				startActivity(i);
				break;
			default:
				break;
			}
		}
	};

	private class getDataTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> list_params = new LinkedList<NameValuePair>();
			list_params.add(new BasicNameValuePair("classname", "Activity"));
			list_params.add(new BasicNameValuePair("uid", Utils.uid));
			list_params.add(new BasicNameValuePair("uname", Utils.uname));
			list_params.add(new BasicNameValuePair("sid", Utils.sid));
			String query = URLEncodedUtils.format(list_params, "utf-8");

			return HttpUtil.doGet(Utils.GET_ACTIVITY + query);
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				if (result == null) {
					return;
				}

				JSONObject jo = new JSONObject(result);
				for (Iterator<?> i = jo.keys(); i.hasNext();) {
					String key = (String) i.next();
					JSONObject jactivity = jo.getJSONObject(key);

					title = getString(R.string.undefined);
					start = getString(R.string.undefined);
					end = getString(R.string.undefined);
					description = getString(R.string.undefined);
					location = getString(R.string.undefined);

					if (!jactivity.isNull("-a_title")) {
						title = jactivity.getString("-a_title");
						if (title.contains("^^")) {
							title = title.substring(0, title.indexOf("^^"));
						}
					}
					if (!jactivity.isNull("-a_start_time")) {
						start = jactivity.getString("-a_start_time");
						if (start.contains("^^")) {
							start = start.substring(0, start.indexOf("^^"));
						}
					}
					if (!jactivity.isNull("-a_end_time")) {
						end = jactivity.getString("-a_end_time");
						if (end.contains("^^")) {
							end = end.substring(0, end.indexOf("^^"));
						}
					}
					if (!jactivity.isNull("-a_description")) {
						description = jactivity.getString("-a_description");
						if (description.contains("^^")) {
							description = description.substring(0,
									description.indexOf("^^"));
						}
					}
					if (!jactivity.isNull("-a_location")) {
						location = jactivity.getString("-a_location");
						if (location.contains("^^")) {
							location = location.substring(0,
									location.indexOf("^^"));
						}
					}

					activity = new MyActivity(title, start, end, description,
							location);
					activities.add(activity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				adapter.setActivityList(activities);
				adapter.notifyDataSetChanged();
			}
		}
	}

}
