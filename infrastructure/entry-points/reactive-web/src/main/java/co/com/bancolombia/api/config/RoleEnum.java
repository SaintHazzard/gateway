package co.com.bancolombia.api.config;


import lombok.Getter;

@Getter
public enum RoleEnum {
  CLIENTE("ROLE_CLIENTE"),
  ADMIN("ROLE_ADMIN"),
  ASESOR("ROLE_ASESOR");

  private final String authority;

  RoleEnum(String authority) {
    this.authority = authority;
  }
}
