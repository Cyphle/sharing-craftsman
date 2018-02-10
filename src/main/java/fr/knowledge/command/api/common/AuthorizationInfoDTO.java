package fr.knowledge.command.api.common;

public class AuthorizationInfoDTO {
  private final String client;
  private final String clientSecret;
  private final String username;
  private final String accessToken;

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
