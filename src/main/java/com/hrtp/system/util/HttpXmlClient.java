package com.hrtp.system.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hrtp.salesAppService.controller.MerchManagerController;

public class HttpXmlClient {
	 private static Logger log = LoggerFactory.getLogger(HttpXmlClient.class);
     
	  public static String post(String url, String params) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient(); 
	        httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1500);
	        httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000*10);
	        String body = null;  
	        try{
	        	log.info("create httppost:" + url);  
	        	HttpPost post = postForm(url, params);  
	        	
	        	body = invoke(httpclient, post);  
	        	
	        	httpclient.getConnectionManager().shutdown();  
	        }catch (Exception e) {
	        	log.info("http请求(json)异常; url"+url+";params"+params+";e:"+e);
			}
	        return body;  
	    }
	  public static String postForJson(String url, String params) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient(); 
	        httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1500);
	        httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000*10);
	        String body = null;  
	        try{
	        	log.info("create httppost:" + url);  
	        	HttpPost post = postFormForJson(url, params);  
	        	
	        	body = invoke(httpclient, post);  
	        	
	        	httpclient.getConnectionManager().shutdown();  
	        }catch (Exception e) {
	        	log.info("http请求(json)异常; url"+url+";params"+params+";e:"+e);
			}
	        return body;  
	    }
	    public static String post(String url, Map<String, String> params) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient(); 
	        httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1500);
	        httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000*10);
	        String body = null;  
	        try{
		        log.info("create httppost:" + url);  
		        HttpPost post = postForm(url, params);  
		          
		        body = invoke(httpclient, post);  
		          
		        httpclient.getConnectionManager().shutdown();  
	        }catch (Exception e) {
	        	log.info("http请求(map)异常; url"+url+";e:"+e);
			} 
	        return body;  
	    }  
	      
	    public static String get(String url) {  
	        DefaultHttpClient httpclient = new DefaultHttpClient();  
	        String body = null;  
	          
	        log.info("create httppost:" + url);  
	        HttpGet get = new HttpGet(url);  
	        body = invoke(httpclient, get);  
	          
	        httpclient.getConnectionManager().shutdown();  
	          
	        return body;  
	    }  
	          
	      
	    private static String invoke(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	          
	        HttpResponse response = sendRequest(httpclient, httpost);  
	        String body = paseResponse(response);  
	          
	        return body;  
	    }  
	  
	    private static String paseResponse(HttpResponse response) {  
	        log.info("get response from http server..");  
	        HttpEntity entity = response.getEntity();  
	          
	        log.info("response status: " + response.getStatusLine());  
	        String charset = EntityUtils.getContentCharSet(entity);  
	        log.info(charset);  
	          
	        String body = null;  
	        try {  
	            body = EntityUtils.toString(entity);  
	            log.info(body);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return body;  
	    }  
	  
	    private static HttpResponse sendRequest(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	        log.info("execute post...");  
	        HttpResponse response = null;  
	          
	        try {  
	            response = httpclient.execute(httpost);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return response;  
	    }  
	    private static HttpPost postForm(String url, String params){  
	          
	        HttpPost httpost = new HttpPost(url);  
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	          
//	        Set<String> keySet = params.keySet();  
//	        for(String key : keySet) {  
//	            nvps.add(new BasicNameValuePair(key, params.get(key)));  
//	        }  
	          
	        try {  
	            log.info("set utf-8 form entity to httppost");  
	            httpost.setEntity((new StringEntity(params,"UTF-8")) );
	            //httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	          
	        return httpost;  
	    } 
	    private static HttpPost postFormForJson(String url, String params){  
	          
	        HttpPost httpost = new HttpPost(url);  
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	          
//	        Set<String> keySet = params.keySet();  
//	        for(String key : keySet) {  
//	            nvps.add(new BasicNameValuePair(key, params.get(key)));  
//	        }  
	          
	        try {  
	            log.info("set utf-8 form entity to httppost");  
	            httpost.setEntity((new StringEntity(params,"UTF-8")) );
	            httpost.setHeader("Content-Type", "application/json");
	            //httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	          
	        return httpost;  
	    } 
	    private static HttpPost postForm(String url, Map<String, String> params){  
	          
	        HttpPost httpost = new HttpPost(url);  
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	          
	        Set<String> keySet = params.keySet();  
	        for(String key : keySet) {  
	            nvps.add(new BasicNameValuePair(key, params.get(key)));  
	        }  
	          
	        try {  
	            log.info("set utf-8 form entity to httppost");  
	            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return httpost;  
	    }  
}
