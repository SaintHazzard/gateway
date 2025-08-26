package co.com.bancolombia.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/clientes")
    public Mono<ResponseEntity<String>> clientesFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de clientes no está disponible en este momento. Por favor, intente más tarde."));
    }

    @GetMapping("/creditos")
    public Mono<ResponseEntity<String>> creditosFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de créditos no está disponible en este momento. Por favor, intente más tarde."));
    }
}
