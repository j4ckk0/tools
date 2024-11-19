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

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
public interface DataProviderPubSub {

	public static final String DATA_PROVIDER_ADDED = "DATA_PROVIDER_ADDED_PROPERTY";

	public static final String DATA_PROVIDER_REMOVED = "DATA_PROVIDER_REMOVED_PROPERTY";

	public static final String DATA_PROVIDER_CHANGED = "DATA_PROVIDER_CHANGED_PROPERTY";

	public static TextMessage castMessage(Message message) throws ClassCastException {
		if (!(message instanceof TextMessage)) {
			String msg = "Pub/Sub message received. Unexpected message type. Expected: " + TextMessage.class + ". Got: "
					+ message.getClass();
			Logger.getLogger(DataProviderPubSub.class.getName()).log(Level.WARNING, msg);
			throw new ClassCastException(msg);
		}

		return (TextMessage) message;
	}
	public static String readMessage(Message message) throws JMSException, ClassCastException {
		TextMessage textMessage = castMessage(message);

		return textMessage.getText();
	}
}
