package fr.knowledge.command.api.common;

public class AuthorizationInfoDTO {
  private String client;
  private String clientSecret;
  private String username;
  private String accessToken;

  public AuthorizationInfoDTO(String client, String clientSecret, String username, String accessToken) {
    this.client = client;
    this.clientSecret = clientSecret;
    this.username = username;
    this.accessToken = accessToken;
  }

  public String getClient() {
    return client;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getUsername() {
    return username;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
