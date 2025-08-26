package co.com.bancolombia.api.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("route-locator")
public class RouteLocatorConfig {

    /**
     * Configuración alternativa de rutas usando RouteLocatorBuilder
     * Esta configuración solo se activa con el perfil "route-locator"
     * y permite definir rutas de forma programática en lugar de usar YAML.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("autentication-service", r -> r
                        .path("/api/clientes/**")
                        .filters(f -> f
                                .rewritePath("/api/clientes/(?<segment>.*)", "/${segment}")
                                .circuitBreaker(config -> config
                                        .setName("clientesCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/clientes")))
                        .uri("solicitudes-service"))
                .route("creditos-service", r -> r
                        .path("/api/creditos/**")
                        .filters(f -> f
                                .rewritePath("/api/creditos/(?<segment>.*)", "/${segment}")
                                .circuitBreaker(config -> config
                                        .setName("creditosCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/creditos")))
                        .uri("http://localhost:8082"))
                .build();
    }
}
