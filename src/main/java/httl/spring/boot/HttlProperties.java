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

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the HTTL template engine, bound to the
 * {@value #PREFIX} namespace.
 * <p>
 * Holds the standard view-resolver options (prefix, suffix, cache,
 * content-type, request-context attribute, view-names, ...) together with
 * HTTL-specific tuning such as the native {@code settings} map, the template
 * loader paths and the {@code autoCheck} hot-reload flag.
 * </p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@ConfigurationProperties(HttlProperties.PREFIX)
public class HttlProperties {

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

	/** Template name prefix applied to every view name. */
	private String prefix = DEFAULT_PREFIX;

	/** Template file suffix applied to every view name. */
	private String suffix = DEFAULT_SUFFIX;

	/** Whether to cache resolved templates. */
	private boolean cache = true;

	/** Content-Type used when rendering HTTL views. */
	private String contentType;

	/** Whether to expose the Spring RequestContext under the configured attribute name. */
	private boolean exposeRequestAttributes = false;

	/** Whether request attributes are allowed to override controller-provided attributes. */
	private boolean allowRequestOverride = false;

	/** Whether to expose the HTTP session as a request attribute. */
	private boolean exposeSessionAttributes = false;

	/** Whether session attributes are allowed to override controller-provided attributes. */
	private boolean allowSessionOverride = false;

	/** Whether to verify that the configured template location exists at startup. */
	private boolean checkTemplateLocation = true;

	/** Attribute name under which the Spring RequestContext is exposed to templates. */
	private String requestContextAttribute;

	/** Restrict the view names this resolver will resolve; {@code null} means no restriction. */
	private String[] viewNames;

	/**
	 * Well-known HTTL keys which will be passed to the HTTL engine configuration.
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

	/** Return whether the starter is enabled. @return true if enabled */
	public boolean isEnabled() {
		return enabled;
	}

	/** Set whether the starter is enabled. @param enabled true to enable */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/** Return the template name prefix. @return the prefix */
	public String getPrefix() {
		return prefix;
	}

	/** Set the template name prefix. @param prefix the prefix */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/** Return the template file suffix. @return the suffix */
	public String getSuffix() {
		return suffix;
	}

	/** Set the template file suffix. @param suffix the suffix */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/** Return whether templates are cached. @return true if caching is enabled */
	public boolean isCache() {
		return cache;
	}

	/** Set whether templates are cached. @param cache true to cache */
	public void setCache(boolean cache) {
		this.cache = cache;
	}

	/** Return the content type used for HTTL views. @return the content type */
	public String getContentType() {
		return contentType;
	}

	/** Set the content type used for HTTL views. @param contentType the content type */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/** Return whether request attributes are exposed to templates. @return true if exposed */
	public boolean isExposeRequestAttributes() {
		return exposeRequestAttributes;
	}

	/** Set whether request attributes are exposed to templates. @param exposeRequestAttributes true to expose */
	public void setExposeRequestAttributes(boolean exposeRequestAttributes) {
		this.exposeRequestAttributes = exposeRequestAttributes;
	}

	/** Return whether request attributes may override controller attributes. @return true if override is allowed */
	public boolean isAllowRequestOverride() {
		return allowRequestOverride;
	}

	/** Set whether request attributes may override controller attributes. @param allowRequestOverride true to allow */
	public void setAllowRequestOverride(boolean allowRequestOverride) {
		this.allowRequestOverride = allowRequestOverride;
	}

	/** Return whether session attributes are exposed to templates. @return true if exposed */
	public boolean isExposeSessionAttributes() {
		return exposeSessionAttributes;
	}

	/** Set whether session attributes are exposed to templates. @param exposeSessionAttributes true to expose */
	public void setExposeSessionAttributes(boolean exposeSessionAttributes) {
		this.exposeSessionAttributes = exposeSessionAttributes;
	}

	/** Return whether session attributes may override controller attributes. @return true if override is allowed */
	public boolean isAllowSessionOverride() {
		return allowSessionOverride;
	}

	/** Set whether session attributes may override controller attributes. @param allowSessionOverride true to allow */
	public void setAllowSessionOverride(boolean allowSessionOverride) {
		this.allowSessionOverride = allowSessionOverride;
	}

	/** Return whether the template location is checked at startup. @return true if checked */
	public boolean isCheckTemplateLocation() {
		return checkTemplateLocation;
	}

	/** Set whether the template location is checked at startup. @param checkTemplateLocation true to check */
	public void setCheckTemplateLocation(boolean checkTemplateLocation) {
		this.checkTemplateLocation = checkTemplateLocation;
	}

	/** Return the request context attribute name. @return the attribute name */
	public String getRequestContextAttribute() {
		return requestContextAttribute;
	}

	/** Set the request context attribute name. @param requestContextAttribute the attribute name */
	public void setRequestContextAttribute(String requestContextAttribute) {
		this.requestContextAttribute = requestContextAttribute;
	}

	/** Return the restricted set of view names. @return the view names, or {@code null} for no restriction */
	public String[] getViewNames() {
		return viewNames;
	}

	/** Set the restricted set of view names. @param viewNames the view names */
	public void setViewNames(String[] viewNames) {
		this.viewNames = viewNames;
	}

	/** Return the native HTTL settings. @return the settings properties */
	public Properties getSettings() {
		return settings;
	}

	/** Set the native HTTL settings. @param settings the settings properties */
	public void setSettings(Properties settings) {
		this.settings = settings;
	}

	/** Return the template loader paths. @return the loader paths */
	public String[] getTemplateLoaderPath() {
		return templateLoaderPath;
	}

	/** Set the template loader paths. @param templateLoaderPath the loader paths */
	public void setTemplateLoaderPath(String[] templateLoaderPath) {
		this.templateLoaderPath = templateLoaderPath;
	}

	/** Return whether file system access is preferred for template loading. @return true if preferred */
	public boolean isPreferFileSystemAccess() {
		return preferFileSystemAccess;
	}

	/** Set whether file system access is preferred for template loading. @param preferFileSystemAccess true to prefer */
	public void setPreferFileSystemAccess(boolean preferFileSystemAccess) {
		this.preferFileSystemAccess = preferFileSystemAccess;
	}

	/** Return whether hot-reload template checking is enabled. @return true if enabled */
	public boolean isAutoCheck() {
		return autoCheck;
	}

	/** Set whether hot-reload template checking is enabled. @param autoCheck true to enable */
	public void setAutoCheck(boolean autoCheck) {
		this.autoCheck = autoCheck;
	}

	/**
	 * Apply the standard view-resolver options to the given HTTL view resolver.
	 * @param resolver the HTTL view resolver to configure
	 */
	public void applyToViewResolver(httl.web.springmvc.HttlViewResolver resolver) {
		resolver.setPrefix(this.getPrefix());
		resolver.setSuffix(this.getSuffix());
		resolver.setCache(this.isCache());
		if (this.getContentType() != null) {
			resolver.setContentType(this.getContentType());
		}
		resolver.setExposeRequestAttributes(this.isExposeRequestAttributes());
		resolver.setAllowRequestOverride(this.isAllowRequestOverride());
		resolver.setExposeSessionAttributes(this.isExposeSessionAttributes());
		resolver.setAllowSessionOverride(this.isAllowSessionOverride());
		if (this.getRequestContextAttribute() != null) {
			resolver.setRequestContextAttribute(this.getRequestContextAttribute());
		}
		if (this.getViewNames() != null) {
			resolver.setViewNames(this.getViewNames());
		}
	}

}
