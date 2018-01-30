package fr.knowledge.remote.user;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("sharing-craftsman-user")
public interface UserService {
  @RequestMapping(method = RequestMethod.GET, value = "/tokens/verify")
  ResponseEntity verifyToken(@RequestHeader("client") String client,
                             @RequestHeader("secret") String secret,
                             @RequestHeader("username") String username,
                             @RequestHeader("access-token") String accessToken);

  @RequestMapping(method = RequestMethod.GET, value = "/roles")
  ResponseEntity getAuthorizationsOfUser(@RequestHeader("client") String client,
                                         @RequestHeader("secret") String secret,
                                         @RequestHeader("username") String username,
                                         @RequestHeader("access-token") String accessToken);
}
