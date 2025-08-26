package co.com.bancolombia.api.config;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfig {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            // Agregar un ID de correlación para rastrear las solicitudes a través de los microservicios
            String requestId = UUID.randomUUID().toString();
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-Request-ID", requestId)
                    .build();
            
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();
            
            // Registrar información sobre la solicitud entrante
            System.out.println("Request ID: " + requestId);
            System.out.println("Path: " + request.getPath());
            System.out.println("Method: " + request.getMethod());
            
            return chain.filter(mutatedExchange)
                    .then(Mono.fromRunnable(() -> {
                        // Registrar información sobre la respuesta saliente
                        System.out.println("Response status: " + exchange.getResponse().getStatusCode());
                    }));
        };
    }
}
