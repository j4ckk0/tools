package org.jtools.mappings.simple.exporters;

/*-
 * #%L
 * Java Tools - Mappings
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

import java.io.IOException;
import java.util.List;

import org.jtools.mappings.common.MappingException;
import org.jtools.mappings.simple.SimpleMappingRow;

// TODO: Auto-generated Javadoc
/**
 * The Class ASimpleMappingExporter.
 */
public abstract class ASimpleMappingExporter {

	/**
	 * Export data.
	 *
	 * @param <T> the generic type
	 * @param data the data
	 * @param mappings the mappings
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws MappingException the mapping exception
	 */
	public abstract <T> void exportData(List<T> data, List<SimpleMappingRow> mappings) throws IOException, MappingException;

}
