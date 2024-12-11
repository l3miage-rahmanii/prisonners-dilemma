package fr.uga.l3miage.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"fr.uga.l3miage.pc"})
@EntityScan(basePackages = "fr.uga.l3miage.pc.Entities")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
