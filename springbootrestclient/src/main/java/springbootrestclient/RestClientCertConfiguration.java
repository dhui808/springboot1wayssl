package springbootrestclient;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientCertConfiguration {

	@Value("${server.ssl.key-store-password}")
    private String keystorepassword;
	
	@Value("${server.ssl.key-store}")
	private String keystore;
	
	@Value("${server.ssl.trust-store}")
	private String truststore;
	

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(ResourceUtils.getFile(keystore), keystorepassword.toCharArray(), keystorepassword.toCharArray())
                .loadTrustMaterial(ResourceUtils.getFile(truststore), keystorepassword.toCharArray())
                .build();

        /*
         * Create an HttpClient that uses the custom SSLContext
         */
        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        /*
         * Create a RestTemplate that uses a request factory that references
         * our custom HttpClient
         */
        return builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }
}