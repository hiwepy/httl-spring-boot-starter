package httl.spring.boot;

import java.util.Properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link HttlProperties}.
 *
 * <p>Exercises the property prefix, defaults and every accessor so the
 * configuration POJO is fully covered.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("HttlProperties Tests")
class HttlPropertiesTest {

	@Test
	@DisplayName("Prefix and default constants are correct")
	void constantsAreCorrect() {
		assertThat(HttlProperties.PREFIX).isEqualTo("spring.httl");
		assertThat(HttlProperties.DEFAULT_TEMPLATE_LOADER_PATH).isEqualTo("classpath:/templates/");
		assertThat(HttlProperties.DEFAULT_PREFIX).isEqualTo("");
		assertThat(HttlProperties.DEFAULT_SUFFIX).isEqualTo(".httl");
	}

	@Test
	@DisplayName("Defaults are applied on construction")
	void defaultsAreApplied() {
		HttlProperties properties = new HttlProperties();
		assertThat(properties.isEnabled()).isFalse();
		assertThat(properties.getPrefix()).isEmpty();
		assertThat(properties.getSuffix()).isEqualTo(".httl");
		assertThat(properties.isCache()).isTrue();
		assertThat(properties.isCheckTemplateLocation()).isTrue();
		assertThat(properties.isPreferFileSystemAccess()).isTrue();
		assertThat(properties.isAutoCheck()).isFalse();
		assertThat(properties.getTemplateLoaderPath()).containsExactly("classpath:/templates/");
		assertThat(properties.getSettings()).isNotNull().isEmpty();
	}

	@Test
	@DisplayName("Accessors round-trip every field")
	void accessorsRoundTrip() {
		HttlProperties properties = new HttlProperties();

		properties.setEnabled(true);
		properties.setPrefix("/httl/");
		properties.setSuffix(".tpl");
		properties.setCache(false);
		properties.setContentType("text/html;charset=UTF-8");
		properties.setExposeRequestAttributes(true);
		properties.setAllowRequestOverride(true);
		properties.setExposeSessionAttributes(true);
		properties.setAllowSessionOverride(true);
		properties.setCheckTemplateLocation(false);
		properties.setRequestContextAttribute("rc");
		properties.setViewNames(new String[] { "home", "error/*" });

		Properties settings = new Properties();
		settings.setProperty("codec", "UTF-8");
		properties.setSettings(settings);

		properties.setTemplateLoaderPath(new String[] { "classpath:/views/", "classpath:/tpl/" });
		properties.setPreferFileSystemAccess(false);
		properties.setAutoCheck(true);

		assertThat(properties.isEnabled()).isTrue();
		assertThat(properties.getPrefix()).isEqualTo("/httl/");
		assertThat(properties.getSuffix()).isEqualTo(".tpl");
		assertThat(properties.isCache()).isFalse();
		assertThat(properties.getContentType()).isEqualTo("text/html;charset=UTF-8");
		assertThat(properties.isExposeRequestAttributes()).isTrue();
		assertThat(properties.isAllowRequestOverride()).isTrue();
		assertThat(properties.isExposeSessionAttributes()).isTrue();
		assertThat(properties.isAllowSessionOverride()).isTrue();
		assertThat(properties.isCheckTemplateLocation()).isFalse();
		assertThat(properties.getRequestContextAttribute()).isEqualTo("rc");
		assertThat(properties.getViewNames()).containsExactly("home", "error/*");
		assertThat(properties.getSettings()).containsEntry("codec", "UTF-8");
		assertThat(properties.getTemplateLoaderPath()).containsExactly("classpath:/views/", "classpath:/tpl/");
		assertThat(properties.isPreferFileSystemAccess()).isFalse();
		assertThat(properties.isAutoCheck()).isTrue();
	}

}
