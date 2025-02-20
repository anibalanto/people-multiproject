package ar.lamansys.health.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "people.service-impl", havingValue = "api-access")
@ComponentScan("ar.lamansys.people.apiaccess")
public class ApiAccessServicesConfig {}
