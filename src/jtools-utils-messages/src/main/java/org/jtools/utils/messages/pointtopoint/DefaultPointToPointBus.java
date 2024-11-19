package org.jtools.utils.messages.pointtopoint;

/*-
 * #%L
 * Java Tools - Utils - Messages
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

import org.jtools.utils.messages.DefaultBusConfig;
public class DefaultPointToPointBus extends PointToPointBus {

	private static DefaultPointToPointBus instance;

	private DefaultPointToPointBus() {
		super(DefaultBusConfig.getUrl());
	}

	public static DefaultPointToPointBus instance() {
		if (instance == null) {
			instance = new DefaultPointToPointBus();
		}
		return instance;
	}

}
