package httl.spring.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Servlet;

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


@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(HttlProperties.class)
public class HttlAutoConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(HttlAutoConfiguration.class);

	private final ApplicationContext applicationContext;

	private final HttlProperties properties;

	public HttlAutoConfiguration(ApplicationContext applicationContext, HttlProperties properties) {
		this.applicationContext = applicationContext;
		this.properties = properties;
	}

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

	protected static class HttlConfiguration {

		@Autowired
		protected HttlProperties properties;

		protected void applyProperties(HttlConfiguration factory) {
			/*factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
			factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
			factory.setDefaultEncoding(this.properties.getCharsetName());*/
			Properties settings = new Properties();
			settings.putAll(this.properties.getSettings());
			//factory.setFreemarkerSettings(settings);
		}

	}
	
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

	@Configuration
	@ConditionalOnClass({ Servlet.class, WebEngine.class })
	@ConditionalOnWebApplication
	public static class HttlWebConfiguration extends HttlConfiguration {
		
		@Bean
		@ConditionalOnMissingBean(name = "httlViewResolver")
		@ConditionalOnProperty(name = "spring.httl.enabled", matchIfMissing = true)
		public HttlViewResolver httlViewResolver() {
			HttlViewResolver resolver = new HttlViewResolver();
			this.properties.applyToMvcViewResolver(resolver);
			return resolver;
		}

		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnEnabledResourceChain
		public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
			return new ResourceUrlEncodingFilter();
		}

	}
	
}
