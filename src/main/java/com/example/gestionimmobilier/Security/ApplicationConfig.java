package com.example.gestionimmobilier.Security;


import com.example.gestionimmobilier.Repository.UserRepository;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableCaching
public class ApplicationConfig {

  private final UserRepository repository;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

//  @Bean
//  public AuditorAware<Integer> auditorAware() {return new ApplicationAuditAware();
//  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
  }
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().addSecurityItem(new SecurityRequirement().
                    addList("Bearer Authentication"))
            .components(new Components().addSecuritySchemes
                    ("Bearer Authentication", createAPIKeyScheme()))
            .info(new Info().title("My REST API")
                    .description("Some custom description of API.")
                    .version("1.0").contact(new Contact().name("Sallo Szrajbman")
                            .email( "www.baeldung.com").url("salloszraj@gmail.com"))
                    .license(new License().name("License of API")
                            .url("API license URL")));
  }
}
