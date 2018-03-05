package http;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * http请求
 * Created by Baxter on 2015/4/28.
 */
public class SimpleHttpClient {

    final static Logger log = LoggerFactory.getLogger(SimpleHttpClient.class);

    private String url;

    private int retryTimes;

    private boolean isRetry;

    public SimpleHttpClient(String url) {
        this(url, 0);
    }

    public SimpleHttpClient(String url, int retryTimes) {
        this.url = url;
        this.retryTimes = retryTimes;
        this.isRetry = retryTimes == 0;
    }

    public String doGet() {
        HttpClient httpClient = new HttpClient();
        GetMethod get = new GetMethod(url);
        get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
        String result = "";
        try {
            int statusCode = httpClient.executeMethod(get);
            if (statusCode == HttpStatus.SC_OK) {
                result = get.getResponseBodyAsString();
            }
        } catch (IOException e) {
            log.error("", e);
        }finally {
            get.releaseConnection();
        }

        return result;
    }


/*
    public String doPost(JSONObject object) {
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
        for (Object key : object.keySet()) {
            String value = (String) object.get((String) key);
            post.addParameter((String) key, value);
        }
        String result = "";
        try {
            int statusCode = httpClient.executeMethod(post);
            if (statusCode == HttpStatus.SC_OK) {
                result = post.getResponseBodyAsString();
            }
        } catch (IOException e) {
            log.error("", e);
        }finally {
            post.releaseConnection();
        }
        return result;

    }*/

    public String doPost(Map<String, String> param, String charset) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
        for (String key : param.keySet()) {
            post.addParameter(key, param.get(key));
        }
        try {
            int statusCode = httpClient.executeMethod(post);
            if (statusCode == HttpStatus.SC_OK) {
                result = post.getResponseBodyAsString();
            }
        } catch (IOException e) {
            log.error("", e);
        }finally {
            post.releaseConnection();
        }
        return result;

    }

    public String doPost(String xml, String charset) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
        try {
            post.setRequestEntity(new StringRequestEntity(xml, "xml/text", charset));
            int statusCode = httpClient.executeMethod(post);
            if (statusCode == HttpStatus.SC_OK) {
                result = post.getResponseBodyAsString();
            }
        } catch (Exception e) {
            log.error("", e);
        }finally {
            post.releaseConnection();
        }
        return result;
    }

    public String doPost(String xml,String contentType, String charset) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
        try {
            post.setRequestEntity(new StringRequestEntity(xml, contentType, charset));
            log.warn("before{}", xml);
            int statusCode = httpClient.executeMethod(post);
            if (statusCode == HttpStatus.SC_OK) {
                result = post.getResponseBodyAsString();
            }
            log.warn("after{}\t{}", xml, result);
        } catch (Exception e) {
            log.error("", e);
        }finally {
            post.releaseConnection();
        }
        return result;
    }

   /* public PostMethod doPost(Map<String, String> params, String charset) {
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
        for (String key : params.keySet()) {
            post.addParameter(key, params.get(key));
        }
        try {
            httpClient.executeMethod(post);
        } catch (IOException e) {
            log.error("", e);
        }
        return post;
    }*/


    public String doGetWithProxy(String host, int port) {
        try {
            URI uri = new URI(url, true);
            HttpClient client = new HttpClient();
            HostConfiguration hcfg = new HostConfiguration();
            hcfg.setHost(uri);
            client.setHostConfiguration(hcfg);
            setProxy(client, host, port);
            // ������֤
            client.getParams().setAuthenticationPreemptive(true);
            // GET����ʽ
            HttpMethod method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (Exception e) {
            log.error("", e);
        }
        return "";

    }

    public String doPostWithProxy(String data, String charset, String host, int port) {
        PostMethod method = null;
        try {
            URI uri = new URI(url, true);
            HttpClient client = new HttpClient();
            HostConfiguration cf = new HostConfiguration();

            cf.setHost(uri);
            client.setHostConfiguration(cf);
            setProxy(client, host, port);

            client.getParams().setAuthenticationPreemptive(true);
            method = new PostMethod(url);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(this.retryTimes, this.isRetry));
            //��Ӳ���
            method.setRequestEntity(new StringRequestEntity(data, "xml/text", charset));
            client.executeMethod(method);
            // ������ص���Ϣ
            return method.getResponseBodyAsString();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            // �ͷ�����
            if(method!=null)
                method.releaseConnection();
        }
        return "";

    }


    private void setProxy(HttpClient client, String host, int port) {
        // ���ô���
        client.getHostConfiguration().setProxy(host, port);

    }



}
