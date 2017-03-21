package edu.zcs.com.educationsys.util.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

	public static final String HOST2 = "http://192.168.56.1:8080";
	public static final String HOST3 = "http://192.168.191.1:8080";
	public static final String HOST = "http://115.159.121.34:8080";
	public static final String ImageHOST=HOST2+"/Edu/img/";
	
	public static void setURL(String Url){
		
		DefaultHttpClient httpClient = null;
		HttpParams paramsw = HttpUtils.createHttpParams();
		HttpPost request = new HttpPost();
		
		try {
			request.setURI(new URI(Url));
			httpClient = new DefaultHttpClient(paramsw);
			HttpResponse httpResponse = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
 
	public static JSONObject getJsonObject(String Url) {

		DefaultHttpClient httpClient = null;
		try {
			HttpParams paramsw = createHttpParams();
			httpClient = new DefaultHttpClient(paramsw);

			HttpPost request = new HttpPost();
			request.setURI(new URI(Url));

			HttpResponse httpResponse = httpClient.execute(request);

			int httpCode = httpResponse.getStatusLine().getStatusCode();

			if (httpCode == HttpURLConnection.HTTP_OK && httpResponse != null) {
				HttpEntity entity = httpResponse.getEntity();

				InputStream inputStream = entity.getContent();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader reader = new BufferedReader(inputStreamReader);
				String s;
				StringBuffer result = new StringBuffer();
				while (((s = reader.readLine()) != null)) {
					result.append(s);
				}
				reader.close();

				JSONObject jsonObject = JSON.parseObject(result.toString());

				return jsonObject;

			} else {
				Log.e("url response", "Error Response");
			}
		} catch (UnsupportedEncodingException e) {
			Log.e("url response", " UnsupportedEncodingException");
		} catch (ClientProtocolException e) {
			Log.e("url response", " ClientProtocolException");
		} catch (IOException e) {
			Log.e("url response", " IOException");
		} catch (URISyntaxException e) {
			Log.e("url response", " Response");
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		}
		return null;
	}

	public static JSONObject getJsonObject(String Url, Map<String, String> params) {

		DefaultHttpClient httpClient = null;
		try {
			HttpParams paramsw = createHttpParams();
			httpClient = new DefaultHttpClient(paramsw);

			HttpPost request = new HttpPost();
			request.setURI(new URI(Url));

			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Entry<String, String> entry : params.entrySet()) {
					NameValuePair p1 = new BasicNameValuePair(entry.getKey(), entry.getValue());
					list.add(p1);
				}
			}
			request.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));

			HttpResponse httpResponse = httpClient.execute(request);

			int httpCode = httpResponse.getStatusLine().getStatusCode();

			if (httpCode == HttpURLConnection.HTTP_OK && httpResponse != null) {
				HttpEntity entity = httpResponse.getEntity();

				InputStream inputStream = entity.getContent();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader reader = new BufferedReader(inputStreamReader);
				String s;
				StringBuffer result = new StringBuffer();
				while (((s = reader.readLine()) != null)) {
					result.append(s);
				}
				reader.close();

				JSONObject jsonObject = JSON.parseObject(result.toString());

				return jsonObject;

			} else {
				Log.e("url response", "Error Response");
			}
		} catch (UnsupportedEncodingException e) {
			Log.e("url response", " UnsupportedEncodingException");
		} catch (ClientProtocolException e) {
			Log.e("url response", " ClientProtocolException");
		} catch (IOException e) {
			Log.e("url response", " IOException");
		} catch (URISyntaxException e) {
			Log.e("url response", " Response");
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		}
		return null;
	}

	public static final HttpParams createHttpParams() {
		final HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
		HttpConnectionParams.setSoTimeout(params, 60 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192 * 5);
		return params;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	public static Bitmap getUrlImage(String url) {
		Bitmap img = null;
		try {
			URL picurl = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) picurl.openConnection();
			conn.setConnectTimeout(6000);// 设置超时
			conn.setDoInput(true);
			conn.setUseCaches(true);
			conn.connect();
			InputStream is = conn.getInputStream();// 获得图片的数据流
			img = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * 检查当前网络是否可用
	 * 
	 * @param
	 * @return
	 */

	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					System.out.println(i + "===状态===" + networkInfo[i].getState());
					System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}