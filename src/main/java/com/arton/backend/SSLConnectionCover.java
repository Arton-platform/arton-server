package com.arton.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

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


    public static JsonNode getUserInfo(String accessToken) {
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
                url = new URL("https://kapi.kakao.com/v2/user/me");
                http = new HttpPost(url.toURI());
                http.setHeader("Authorization", "Bearer "+accessToken);
            } catch (Exception e) {
                http = new HttpPost(url.toURI());
            }

            HttpResponse response = httpClient.execute(http);
            String s = new BasicResponseHandler().handleResponse(response);
            System.out.println("getUserInfo = " + s);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    // naver

    public static String getAccessTokenNaver(String clientId, String clientSecret, String code, String state) {
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
                url = new URL("https://nid.naver.com/oauth2.0/token");
                http = new HttpPost(url.toURI());
                // post by json
                http.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            } catch (Exception e) {
                http = new HttpPost(url.toURI());
            }

            String reqParam = "grant_type=authorization_code"+ "&"+
                    "client_id="+clientId+"&"+
                    "client_secret="+clientSecret+"&" +
                    "code="+code+"&"+
                    "state="+state;


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

    public static JsonNode getUserInfoNaver(String accessToken) {
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
                url = new URL("https://openapi.naver.com/v1/nid/me");
                http = new HttpPost(url.toURI());
                http.setHeader("Authorization", "Bearer "+accessToken);
            } catch (Exception e) {
                http = new HttpPost(url.toURI());
            }

            HttpResponse response = httpClient.execute(http);
            String s = new BasicResponseHandler().handleResponse(response);
            System.out.println("getUserInfo = " + s);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }


}
