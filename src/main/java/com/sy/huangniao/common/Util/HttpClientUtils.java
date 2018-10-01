package com.sy.huangniao.common.Util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

public final class HttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger( HttpClientUtils.class );

    public static String get(final String url, final Map<String, String> headerMap,int socketTimeout,int connectTimeout) {
        return get( url, null, headerMap , socketTimeout, connectTimeout);
    }

    public static String get(final String url, final Map<String, String> headerMap) {
        return get( url, headerMap , 30000,30000);
    }

    public static String post(final String url, final Map<String, String> params,
                              final Map<String, String> headerMap) {
        return post( url, params, headerMap, 30000, 30000);
    }
    public static String post(final String url, final Map<String, String> params,
                              final Map<String, String> headerMap,int socketTimeout,int connectTimeout) {
        return post( url, params, headerMap, "utf-8" , socketTimeout, connectTimeout);
    }
    public static String post(final String url, final Map<String, String> params) {
        return post( url, params,  30000, 3000);
    }

    public static String post(final String url, final Map<String, String> params,int socketTimeout,int connectTimeout) {
        return post( url, params, null, "utf-8" , socketTimeout, connectTimeout);
    }

    public static String get(final String url, final Map<String, String> params, final Map<String, String> headerMap,int socketTimeout,int connectTimeout) {
        HttpGet httpGet = null;
        try {
            URIBuilder ub = new URIBuilder( url );
            List<NameValuePair> pairs = Lists.newArrayList();

            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add( new BasicNameValuePair( entry.getKey(), entry.getValue().toString() ) );
                }

                ub.setParameters( pairs );
            }
            httpGet = new HttpGet( ub.build() );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return execute( httpGet, headerMap , socketTimeout, connectTimeout);
    }

    public static String post(final String url, final Map<String, String> params, final Map<String, String> headerMap,
                              final String charset,int socketTimeout,int connectTimeout) {
        final HttpPost request = new HttpPost( url );
        if (params != null && params.size() > 0) {
            List<NameValuePair> paramList = null;
            final Set<Map.Entry<String, String>> entrySet = params.entrySet();
            paramList = new ArrayList<NameValuePair>();
            for (final Iterator<Map.Entry<String, String>> it = entrySet.iterator(); it.hasNext(); ) {
                final Map.Entry<String, String> entry = it.next();
                final String key = entry.getKey();
                final Object value = entry.getValue();
                if (key != null && value != null) {
                    final NameValuePair nvp = new BasicNameValuePair( key, value.toString() );
                    paramList.add( nvp );
                }
            }
            try {
                if (StringUtils.isEmpty( charset )) {
                    request.setEntity( new UrlEncodedFormEntity( paramList ) );
                } else {
                    request.setEntity( new UrlEncodedFormEntity( paramList, Charset.forName( charset ) ) );
                }
            } catch (final Exception e) {
                logger.error( "HttpClientUtils post", e );
                return null;
            }
        }
        return execute( request, headerMap , socketTimeout, connectTimeout );
    }

    public static String post(final String url, final HttpEntity entity, final Map<String, String> headerMap,int socketTimeout,int connectTimeout) {
        final HttpPost request = new HttpPost( url );
        request.setEntity( entity );
        return execute( request, headerMap, socketTimeout, connectTimeout  );
    }

    public static String execute(final HttpRequestBase request, final Map<String, String> headerMap,int socketTimeout,int connectTimeout) {
        return execute( null, request, headerMap, socketTimeout, connectTimeout );
    }

    private static String execute(CloseableHttpClient httpclient, final HttpRequestBase request,
                                  final Map<String, String> headerMap,int socketTimeout,int connectTimeout) {
        final StringBuffer log = new StringBuffer(
            "HttpClientUtils execute  method:" + request.getMethod() + " url:" + request.getURI() );

        boolean isClose = false;
        if (httpclient == null) {
            httpclient = HttpClients.createDefault();
            isClose = true;
        }
        InputStream resStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String result = "";

        if (headerMap != null && headerMap.size() > 0) {
            final Iterator<String> iterator = headerMap.keySet().iterator();
            while (iterator.hasNext()) {
                final String key = iterator.next();
                request.setHeader( key, headerMap.get( key ) );
            }
        }

        try {
            final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout( socketTimeout ).setConnectTimeout(
                    connectTimeout )
                .build();
            request.setConfig( requestConfig );
            final CloseableHttpResponse response = httpclient.execute( request );

            try {

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    resStream = entity.getContent();
                    try {
                        inputStreamReader = new InputStreamReader( resStream );
                        br = new BufferedReader( inputStreamReader );
                        final StringBuffer resBuffer = new StringBuffer();
                        String resTemp = "";
                        while ((resTemp = br.readLine()) != null) {
                            resBuffer.append( resTemp );
                        }
                        result = resBuffer.toString();
                    } finally {
                        if (br != null) {
                            br.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                        if (resStream != null) {
                            resStream.close();
                        }
                    }
                }
            } finally {
                response.close();
            }
        } catch (final Exception e) {
            request.abort();
            logger.error( log.toString(), e );
        } finally {
            request.abort();
            try {
                if (isClose) {
                    httpclient.close();
                }
            } catch (final IOException e) {
                logger.error( log.toString(), e );
            }
        }
        return result;
    }

    public static   String respondString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-8"));
            br = new BufferedReader( inputStreamReader );
            final StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append( resTemp );
            }
           return resBuffer.toString();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }catch (Exception e){}

        }
        return  null;
    }
}
