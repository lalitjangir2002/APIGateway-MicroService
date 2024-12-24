package com.lalit.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Order(1) // once server starts this will be first to run
@Component
public class RequestTraceFilter implements GlobalFilter{
	private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);
	@Autowired
	public FiltersUtility filtersUtility;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { //these are defined with spring reactive model
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		if (isCorrelationIdPresent(requestHeaders)) {
			logger.debug("lalit-correlation-id found in RequestTraceFilter : {}",
					filtersUtility.getCorrelationId(requestHeaders));
		} else {
			String correlationID = generateCorrelationId();
			exchange = filtersUtility.setCorrelationId(exchange, correlationID);
			logger.debug("lalit-correlation-id generated in RequestTraceFilter : {}", correlationID);
		}
		return chain.filter(exchange);
	}
	
	private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
		if(filtersUtility.getCorrelationId(requestHeaders) != null){
			return true;
		} else{
			return false;
		}
	}
	
	private String generateCorrelationId(){
		return java.util.UUID.randomUUID().toString();
	}
}
