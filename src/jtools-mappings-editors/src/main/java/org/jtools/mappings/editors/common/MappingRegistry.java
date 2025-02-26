package org.jtools.mappings.editors.common;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.mappings.common.IMapping;
import org.jtools.utils.messages.pubsub.DefaultPubSubBus;
// TODO: Auto-generated Javadoc

/**
 * The Class MappingRegistry.
 */
public class MappingRegistry {

	/** The instance. */
	private static MappingRegistry instance;

	/** The mappings. */
	private final Map<UUID, IMapping> mappings;

	/**
	 * Instantiates a new mapping registry.
	 */
	private MappingRegistry() {
		this.mappings = new HashMap<>();
	}

	/**
	 * Instance.
	 *
	 * @return the mapping registry
	 */
	public static MappingRegistry instance() {
		if (instance == null) {
			instance = new MappingRegistry();
		}
		return instance;
	}

	/**
	 * Register.
	 *
	 * @param mapping the mapping
	 */
	public void register(IMapping mapping) {
		mappings.put(mapping.getId(), mapping);
		
		DefaultPubSubBus.instance().sendObjectMessage(MappingPubSub.MAPPING_ADDED, mapping.getId());
	}

	/**
	 * Unregister.
	 *
	 * @param mapping the mapping
	 */
	public void unregister(IMapping mapping) {
		mappings.remove(mapping.getId());
		
		DefaultPubSubBus.instance().sendObjectMessage(MappingPubSub.MAPPING_REMOVED, mapping.getId());
	}

	/**
	 * Gets the.
	 *
	 * @param mappingId the mapping id
	 * @return the i mapping
	 */
	public IMapping get(UUID mappingId) {
		return mappings.get(mappingId);
	}

	/**
	 * Gets the.
	 *
	 * @param <M> the generic type
	 * @param mappingId the mapping id
	 * @param mappingClass the mapping class
	 * @return the m
	 */
	public <M extends IMapping> M get(UUID mappingId, Class<M> mappingClass) {
		IMapping mapping = get(mappingId);

		try {
			return mappingClass.cast(mapping);
		} catch (ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Wrong mapping type found for mappingId: "
					+ mappingId + ". Found: " + mapping.getClass() + ". Requested: " + mappingClass);
			return null;
		}
	}
}
