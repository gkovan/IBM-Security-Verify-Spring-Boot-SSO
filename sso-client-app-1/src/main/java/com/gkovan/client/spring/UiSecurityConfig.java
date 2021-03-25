package com.gkovan.client.spring;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@EnableWebSecurity
public class UiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(UiSecurityConfig.class);
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
		http
			.authorizeRequests().antMatchers("/", "/login**").permitAll()
				.anyRequest().authenticated()
				.and()
			.oauth2Login()
				.tokenEndpoint().accessTokenResponseClient(authorizationCodeTokenResponseClient());

	}
    
	private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeTokenResponseClient() {
		
		OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter =
				new OAuth2AccessTokenResponseHttpMessageConverter();
		
		tokenResponseHttpMessageConverter.setTokenResponseConverter(new CustomAccessTokenResponseConverter());

		RestTemplate restTemplate = new RestTemplate(Arrays.asList(
				new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
		
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

		DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
		tokenResponseClient.setRestOperations(restTemplate);

		return tokenResponseClient;
	}

	/*****  References for the solution to get spring boot sso to work with IBM Security verify
	 
	   https://stackoverflow.com/questions/58629596/access-token-response-tokentype-cannot-be-null
	   https://github.com/spring-projects/spring-security/issues/5983	
	   https://docs.spring.io/spring-security/site/docs/5.1.1.RELEASE/reference/htmlsingle/#oauth2Client-access-token-client
	   https://github.com/jzheaux/messaging-app/blob/master/client-app/src/main/java/sample/web/CustomAccessTokenResponseConverter.java#L35
	   https://github.com/jzheaux/messaging-app/blob/master/client-app/src/main/java/sample/config/SecurityConfig.java#L61
	****/
	
    @Bean
    WebClient webClient(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, authorizedClientRepository);
        oauth2.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder()
            .apply(oauth2.oauth2Configuration())
            .filter(logRequest())   // log requests for web client
            .codecs(configurer -> configurer.defaultCodecs().enableLoggingRequestDetails(true))  // another way to log web client requests; also see properties in yaml file
            .build();
    }
    
    // This method returns filter function which will log request data
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            LOG.info("####### GK WebClient REQUEST: ####### {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> LOG.info("### GK WebClient HEADER ### {}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

}
