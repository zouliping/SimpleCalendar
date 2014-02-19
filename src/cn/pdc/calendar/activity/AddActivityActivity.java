package cn.pdc.calendar.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.pdc.calendar.R;
import cn.pdc.calendar.entity.MyActivity;
import cn.pdc.calendar.utils.HttpUtil;
import cn.pdc.calendar.utils.ToastUtil;
import cn.pdc.calendar.utils.Utils;

public class AddActivityActivity extends Activity {

	private ImageView iv_back;
	private Button btn_add;

	private EditText et_title;
	private EditText et_start;
	private EditText et_end;
	private EditText et_description;
	private EditText et_location;

	private String title;
	private String start;
	private String end;
	private String description;
	private String location;

	private Boolean isSuccess = false;

	private MyActivity tmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add);

		initData();
		initViews();
	}

	private void initData() {
		Intent intent = getIntent();
		String jsonString = intent.getStringExtra("info");
		if (jsonString != null) {
			tmp = new MyActivity();
			try {
				JSONObject jo = new JSONObject(jsonString);
				tmp.setDescription(jo.getString("description"));
				tmp.setEnd(jo.getString("end"));
				tmp.setLocation(jo.getString("location"));
				tmp.setStart(jo.getString("start"));
				tmp.setTitle(jo.getString("title"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void initViews() {
		iv_back = (ImageView) findViewById(R.id.back_btn);
		iv_back.setOnClickListener(listener);
		btn_add = (Button) findViewById(R.id.add);
		btn_add.setOnClickListener(listener);

		et_description = (EditText) findViewById(R.id.description);
		et_end = (EditText) findViewById(R.id.to);
		et_location = (EditText) findViewById(R.id.location);
		et_start = (EditText) findViewById(R.id.from);
		et_title = (EditText) findViewById(R.id.title);

		if (tmp != null) {
			et_description.setText(tmp.getDescription());
			et_end.setText(tmp.getEnd());
			et_location.setText(tmp.getLocation());
			et_start.setText(tmp.getStart());
			et_title.setText(tmp.getTitle());
		}
	}

	private void getData() {
		description = et_description.getText().toString();
		title = et_title.getText().toString();
		start = et_start.getText().toString();
		end = et_end.getText().toString();
		location = et_location.getText().toString();
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_btn:
				finish();
				break;
			case R.id.add:
				getData();
				new putDataTask().execute("");
				if (isSuccess) {
					finish();
				}
				break;
			default:
				break;
			}
		}
	};

	private class putDataTask extends AsyncTask<String, String, Boolean> {

		ProgressDialog dlg;

		@Override
		protected void onPreExecute() {
			dlg = new ProgressDialog(AddActivityActivity.this);
			dlg.setTitle("Add Activity");
			dlg.setMessage("Please wait for a monent...");
			dlg.setCancelable(false);
			dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dlg.show();

		}

		@Override
		protected Boolean doInBackground(String... params) {
			JSONObject jo = new JSONObject();
			try {
				jo.put("classname", "Activity");
				jo.put("individualname", title + System.currentTimeMillis());
				jo.put("uid", Utils.uid);
				jo.put("title", title);
				jo.put("start_time", start);
				jo.put("end_time", end);
				jo.put("description", description);
				jo.put("location", location);

				JSONObject result = new JSONObject(HttpUtil.doPut(
						Utils.ADD_ACTIVITY, jo));
				isSuccess = (Boolean) result.get("result");
				return isSuccess;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			dlg.dismiss();
			if (result == false) {
				ToastUtil.showShortToast(AddActivityActivity.this, "Failed");
			} else {
				ToastUtil.showShortToast(AddActivityActivity.this, "Success");
			}
		}
	}
}
