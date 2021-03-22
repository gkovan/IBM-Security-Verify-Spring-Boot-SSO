package com.gkovan.client.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

//@Configuration
public class WebClientConfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(WebClientConfiguration.class);
	
	@Bean
	public WebClient webClientWithTimeout() {
		
		WebClient webClient = WebClient.builder()
				  .filter(logRequest())
				  .codecs(configurer -> configurer.defaultCodecs().enableLoggingRequestDetails(true))
				  .build();
		
		System.out.println("GK webclient bean created");
		
		return webClient;		
	}
	
    // This method returns filter function which will log request data
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
        	System.out.println("GK99");
            LOG.info("GK Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> LOG.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }
}