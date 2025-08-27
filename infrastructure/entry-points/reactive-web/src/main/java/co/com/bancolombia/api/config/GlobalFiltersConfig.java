package co.com.bancolombia.api.config;

import java.util.UUID;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Log4j2
@Configuration
public class GlobalFiltersConfig {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return new GlobalFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                
                // Agregar un ID de correlación para rastrear las solicitudes
                String requestId = UUID.randomUUID().toString();
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-Request-ID", requestId)
                        .build();
                
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(mutatedRequest)
                        .build();
                
                // Registrar información sobre la solicitud entrante
                log.info("Request ID: {}", requestId);
                log.info("Path: {}", request.getPath());
                log.info("Method: {}", request.getMethod());
                log.info("Headers: {}", request.getHeaders());
                
                return chain.filter(mutatedExchange)
                        .then(Mono.fromRunnable(() -> {
                            // Registrar información sobre la respuesta saliente
                            log.info("Response status for request {}: {}", 
                                    requestId, exchange.getResponse().getStatusCode());
                        }));
            }
        };
    }
}
