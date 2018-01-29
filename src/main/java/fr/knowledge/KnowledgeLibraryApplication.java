package fr.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class KnowledgeLibraryApplication {
  public static void main(String[] args) {
    SpringApplication.run(KnowledgeLibraryApplication.class, args);
  }
}

