package de.digitalcollections.iiif.hymir.config;

import de.digitalcollections.iiif.hymir.image.business.api.ImageQualityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("!plugins")
@ComponentScan(
        basePackages = {"de.digitalcollections.iiif.hymir.image"}
)
public class SpringConfigImageQualityService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SpringConfigImageQualityService.class);

    @Autowired
    List<ImageQualityService> services;

    @Bean
    public List<ImageQualityService> getServices() {
        for (ImageQualityService iqs: services) {
            LOGGER.info("Supported quality {} found - {}", iqs.getQuality().toString(), (iqs.enabled() ? "enabled" : "disabled" ));
        }
        return services;
    }
}
