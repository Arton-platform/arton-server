package com.arton.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

public class SSLConnectionCover {

    // 30 seconds
    private static int CONN_TIME_OUT = 1000 * 30;

    public static String getAccessToken(String clientId, String uri, String code) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {

                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {

                    return null;
                }
            };

            sslContext.init(null, new TrustManager[] { trustManager },
                    new SecureRandom());
            SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 443, socketFactory);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);

            HttpParams httpParam = httpClient.getParams();
            org.apache.http.params.HttpConnectionParams.setConnectionTimeout(httpParam, CONN_TIME_OUT);
            org.apache.http.params.HttpConnectionParams.setSoTimeout(httpParam, CONN_TIME_OUT);

            HttpPost http = null;
            URL url = null;
            try {
                url = new URL("https://kauth.kakao.com/oauth/token");
                http = new HttpPost(url.toURI());
                // post by json
                http.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            } catch (Exception e) {
                http = new HttpPost(url.toURI());
            }

            String reqParam = "grant_type=authorization_code"+ "&"+
                    "client_id="+clientId+"&"+
                    "redirect_uri="+uri+"&" +"code="+code;


            StringEntity entity = new StringEntity(reqParam);
            http.setEntity(entity);
            HttpResponse response = httpClient.execute(http);
            String s = new BasicResponseHandler().handleResponse(response);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(s).get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}
