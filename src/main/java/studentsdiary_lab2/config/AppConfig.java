package studentsdiary_lab2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "studentsdiary_lab2")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {
}