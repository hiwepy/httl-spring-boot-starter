/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package httl.spring.boot;

import java.util.Properties;

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for the HTTL template engine, bound to the
 * {@value #PREFIX} namespace.
 * <p>
 * Extends Spring Boot's {@link AbstractTemplateViewResolverProperties} so the
 * standard view-resolver options (prefix, suffix, cache, content-type, ...)
 * are honoured, while adding HTTL-specific tuning such as the native
 * {@code settings} map and the {@code autoCheck} hot-reload flag.
 * </p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@ConfigurationProperties(HttlProperties.PREFIX)
@Getter
@Setter
@ToString
public class HttlProperties extends AbstractTemplateViewResolverProperties {

	/**
	 * Property prefix under which HTTL options live.
	 */
	public static final String PREFIX = "spring.httl";

	/**
	 * Default location from which HTTL templates are loaded.
	 */
	public static final String DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/";

	/**
	 * Default template name prefix.
	 */
	public static final String DEFAULT_PREFIX = "";

	/**
	 * Default template file suffix.
	 */
	public static final String DEFAULT_SUFFIX = ".httl";

	/** Whether Enable Form Authorization. */
	private boolean enabled = false;

	/**
	 * Well-known Beetl keys which will be passed to Beetl's  Configuration.
	 */
	private Properties settings = new Properties();

	/**
	 * Comma-separated list of template paths.
	 */
	private String[] templateLoaderPath = new String[] { DEFAULT_TEMPLATE_LOADER_PATH };

	/**
	 * Prefer file system access for template loading. File system access enables
	 * hot detection of template changes.
	 */
	private boolean preferFileSystemAccess = true;

	/**
	 * Whether template file changes are detected automatically (hot reload).
	 */
	private boolean autoCheck = false;

	/**
	 * Creates a new instance using the default template prefix and suffix.
	 */
	public HttlProperties() {
		super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
	}

}
