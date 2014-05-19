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
package com.clearnlp.dictionary.universal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.clearnlp.dictionary.universal.DTCompound;
import com.clearnlp.type.LanguageType;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jdchoi77@gmail.com})
 */
public class DTCompoundTest
{
	@Test
	public void test()
	{
		DTCompound dt = new DTCompound(LanguageType.ENGLISH);
		
		assertEquals("[I, 'mmm]"   , Arrays.toString(dt.tokenize("I'mmm")));
		assertEquals("[wha, d, ya]", Arrays.toString(dt.tokenize("whadya")));
		
		assertTrue(dt.tokenize("I'm") == null);
	}
}
