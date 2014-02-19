package cn.pdc.calendar.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_btn:
				Intent intent = new Intent(mContext, AddActivityActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	private class getDataTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			return HttpUtil.doGet(Utils.GET_ACTIVITY + Utils.uid);
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

					if (!jactivity.isNull("title")) {
						title = jactivity.getString("title");
						if (title.contains("^^")) {
							title = title.substring(0, title.indexOf("^^"));
						}
					}
					if (!jactivity.isNull("start_time")) {
						start = jactivity.getString("start_time");
					}
					if (!jactivity.isNull("end_time")) {
						end = jactivity.getString("end_time");
					}
					if (!jactivity.isNull("description")) {
						description = jactivity.getString("description");
						if (description.contains("^^")) {
							description = description.substring(0,
									description.indexOf("^^"));
						}
					}
					if (!jactivity.isNull("location")) {
						location = jactivity.getString("location");
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
