package httl.spring.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import httl.web.WebEngine;
import httl.web.springmvc.HttlViewResolver;


/**
 * Spring Boot auto-configuration for the HTTL (Hyper-Text Template Language)
 * view layer.
 * <p>
 * Binds {@link HttlProperties}, optionally verifies that the configured
 * template location exists, and registers the appropriate inner
 * configuration based on the application type: a no-op
 * {@link HttlNonWebConfiguration} for non-web apps and an
 * {@link HttlWebConfiguration} that contributes an {@link HttlViewResolver}
 * (and optional {@link ResourceUrlEncodingFilter}) for web apps.
 * </p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(HttlProperties.class)
public class HttlAutoConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(HttlAutoConfiguration.class);

	private final ApplicationContext applicationContext;

	private final HttlProperties properties;

	/**
	 * Creates a new instance wiring the Spring context and the bound HTTL
	 * properties.
	 *
	 * @param applicationContext the running Spring application context
	 * @param properties         the bound HTTL configuration properties
	 */
	public HttlAutoConfiguration(ApplicationContext applicationContext, HttlProperties properties) {
		this.applicationContext = applicationContext;
		this.properties = properties;
	}

	/**
	 * Verifies that at least one of the configured template loader paths
	 * resolves to an existing resource, emitting a warning otherwise.
	 * <p>Skipped when {@code spring.httl.checkTemplateLocation} is disabled.</p>
	 */
	@PostConstruct
	public void checkTemplateLocationExists() {
		if (this.properties.isCheckTemplateLocation()) {
			TemplateLocation templatePathLocation = null;
			List<TemplateLocation> locations = new ArrayList<TemplateLocation>();
			for (String templateLoaderPath : this.properties.getTemplateLoaderPath()) {
				TemplateLocation location = new TemplateLocation(templateLoaderPath);
				locations.add(location);
				if (location.exists(this.applicationContext)) {
					templatePathLocation = location;
					break;
				}
			}
			if (templatePathLocation == null) {
				logger.warn("Cannot find template location(s): " + locations
						+ " (please add some templates, "
						+ "check your Httl configuration, or set "
						+ "spring.httl.checkTemplateLocation=false)");
			}
		}
	}

	/**
	 * Shared base class holding the common HTTL wiring logic for both web and
	 * non-web application configurations.
	 */
	protected static class HttlConfiguration {

		@Autowired
		protected HttlProperties properties;

		/**
		 * Applies the bound HTTL settings to the supplied configuration factory.
		 *
		 * @param factory the configuration factory to configure
		 */
		protected void applyProperties(HttlConfiguration factory) {
			/*factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
			factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
			factory.setDefaultEncoding(this.properties.getCharsetName());*/
			Properties settings = new Properties();
			settings.putAll(this.properties.getSettings());
			//factory.setFreemarkerSettings(settings);
		}

	}

	/**
	 * Inner configuration activated in non-web applications. Kept as an
	 * extension point; the concrete bean wiring is currently commented out.
	 */
	@Configuration
	@ConditionalOnNotWebApplication
	public static class HttlNonWebConfiguration extends HttlConfiguration {

		/*@Bean
		@ConditionalOnMissingBean
		public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration() {
			FreeMarkerConfigurationFactoryBean freeMarkerFactoryBean = new FreeMarkerConfigurationFactoryBean();
			applyProperties(freeMarkerFactoryBean);
			return freeMarkerFactoryBean;
		}*/

	}

	/**
	 * Inner configuration activated in web applications, registering the HTTL
	 * Spring MVC view resolver and (optionally) the resource URL encoding
	 * filter used for cache-busting static resources.
	 */
	@Configuration
	@ConditionalOnClass({ Servlet.class, WebEngine.class })
	@ConditionalOnWebApplication
	public static class HttlWebConfiguration extends HttlConfiguration {

		/**
		 * Creates the {@link HttlViewResolver} used to render HTTL templates,
		 * unless the user has already defined a bean named {@code httlViewResolver}.
		 *
		 * @return the HTTL view resolver
		 */
		@Bean
		@ConditionalOnMissingBean(name = "httlViewResolver")
		@ConditionalOnProperty(name = "spring.httl.enabled", matchIfMissing = true)
		public HttlViewResolver httlViewResolver() {
			HttlViewResolver resolver = new HttlViewResolver();
			this.properties.applyToMvcViewResolver(resolver);
			return resolver;
		}

		/**
		 * Creates the {@link ResourceUrlEncodingFilter} that rewrites static
		 * resource URLs to include cache-busting content hashes.
		 *
		 * @return the resource URL encoding filter
		 */
		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnEnabledResourceChain
		public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
			return new ResourceUrlEncodingFilter();
		}

	}

}
