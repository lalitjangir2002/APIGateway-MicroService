package com.lalit.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator lalitCustomRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/lalit/accounts/**")
						.filters(f->f.rewritePath("/lalit/accounts/(?<segment>.*)","/${segment}")
								     .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				        .uri("lb://ACCOUNTS"))
				.route(p-> p
						.path("/lalit/loans/**")
						.filters(f -> f.rewritePath("/lalit/loans/(?<segment>.*)" , "/${segment}")
								       .addResponseHeader("X-Response-Time",LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/lalit/cards/**")
						.filters(f -> f.rewritePath("/lalit/cards/(?<segment>.*)","/${segment}")
								       .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				        .uri("lb://CARDS")).build();
	}

}
