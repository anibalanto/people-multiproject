package ar.lamansys.education.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "alert.service-impl", havingValue = "api-access")
@ComponentScan("ar.lamansys.alert.apiaccess")
public class ServicesImplAlertApiAccessConfig {}
