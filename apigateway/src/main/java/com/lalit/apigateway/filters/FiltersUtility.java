package com.lalit.apigateway.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

// TODO: Implement filters utility class to manage and apply various filters for API Gateway
@Component
public class FiltersUtility {
	public static final String CORRELATION_ID = "lalit-correlation-id";
	
	public String getCorrelationId(HttpHeaders requestHeaders) {
		if (requestHeaders.get(CORRELATION_ID) != null) {
			List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
			return requestHeaderList.stream().findFirst().get();
		} else {
			return null;
		}
	}
	
	public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
		return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
	}
	public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
		return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
	}
	
}
