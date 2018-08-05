package io.customer.customerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Boot Configuration class.
 * 
 * @author Viral Patel
 *
 */
@SpringBootApplication
public class CustomerapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerapiApplication.class, args);
	}

	/**
	 * RestTemplate for connecting to Backend CRM System. Default timeouts are
	 * configured here.
	 * 
	 * @return
	 */
	@Bean
	protected RestTemplate crmRestTemplate() {
		final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		// TODO: Externalize timeouts to application configuration

		requestFactory.setReadTimeout(10 * 1000); // 10 seconds
		requestFactory.setConnectTimeout(5 * 1000); // 5 seconds

		final ClientHttpRequestFactory httpFactory = new BufferingClientHttpRequestFactory(requestFactory);

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(httpFactory);

		return restTemplate;
	}

}
