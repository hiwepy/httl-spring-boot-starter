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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.template.PathBasedTemplateAvailabilityProvider;

public class HttlTemplateAvailabilityProvider extends PathBasedTemplateAvailabilityProvider {

	public HttlTemplateAvailabilityProvider() {
		super("httl.spring.boot.HttlAutoConfiguration", HttlTemplateAvailabilityProperties.class, "spring.httl");
	}

	/**
	 * Minimal mirror of {@link HttlProperties} used by the availability check,
	 * exposing only the fields Spring Boot needs to resolve a template path.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
	 */
	static final class HttlTemplateAvailabilityProperties extends TemplateAvailabilityProperties {

		private List<String> templateLoaderPath = new ArrayList<String>(Arrays.asList(HttlProperties.DEFAULT_TEMPLATE_LOADER_PATH));

		HttlTemplateAvailabilityProperties() {
			super(HttlProperties.DEFAULT_PREFIX, HttlProperties.DEFAULT_SUFFIX);
		}

		/**
		 * Returns the template loader paths used for the availability check.
		 *
		 * @return list of template loader paths
		 */
		@Override
		protected List<String> getLoaderPath() {
			return this.templateLoaderPath;
		}

		/**
		 * Returns the template loader paths.
		 *
		 * @return list of template loader paths
		 */
		public List<String> getTemplateLoaderPath() {
			return this.templateLoaderPath;
		}

		/**
		 * Sets the template loader paths.
		 *
		 * @param templateLoaderPath list of template loader paths
		 */
		public void setTemplateLoaderPath(List<String> templateLoaderPath) {
			this.templateLoaderPath = templateLoaderPath;
		}

	}

}
