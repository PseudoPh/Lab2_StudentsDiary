package studentsdiary_lab2.config;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "studentsdiary_lab2")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}