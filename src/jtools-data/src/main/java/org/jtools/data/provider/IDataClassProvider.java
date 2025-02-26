package org.jtools.data.provider;

/*-
 * #%L
 * Java Tools - Data
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Collections;
import java.util.List;
// TODO: Auto-generated Javadoc

/**
 * The Interface IDataClassProvider.
 */
public interface IDataClassProvider {

	/**
	 * Gets the data class.
	 *
	 * @return the data class
	 */
	public Class<?> getDataClass();

	/**
	 * Gets the possible data classes.
	 *
	 * @return the possible data classes
	 */
	public default List<Class<?>> getPossibleDataClasses() {
		return Collections.singletonList(getDataClass());
	}

}
