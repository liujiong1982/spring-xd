/*
 * Copyright 2013 the original author or authors.
 *
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
 */

package org.springframework.xd.dirt.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.context.ApplicationEvent;

/**
 * @author Mark Fisher
 */
@SuppressWarnings("serial")
public abstract class AbstractEvent<S> extends ApplicationEvent {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final Map<String, String> attributes = new HashMap<String, String>();

	private final Log logger = LogFactory.getLog(getClass());


	public AbstractEvent(S source) {
		super(source);
	}


	@Override
	@SuppressWarnings("unchecked")
	public S getSource() {
		return (S) super.getSource();
	}

	public final String getType() {
		String name = this.getClass().getSimpleName();
		return name.replace("Event", "");
	}

	public final Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(this.attributes);
	}

	public void setAttribute(String key, String value) {
		this.attributes.put(key, value);
	}

	@Override
	public String toString() {
		try {
			return this.objectMapper.writeValueAsString(this);
		}
		catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("failed to generate JSON", e);
			}
			return super.toString();
		}
	}

}
