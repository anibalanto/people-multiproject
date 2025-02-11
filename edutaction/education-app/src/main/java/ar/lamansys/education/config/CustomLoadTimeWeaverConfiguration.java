package ar.lamansys.education.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.LoadTimeWeaver;

import java.lang.instrument.ClassFileTransformer;

@Configuration
public class CustomLoadTimeWeaverConfiguration {

    @Bean
    public LoadTimeWeaver loadTimeWeaver() {
        return new LoadTimeWeaver() {
            @Override
            public void addTransformer(ClassFileTransformer transformer) {
                // No-op, o implementa según tus necesidades
            }

            @Override
            public ClassLoader getInstrumentableClassLoader() {
                return Thread.currentThread().getContextClassLoader();
            }

            @Override
            public ClassLoader getThrowawayClassLoader() {
                // Puedes retornar el mismo class loader o crear uno nuevo según lo requieras.
                return getInstrumentableClassLoader();
            }
        };
    }
}
