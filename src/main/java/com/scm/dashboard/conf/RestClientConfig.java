package com.scm.dashboard.conf;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by amqu on 2017/7/5.
 */
@Configuration
public class RestClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestClientConfig.class);

    @Bean(name="httpComponentsClientHttpRequestFactory")
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(getHttpClient());
        factory.setConnectTimeout(2*1000);
        factory.setReadTimeout(5*1000);
        return factory;
    }


    @Bean
    public CloseableHttpClient getHttpClient() {
        final ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy(){
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                return 5 * 1000;//设置一个链接的最大存活时间
            }

        };
        return HttpClientBuilder.create().setConnectionManager(getManager())
                .setKeepAliveStrategy(keepAliveStrategy).build();
    }

    @Bean
    public PoolingHttpClientConnectionManager getManager(){
        PoolingHttpClientConnectionManager connectionManager =new PoolingHttpClientConnectionManager();
        connectionManager.closeExpiredConnections();
        connectionManager.closeIdleConnections(10, TimeUnit.SECONDS);
        connectionManager.setDefaultMaxPerRoute(connectionManager.getDefaultMaxPerRoute());
        connectionManager.setMaxTotal(100);
        return connectionManager;
    }
}
