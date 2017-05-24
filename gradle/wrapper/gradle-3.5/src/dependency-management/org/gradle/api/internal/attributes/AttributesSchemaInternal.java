/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.api.internal.attributes;

import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.attributes.AttributesSchema;
import org.gradle.api.attributes.HasAttributes;
import org.gradle.internal.component.model.AttributeMatcher;

import java.util.List;

public interface AttributesSchemaInternal extends AttributesSchema {
    /**
     * Creates a copy of this schema, that will ignore all attributes for which the producer has provided a value but the consumer has not.
     */
    AttributeMatcher ignoreAdditionalProducerAttributes();

    /**
     * Creates a copy of this schema, that will ignore all attributes for which the consumer has provided a value but the producer has not.
     */
    AttributeMatcher ignoreAdditionalConsumerAttributes();

    <T extends HasAttributes> List<T> getMatches(AttributesSchema producerAttributeSchema, List<T> candidates, AttributeContainer consumer);
}
