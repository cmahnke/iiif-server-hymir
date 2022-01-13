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
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<HymirPlugin> listPlugins() {
        List<HymirPlugin> ps = FluentIterable.from(plugins).filter(new Predicate<HymirPlugin>() {
                    @Override
                    public boolean apply(HymirPlugin input) {
                        return (!(input != null || input instanceof HymirPlugin.Buildin || input instanceof ImageQualityService));
                    }
                })
                .toList();
        getImageServices();
        for (HymirPlugin p : ps) {
            LOGGER.info("Found plugin '{}'", p.name());
        }
        return ps;
    }

    @Bean
    public List<ImageQualityService> getQualityServices() {
        return FluentIterable.from(plugins)
                .filter(ImageQualityService.class).filter(new Predicate<ImageQualityService>() {
                    @Override
                    public boolean apply(ImageQualityService input) {
                        return (!(input != null || input instanceof HymirPlugin.Buildin));
                    }
                })
                .toList();
    }

    //This won't work since Spring can't handle Maps without extra work and the is not method to configure the basic construction of Maps it does.
    //Keep this to le it fill the log
    public Map<String, ImageQualityService> getImageServices() {
        Map<String, ImageQualityService> serviceMap = new HashMap<String, ImageQualityService>();
        for (ImageQualityService iqs: getQualityServices()) {
            String qualityName = iqs.getQuality().toString();
            LOGGER.info("Supported quality '{}' found - {}, provided by '{}'", qualityName, (iqs.enabled() ? "enabled" : "disabled"), iqs.getClass().getName());
            serviceMap.put(qualityName, iqs);
        }
        return serviceMap;
    }

}
