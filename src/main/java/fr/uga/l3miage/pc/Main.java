package fr.uga.l3miage.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EntityScan(basePackages = "fr.uga.l3miage.pc.entities")
@SpringBootApplication(
        scanBasePackages = {"fr.uga.l3miage.pc"},  // Specify the base package for component scanning
        exclude = {DataSourceAutoConfiguration.class}  // Exclude DataSource auto-configuration
)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://frontend-example.com") // Remplace par ton domaine de frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*") // Restreindre les headers si nécessaire
                        .allowCredentials(true); // Permettre l'envoi des cookies (si nécessaire)
            }
        };
    }
}