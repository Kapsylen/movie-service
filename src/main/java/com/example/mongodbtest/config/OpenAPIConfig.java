package com.example.mongodbtest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Value("${app.openapi.swagger-url}")
  private String url;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(url);
    devServer.setDescription("Server URL in Development environment");

    Contact contact = new Contact();
    contact.setEmail("example-email@mail.com");
    contact.setName("Contact name");
    contact.setUrl("https://www.example-project.com");

    License mitLicense = new License().name("Example License").url("https://www.example-project.com/");

    Info info = new Info()
        .title("Movie API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes Movie Service endpoints.").termsOfService("https://www.example-project.com")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}