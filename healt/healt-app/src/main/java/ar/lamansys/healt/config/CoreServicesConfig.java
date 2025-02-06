package ar.lamansys.healt.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "people.service-impl", havingValue = "core")
@ComponentScan("ar.lamansys.people.core")
public class CoreServicesConfig {}