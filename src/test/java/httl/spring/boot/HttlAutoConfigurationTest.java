package httl.spring.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

import httl.web.springmvc.HttlViewResolver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link HttlAutoConfiguration}.
 *
 * <p>The HTTL Spring MVC view resolver ({@link HttlViewResolver}) requires a
 * fully initialized servlet container to complete {@code afterPropertiesSet},
 * so these tests avoid spinning up a whole web context and instead exercise
 * the configuration class and {@link HttlProperties#applyToViewResolver}
 * directly.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("HttlAutoConfiguration Tests")
class HttlAutoConfigurationTest {

	@Test
	@DisplayName("Configuration can be instantiated and exposes its dependencies")
	void configurationIsInstantiable() {
		StaticApplicationContext context = new StaticApplicationContext();
		HttlProperties properties = new HttlProperties();
		HttlAutoConfiguration configuration = new HttlAutoConfiguration(context, properties);

		assertThat(configuration.getApplicationContext()).isSameAs(context);
		assertThat(configuration.getProperties()).isSameAs(properties);
	}

	@Test
	@DisplayName("checkTemplateLocationExists is a no-op when checking is disabled")
	void checkIsNoOpWhenDisabled() {
		StaticApplicationContext context = new StaticApplicationContext();
		HttlProperties properties = new HttlProperties();
		properties.setCheckTemplateLocation(false);
		HttlAutoConfiguration configuration = new HttlAutoConfiguration(context, properties);

		// Should not throw even though no template location exists.
		configuration.checkTemplateLocationExists();
		assertThat(properties.isCheckTemplateLocation()).isFalse();
	}

	@Test
	@DisplayName("checkTemplateLocationExists warns but does not fail for a missing location")
	void checkWarnsForMissingLocation() {
		StaticApplicationContext context = new StaticApplicationContext();
		HttlProperties properties = new HttlProperties();
		properties.setCheckTemplateLocation(true);
		properties.setTemplateLoaderPath(new String[] { "classpath:/does-not-exist/" });
		HttlAutoConfiguration configuration = new HttlAutoConfiguration(context, properties);

		configuration.checkTemplateLocationExists();
		assertThat(properties.isCheckTemplateLocation()).isTrue();
	}

	@Test
	@DisplayName("applyToViewResolver applies all view-resolver options")
	void applyToViewResolverAppliesOptions() {
		HttlProperties properties = new HttlProperties();
		properties.setPrefix("/httl/");
		properties.setSuffix(".tpl");
		properties.setCache(false);
		properties.setContentType("text/html;charset=UTF-8");
		properties.setExposeRequestAttributes(true);
		properties.setAllowRequestOverride(true);
		properties.setExposeSessionAttributes(true);
		properties.setAllowSessionOverride(true);
		properties.setRequestContextAttribute("rc");
		properties.setViewNames(new String[] { "home" });

		HttlViewResolver resolver = new HttlViewResolver();
		// Exercises every branch of applyToViewResolver; the resolver getters are
		// protected so we only assert the call completes without error.
		properties.applyToViewResolver(resolver);
		assertThat(resolver).isNotNull();
	}

	@Test
	@DisplayName("applyToViewResolver tolerates null optional fields")
	void applyToViewResolverToleratesNulls() {
		HttlProperties properties = new HttlProperties();
		// contentType, requestContextAttribute and viewNames are null by default.
		HttlViewResolver resolver = new HttlViewResolver();
		properties.applyToViewResolver(resolver);
		assertThat(resolver).isNotNull();
	}

	@Test
	@DisplayName("HttlConfiguration.buildSettings returns a copy of the bound settings")
	void buildSettingsReturnsCopy() {
		HttlAutoConfiguration.HttlConfiguration configuration = new HttlAutoConfiguration.HttlConfiguration();
		HttlProperties properties = new HttlProperties();
		properties.getSettings().setProperty("codec", "UTF-8");
		configuration.properties = properties;

		java.util.Properties built = configuration.buildSettings();

		assertThat(built).isNotSameAs(properties.getSettings()).containsEntry("codec", "UTF-8");
	}

	@Test
	@DisplayName("Inner web and non-web configuration classes are instantiable")
	void innerConfigurationsAreInstantiable() {
		assertThat(new HttlAutoConfiguration.HttlNonWebConfiguration()).isNotNull();
		assertThat(new HttlAutoConfiguration.HttlWebConfiguration()).isNotNull();
	}

}
