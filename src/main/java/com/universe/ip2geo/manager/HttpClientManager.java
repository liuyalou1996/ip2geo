package com.universe.ip2geo.manager;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convenient class for http invocation.
 * @author 刘亚楼
 * @date 2022/5/10
 */
@Slf4j
public class HttpClientManager {

	private final HttpClient httpClient;

	public HttpClientManager(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * 发送get请求
	 * @param url 资源地址
	 * @param params 请求参数
	 * @return
	 * @throws Exception
	 */
	public HttpClientResp get(String url, Map<String, Object> params) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);
		if (!CollectionUtils.isEmpty(params)) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				uriBuilder.setParameter(param.getKey(), String.valueOf(param.getValue()));
			}
		}

		URI uri = uriBuilder.build();
		HttpGet httpGet = new HttpGet(uri);
		return getResponse(httpGet);
	}

	/**
	 * 模拟表单发送post请求
	 * @param url 资源地址
	 * @param params 请求参数
	 * @return
	 * @throws IOException
	 */
	public HttpClientResp postInHtmlForm(String url, Map<String, Object> params) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		if (!CollectionUtils.isEmpty(params)) {
			List<NameValuePair> formParams = new ArrayList<>();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				formParams.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
		}

		return getResponse(httpPost);
	}

	/**
	 * 发送post请求，请求参数格式为json
	 * @param url 资源地址
	 * @param jsonStr 请求参数json字符串
	 * @return
	 * @throws IOException
	 */
	public HttpClientResp postInJson(String url, String jsonStr) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
		return getResponse(httpPost);
	}

	public static void setHeaders(AbstractHttpMessage message, Map<String, Object> headers) {
		if (!CollectionUtils.isEmpty(headers)) {
			for (Map.Entry<String, Object> header : headers.entrySet()) {
				message.setHeader(header.getKey(), String.valueOf(header.getValue()));
			}
		}
	}

	private HttpClientResp getResponse(HttpRequestBase request) throws IOException {
		try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request, HttpClientContext.create())) {
			HttpClientResp resp = new HttpClientResp();

			int statusCode = response.getStatusLine().getStatusCode();
			resp.setStatusCode(statusCode);
			if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
				Map<String, String> headers = new HashMap<>();
				for (Header header : response.getAllHeaders()) {
					headers.put(header.getName(), header.getValue());
				}

				HttpEntity httpEntity = response.getEntity();
				resp.setSuccessful(true);
				resp.setHeaders(headers);
				resp.setContentType(httpEntity.getContentType().getValue());
				resp.setContentLength(httpEntity.getContentLength());
				resp.setRespContent(EntityUtils.toString(httpEntity, Consts.UTF_8));
				if (httpEntity.getContentEncoding() != null) {
					resp.setContentEncoding(httpEntity.getContentEncoding().getValue());
				}
			}

			return resp;
		}

	}

	@Data
	public static class HttpClientResp {

		private int statusCode;
		private String respContent;
		private long contentLength;
		private String contentType;
		private String contentEncoding;
		private Map<String, String> headers;
		private boolean successful;
	}

}
