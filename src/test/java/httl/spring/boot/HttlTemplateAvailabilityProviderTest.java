package httl.spring.boot;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link HttlTemplateAvailabilityProvider}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("HttlTemplateAvailabilityProvider Tests")
class HttlTemplateAvailabilityProviderTest {

	@Test
	@DisplayName("Provider is a TemplateAvailabilityProvider instance")
	void isTemplateAvailabilityProvider() {
		TemplateAvailabilityProvider provider = new HttlTemplateAvailabilityProvider();
		assertThat(provider).isNotNull();
	}

	@Test
	@DisplayName("Availability properties expose default loader path, prefix and suffix")
	void availabilityPropertiesDefaults() {
		HttlTemplateAvailabilityProvider.HttlTemplateAvailabilityProperties properties =
				new HttlTemplateAvailabilityProvider.HttlTemplateAvailabilityProperties();

		List<String> loaderPath = properties.getLoaderPath();
		assertThat(loaderPath).isNotNull();
		assertThat(loaderPath).containsExactly(HttlProperties.DEFAULT_TEMPLATE_LOADER_PATH);

		List<String> templateLoaderPath = properties.getTemplateLoaderPath();
		assertThat(templateLoaderPath).containsExactly(HttlProperties.DEFAULT_TEMPLATE_LOADER_PATH);
		assertThat(properties.getPrefix()).isEqualTo(HttlProperties.DEFAULT_PREFIX);
		assertThat(properties.getSuffix()).isEqualTo(HttlProperties.DEFAULT_SUFFIX);
	}

	@Test
	@DisplayName("Availability properties loader path is mutable")
	void availabilityPropertiesSetter() {
		HttlTemplateAvailabilityProvider.HttlTemplateAvailabilityProperties properties =
				new HttlTemplateAvailabilityProvider.HttlTemplateAvailabilityProperties();

		List<String> paths = Arrays.asList("classpath:/views/", "classpath:/tpl/");
		properties.setTemplateLoaderPath(paths);

		assertThat(properties.getTemplateLoaderPath()).isSameAs(paths);
		assertThat(properties.getLoaderPath()).isSameAs(paths);
	}

}
