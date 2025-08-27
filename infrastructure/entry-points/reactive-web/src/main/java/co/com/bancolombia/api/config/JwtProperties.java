package co.com.bancolombia.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
  private String privateKey;
  private String publicKey;
  private long expiration;
  private String issuer;
}
