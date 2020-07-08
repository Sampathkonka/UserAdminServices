package com.okta.UserAdminServices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;

@Configuration
public class OktaConnectionObject {
	
	@Value("${okta.client.url}")
	private String oktaURL;
	@Value("${okta.client.token}")
	private String token;
	
	@Bean
    public Client client()  {    	
		Client client = Clients.builder()
			    .setOrgUrl(oktaURL)  // e.g https://dev-123456.okta.com
			    .setClientCredentials(new TokenClientCredentials(token))
			    .build();
		return client;
    }

}
