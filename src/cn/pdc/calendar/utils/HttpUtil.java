package cn.pdc.calendar.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtil {
	private static HttpClient client;

	/**
	 * get client
	 * 
	 * @return
	 */
	public static synchronized HttpClient getHttpClient() {
		if (null == client) {
			HttpParams params = new BasicHttpParams();
			// set some params
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");

//			ConnManagerParams.setTimeout(params, 10000);
//			HttpConnectionParams.setConnectionTimeout(params, 10000);
//			HttpConnectionParams.setSoTimeout(params, 10000);

			// set for http and https
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			client = new DefaultHttpClient(conMgr, params);
		}
		return client;
	}

	/**
	 * do get
	 * 
	 * @param uri
	 * @return
	 */
	public static String doGet(String uri) {
		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet(uri);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is = response.getEntity().getContent();
				return StringUtil.inputStream2String(is);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * do post
	 * 
	 * @param uri
	 * @param jo
	 * @return
	 */
	public static String doPost(String uri, JSONObject jo) {
		try {
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(uri);
			StringEntity se = new StringEntity(jo.toString());
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			request.setEntity(se);
			request.setHeader("Content-type", "application/json");
			HttpResponse response = client.execute(request);
			return EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * do put
	 * 
	 * @param uri
	 * @param jo
	 * @return
	 */
	public static String doPut(String uri, JSONObject jo) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPut request = new HttpPut(uri);
			StringEntity se = new StringEntity(jo.toString());
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			request.setEntity(se);
			request.setHeader("Content-type", "application/json");
			request.setEntity(se);
			HttpResponse response = client.execute(request);
			return EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
