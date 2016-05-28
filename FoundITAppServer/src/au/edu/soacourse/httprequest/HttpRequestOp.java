package au.edu.soacourse.httprequest;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpRequestOp {

	public HttpResponse makePostRequest(String uri, String json) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("Content-Type", "application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		httppost.setEntity(new StringEntity(json));
		CloseableHttpResponse hresponse = httpclient.execute(httppost);
		return hresponse;
	}
	
	public HttpResponse makeGetRequest(String uri) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("Accept", "application/json; charset=utf-8");
		CloseableHttpResponse hresponse = httpclient.execute(httpget);
		return hresponse;
	}
}
