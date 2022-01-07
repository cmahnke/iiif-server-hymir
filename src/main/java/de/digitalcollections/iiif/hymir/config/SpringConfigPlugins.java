package de.digitalcollections.iiif.hymir.config;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import de.digitalcollections.iiif.hymir.image.business.api.ImageQualityService;
import de.digitalcollections.iiif.hymir.model.api.HymirPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("plugins")
@ComponentScan(
        //TODO: There might be a Spring bug: If `custom.plugins.packages` isn't defined in the config this fails, The default is ignored here
        basePackages = {"${plugins.packages:de.digitalcollections.iiif.hymir}"}
)
public class SpringConfigPlugins {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SpringConfigPlugins.class);

    @Value("${plugins.packages:de.digitalcollections.iiif.hymir}")
    private String packages;

    @Autowired
    private List<HymirPlugin> plugins;

    @Bean
    public List<ImageQualityService> getImageQualityServices() {
        List<ImageQualityService> imageQualityServices = FluentIterable.from(plugins)
                .filter(ImageQualityService.class).filter(new Predicate<ImageQualityService>() {
                    @Override
                    public boolean apply(ImageQualityService input) {
                        return (!(input instanceof HymirPlugin.Buildin));
                    }
                })
                .toList();
        for (ImageQualityService iqs : imageQualityServices) {
            LOGGER.info("Supported quality {} found - {}", iqs.getQuality().toString(), (iqs.enabled() ? "enabled" : "disabled"));
        }
        return imageQualityServices;
    }

}
