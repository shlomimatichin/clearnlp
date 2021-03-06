/**
 * Copyright 2014, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.clir.clearnlp.component.mode.dep;

import java.io.ObjectInputStream;

import edu.emory.clir.clearnlp.classification.model.StringModel;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class DefaultDEPParser extends AbstractDEPParser
{
	/** Creates a dependency parser for train. */
	public DefaultDEPParser(DEPConfiguration configuration, DEPFeatureExtractor[] extractors, Object lexicons)
	{
		super(configuration, extractors, lexicons);
	}
	
	/** Creates a dependency parser for bootstrap or evaluate. */
	public DefaultDEPParser(DEPConfiguration configuration, DEPFeatureExtractor[] extractors, Object lexicons, StringModel[] models, boolean bootstrap)
	{
		super(configuration, extractors, lexicons, models, bootstrap);
	}

	/** Creates a pos tagger for decode. */
	public DefaultDEPParser(DEPConfiguration configuration, ObjectInputStream in)
	{
		super(configuration, in);
	}
	
	/** Creates a dependency parsing for decode. */
	public DefaultDEPParser(DEPConfiguration configuration, byte[] models)
	{
		super(configuration, models);
	}
}
