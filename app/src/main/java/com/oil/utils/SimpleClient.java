package com.oil.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * 获取web端session
 */
public class SimpleClient {
	private static HttpParams httpParams;
	private static DefaultHttpClient httpClient;
	private static String JSESSIONID; //定义一个静态的字段，保存sessionID

	/**
	 * 实例化httpClient
	 */
	public static HttpClient getHttpClient() throws Exception {
		// 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
		httpParams = new BasicHttpParams();
		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(httpParams, true);
		// 设置 user agent
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		// 创建一个 HttpClient 实例
		// 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient
		// 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
		httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	/**
	 * 发送post请求
	 */
	public static String doPost(String url, List<NameValuePair> params) throws Exception {
		/* 建立HTTPPost对象 */
		HttpPost httpRequest = new HttpPost(url);
		String strResult = "doPostError";
		/* 添加请求参数到请求对象 */
		if (params != null && params.size() > 0) {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		}
		if(null != JSESSIONID){
			httpRequest.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
		}
		/* 发送请求并等待响应 */
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		/* 若状态码为200 ok */
		if (httpResponse.getStatusLine().getStatusCode() != 0) {
			/* 读返回数据 */
			strResult = EntityUtils.toString(httpResponse.getEntity());
			/* 获取cookieStore */
			CookieStore cookieStore = httpClient.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			//解析出session
			for(int i=0;i<cookies.size();i++){
				if("JSESSIONID".equals(cookies.get(i).getName())){
					JSESSIONID = cookies.get(i).getValue();
					System.out.println(JSESSIONID);
					break;
				}
			}
		}
		return JSESSIONID;
	}

}