package ns.maintainusers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // Allow all paths
      .allowedOrigins("http://localhost:4200") // Allow requests from this origin
      .allowedMethods("GET", "POST", "PUT", "DELETE"); // Allow these methods
  }
}